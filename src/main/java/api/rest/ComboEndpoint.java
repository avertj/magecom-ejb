package api.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import api.rest.dto.ComboDTO;
import persistance.entity.Combo;

/**
 * 
 */
@Stateless
@Path("/combo")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ComboEndpoint {
	@PersistenceContext(unitName = "magecom-ejb-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes("application/json")
	public Response create(ComboDTO dto) {
		Combo entity = dto.fromDTO(null, em);
		em.persist(entity);
		return Response.created(
				UriBuilder.fromResource(ComboEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Combo entity = em.find(Combo.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		em.remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
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
	@Produces("application/json")
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
	@Produces("application/json")
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
	@Produces("application/json")
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
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, ComboDTO dto) {
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
		entity = dto.fromDTO(entity, em);
		try {
			entity = em.merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}
		return Response.noContent().build();
	}
}
