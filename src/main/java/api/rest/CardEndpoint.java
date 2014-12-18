package api.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import persistance.entity.Card;
import api.rest.dto.CardDTO;
import api.rest.dto.search.CardSearchDTO;

@Stateless
@Path("/card")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CardEndpoint {
	@PersistenceContext(unitName = "magecom-ejb-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(CardDTO dto) {
		Card entity = dto.newFromDTO(em);
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(CardEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	/*@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Card entity = em.find(Card.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}*/

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findById(@PathParam("id") Long id) {
		TypedQuery<Card> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM Card c WHERE c.id = :entityId ORDER BY c.id",
						Card.class);
		findByIdQuery.setParameter("entityId", id);
		Card entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		CardDTO dto = new CardDTO(entity);
		return Response.ok(dto).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CardDTO> listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<Card> findAllQuery = em.createQuery(
				"SELECT DISTINCT c FROM Card c ORDER BY c.id", Card.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<Card> searchResults = findAllQuery.getResultList();
		final List<CardDTO> results = new ArrayList<CardDTO>();
		for (Card searchResult : searchResults) {
			CardDTO dto = new CardDTO(searchResult);
			results.add(dto);
		}
		return results;
	}

	/*@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, CardDTO dto) {
		TypedQuery<Card> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT c FROM Card c WHERE c.id = :entityId ORDER BY c.id",
						Card.class);
		findByIdQuery.setParameter("entityId", id);
		Card entity;
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
	}*/

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CardDTO> search(@BeanParam CardSearchDTO search) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(em);

		BooleanQuery root = new BooleanQuery();

		QueryBuilder qb = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(Card.class).get();

		if (search.getName() != null) {
			org.apache.lucene.search.Query nameCriteria = qb.keyword()
					.onField("name").andField("engram").boostedTo(5)
					.matching(search.getName()).createQuery();
			root.add(nameCriteria, BooleanClause.Occur.MUST);
		}
		if (search.getType() != null) {
			org.apache.lucene.search.Query typeCriteria = qb.phrase()
					.withSlop(2).onField("type").sentence(search.getType())
					.createQuery();
			root.add(typeCriteria, BooleanClause.Occur.MUST);
		}
		if (search.getText() != null) {
			org.apache.lucene.search.Query textCriteria = qb.phrase()
					.withSlop(4).onField("text").sentence(search.getText())
					.createQuery();
			root.add(textCriteria, BooleanClause.Occur.MUST);
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
				.createFullTextQuery(root, Card.class);

		final List<Card> searchResults = jpaQuery.getResultList();
		final List<CardDTO> results = new ArrayList<CardDTO>();
		for (Card searchResult : searchResults) {
			CardDTO dto = new CardDTO(searchResult);
			results.add(dto);
		}
		// em.getTransaction().commit();
		// em.close();
		return results;
	}

}
