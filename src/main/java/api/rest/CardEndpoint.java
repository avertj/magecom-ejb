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

import org.codehaus.jackson.map.annotate.JsonSerialize;

import persistance.entity.Card;
import api.rest.dto.CardDTO;
import api.rest.dto.search.CardSearchDTO;

/**
 * 
 */
@Stateless
@Path("/card")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CardEndpoint {
	@PersistenceContext(unitName = "magecom-ejb-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(CardDTO dto) {
		Card entity = dto.fromDTO(null, em);
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(CardEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Card entity = em.find(Card.class, id);
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

	@PUT
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
	}

	public List<Card> search(@BeanParam CardSearchDTO search) {
		return null;
	}

}
