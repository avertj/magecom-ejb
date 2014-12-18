package api.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;

import persistance.entity.Combo;
import persistance.entity.Member;
import util.AuthUtils;
import api.rest.dto.ComboDTO;
import api.rest.dto.search.ComboSearchDTO;

@Stateless
@Path("/combo")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ComboEndpoint {
	@PersistenceContext(unitName = "magecom-ejb-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(ComboDTO dto) {
		Member member = em.find(Member.class, dto.getMember().getId());
		if (AuthUtils.validate(member, dto.getMember().getToken())) {
			Combo entity = dto.fromDTO(null, em);
			em.persist(entity);
			return Response
					.created(
							UriBuilder.fromResource(ComboEndpoint.class)
									.path(String.valueOf(entity.getId()))
									.build()).entity(entity).build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id,
			@QueryParam("token") String token) {
		Combo entity = em.find(Combo.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (AuthUtils.validate(entity.getMember(), token)) {
			em.remove(entity);
			return Response.noContent().build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		TypedQuery<Combo> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM Combo c LEFT JOIN FETCH c.member LEFT JOIN FETCH c.cards WHERE c.id = :entityId ORDER BY c.id",
						Combo.class);
		findByIdQuery.setParameter("entityId", id);
		Combo entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		ComboDTO dto = new ComboDTO(entity, true);
		return Response.ok(dto).build();
	}

	@GET
	@Path("/card/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ComboDTO> findByCard(@PathParam("id") Long id) {
		final List<ComboDTO> results = new ArrayList<ComboDTO>();

		TypedQuery<Combo> findByCardQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM Combo c, ComboTuple ct LEFT JOIN FETCH c.member LEFT JOIN FETCH c.cards WHERE ct MEMBER OF c.cards AND ct.card.id = :card ORDER BY c.id",
						Combo.class);
		findByCardQuery.setParameter("card", id);
		final List<Combo> searchResults = findByCardQuery.getResultList();
		for (Combo searchResult : searchResults) {
			ComboDTO dto = new ComboDTO(searchResult, true);
			results.add(dto);
		}
		return results;
	}

	@GET
	@Path("/member/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ComboDTO> findByMember(@PathParam("id") Long id) {
		final List<ComboDTO> results = new ArrayList<ComboDTO>();

		TypedQuery<Combo> findByMemberQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM Combo c LEFT JOIN FETCH c.member LEFT JOIN FETCH c.cards WHERE c.member.id = :member ORDER BY c.id",
						Combo.class);
		findByMemberQuery.setParameter("member", id);
		final List<Combo> searchResults = findByMemberQuery.getResultList();
		for (Combo searchResult : searchResults) {
			ComboDTO dto = new ComboDTO(searchResult, false);
			results.add(dto);
		}
		return results;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ComboDTO> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<Combo> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM Combo c LEFT JOIN FETCH c.member LEFT JOIN FETCH c.cards ORDER BY c.id",
						Combo.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<Combo> searchResults = findAllQuery.getResultList();
		final List<ComboDTO> results = new ArrayList<ComboDTO>();
		for (Combo searchResult : searchResults) {
			ComboDTO dto = new ComboDTO(searchResult, true);
			results.add(dto);
		}
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, ComboDTO dto) {
		TypedQuery<Combo> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM Combo c WHERE c.id = :entityId ORDER BY c.id",
						Combo.class);
		findByIdQuery.setParameter("entityId", id);
		Combo entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (AuthUtils.validate(entity.getMember(), dto.getMember().getToken())) {
			entity = dto.fromDTO(entity, em);
			try {
				entity = em.merge(entity);
			} catch (OptimisticLockException e) {
				return Response.status(Status.CONFLICT).entity(e.getEntity())
						.build();
			}
			return Response.noContent().build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ComboDTO> search(@BeanParam ComboSearchDTO search) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(em);

		BooleanQuery root = new BooleanQuery();

		QueryBuilder qb = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(Combo.class).get();

		if (search.getName() != null) {
			org.apache.lucene.search.Query nameCriteria = qb.keyword()
					.onField("name").andField("engram").boostedTo(5)
					.matching(search.getName()).createQuery();
			root.add(nameCriteria, BooleanClause.Occur.MUST);
		}
		if (search.getDescription() != null) {
			org.apache.lucene.search.Query typeCriteria = qb.phrase()
					.withSlop(2).onField("description")
					.sentence(search.getDescription()).createQuery();
			root.add(typeCriteria, BooleanClause.Occur.MUST);
		}
		if (search.getColor() != null) {
			if (search.getColor().getBlack() != null) {
				org.apache.lucene.search.Query manaCriteria = qb.keyword()
						.onField("color.black")
						.matching(search.getColor().getBlack()).createQuery();

				root.add(manaCriteria, BooleanClause.Occur.MUST);
			}
			if (search.getColor().getBlue() != null) {
				org.apache.lucene.search.Query manaCriteria = qb.keyword()
						.onField("color.blue")
						.matching(search.getColor().getBlue()).createQuery();

				root.add(manaCriteria, BooleanClause.Occur.MUST);
			}
			if (search.getColor().getGreen() != null) {
				org.apache.lucene.search.Query manaCriteria = qb.keyword()
						.onField("color.green")
						.matching(search.getColor().getGreen()).createQuery();

				root.add(manaCriteria, BooleanClause.Occur.MUST);
			}
			if (search.getColor().getRed() != null) {
				org.apache.lucene.search.Query manaCriteria = qb.keyword()
						.onField("color.red")
						.matching(search.getColor().getRed()).createQuery();

				root.add(manaCriteria, BooleanClause.Occur.MUST);
			}
			if (search.getColor().getWhite() != null) {
				org.apache.lucene.search.Query manaCriteria = qb.keyword()
						.onField("color.white")
						.matching(search.getColor().getWhite()).createQuery();

				root.add(manaCriteria, BooleanClause.Occur.MUST);
			}
		}
		javax.persistence.Query jpaQuery = fullTextEntityManager
				.createFullTextQuery(root, Combo.class);

		final List<Combo> searchResults = jpaQuery.getResultList();
		final List<ComboDTO> results = new ArrayList<ComboDTO>();
		for (Combo searchResult : searchResults) {
			ComboDTO dto = new ComboDTO(searchResult, false);
			results.add(dto);
		}
		// em.getTransaction().commit();
		// em.close();
		return results;
	}
}
