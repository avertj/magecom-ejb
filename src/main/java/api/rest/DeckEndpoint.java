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

import persistance.entity.Deck;
import api.rest.dto.DeckDTO;
import api.rest.dto.search.DeckSearchDTO;

@Stateless
@Path("/deck")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DeckEndpoint {
	@PersistenceContext(unitName = "magecom-ejb-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(DeckDTO dto) {
		Deck entity = dto.fromDTO(null, em);
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(DeckEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Deck entity = em.find(Deck.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		TypedQuery<Deck> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT d FROM Deck d LEFT JOIN FETCH d.member LEFT JOIN FETCH d.cards WHERE d.id = :entityId ORDER BY d.id",
						Deck.class);
		findByIdQuery.setParameter("entityId", id);
		Deck entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		DeckDTO dto = new DeckDTO(entity, true, false);
		return Response.ok(dto).build();
	}

	@GET
	@Path("/card/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DeckDTO> findByCard(@PathParam("id") Long id) {
		final List<DeckDTO> results = new ArrayList<DeckDTO>();

		TypedQuery<Deck> findByCardQuery = em
				.createQuery(
						"SELECT DISTINCT d FROM Deck d, DeckTuple dt LEFT JOIN FETCH d.member LEFT JOIN FETCH d.cards WHERE dt MEMBER OF d.cards AND dt.card.id = :card ORDER BY d.id",
						Deck.class);
		findByCardQuery.setParameter("card", id);
		final List<Deck> searchResults = findByCardQuery.getResultList();
		for (Deck searchResult : searchResults) {
			DeckDTO dto = new DeckDTO(searchResult, true, true);
			results.add(dto);
		}
		return results;
	}

	@GET
	@Path("/member/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DeckDTO> findByMember(@PathParam("id") Long id) {
		final List<DeckDTO> results = new ArrayList<DeckDTO>();

		TypedQuery<Deck> findByMemberQuery = em
				.createQuery(
						"SELECT DISTINCT d FROM Deck d LEFT JOIN FETCH d.member LEFT JOIN FETCH d.cards WHERE d.member.id = :member ORDER BY d.id",
						Deck.class);
		findByMemberQuery.setParameter("member", id);
		final List<Deck> searchResults = findByMemberQuery.getResultList();
		for (Deck searchResult : searchResults) {
			DeckDTO dto = new DeckDTO(searchResult, false, true);
			results.add(dto);
		}
		return results;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<DeckDTO> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<Deck> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT d FROM Deck d LEFT JOIN FETCH d.member LEFT JOIN FETCH d.cards ORDER BY d.id",
						Deck.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<Deck> searchResults = findAllQuery.getResultList();
		final List<DeckDTO> results = new ArrayList<DeckDTO>();
		for (Deck searchResult : searchResults) {
			DeckDTO dto = new DeckDTO(searchResult, true, true);
			results.add(dto);
		}
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, DeckDTO dto) {
		TypedQuery<Deck> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT d FROM Deck d WHERE d.id = :entityId ORDER BY d.id",
						Deck.class);
		findByIdQuery.setParameter("entityId", id);
		Deck entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		entity = dto.fromDTO(entity, em);
		try {
			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}
		return Response.noContent().build();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DeckDTO> search(@BeanParam DeckSearchDTO search) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(em);

		BooleanQuery root = new BooleanQuery();

		QueryBuilder qb = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(Deck.class).get();

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
				.createFullTextQuery(root, Deck.class);

		final List<Deck> searchResults = jpaQuery.getResultList();
		final List<DeckDTO> results = new ArrayList<DeckDTO>();
		for (Deck searchResult : searchResults) {
			DeckDTO dto = new DeckDTO(searchResult, false, true);
			results.add(dto);
		}
		// em.getTransaction().commit();
		// em.close();
		return results;
	}
}
