package api.rest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.exception.ConstraintViolationException;

import persistance.entity.Member;
import util.AuthUtils;
import api.rest.dto.MemberDTO;
import api.rest.dto.PublicMemberDTO;

@Stateless
@Path("/member")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MemberEndpoint {
	@PersistenceContext(unitName = "magecom-ejb-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(MemberDTO dto) {
		Member entity = dto.fromDTO(null, em);
		try {
			em.persist(entity);
			em.flush();
		} catch (PersistenceException e) {
			Throwable t = e.getCause();
			if (t instanceof ConstraintViolationException) {
				ConstraintViolationException ex = (ConstraintViolationException) t;
				SQLException sqlex = (SQLException) ex.getSQLException();
				if (sqlex.getSQLState().equals("23505")) { // unique constraint
															// violation
					String message = sqlex.getMessage();
					String field = message.substring(
							(message.indexOf("(") + 1), message.indexOf(")"));
					System.out.println(field);
					return Response.status(409).entity("{\"field\": \"" + field + "\"}").build(); //conflit
				}
			}
		}

		return Response.created(
				UriBuilder.fromResource(MemberEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Member entity = em.find(Member.class, id);
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
		TypedQuery<Member> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT m FROM Member m LEFT JOIN FETCH m.decks LEFT JOIN FETCH m.combos LEFT JOIN FETCH m.collection WHERE m.id = :entityId ORDER BY m.id",
						Member.class);
		findByIdQuery.setParameter("entityId", id);
		Member entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		PublicMemberDTO dto = new PublicMemberDTO(entity, true);
		return Response.ok(dto).build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}/full")
	@Produces(MediaType.APPLICATION_JSON)
	public Response findByIdFull(@PathParam("id") Long id,
			@QueryParam("token") String token) {
		if (token != null) {
			TypedQuery<Member> findByIdQuery = em
					.createQuery(
							"SELECT DISTINCT m FROM Member m LEFT JOIN FETCH m.decks LEFT JOIN FETCH m.combos LEFT JOIN FETCH m.purchases LEFT JOIN FETCH m.collection WHERE m.id = :entityId ORDER BY m.id",
							Member.class);
			findByIdQuery.setParameter("entityId", id);
			Member entity;
			try {
				entity = findByIdQuery.getSingleResult();
			} catch (NoResultException nre) {
				entity = null;
			}
			if (entity == null) {
				return Response.status(Status.NOT_FOUND).build();
			}
			if (AuthUtils.validate(entity, token)) {
				MemberDTO dto = new MemberDTO(entity);
				return Response.ok(dto).build();
			}
		}
		return Response.status(401).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PublicMemberDTO> listAll(
			@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {
		TypedQuery<Member> findAllQuery = em
				.createQuery(
						"SELECT DISTINCT m FROM Member m LEFT JOIN FETCH m.decks LEFT JOIN FETCH m.combos LEFT JOIN FETCH m.purchases LEFT JOIN FETCH m.collection ORDER BY m.id",
						Member.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<Member> searchResults = findAllQuery.getResultList();
		final List<PublicMemberDTO> results = new ArrayList<PublicMemberDTO>();
		for (Member searchResult : searchResults) {
			PublicMemberDTO dto = new PublicMemberDTO(searchResult, false);
			results.add(dto);
		}
		return results;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, MemberDTO dto) {
		TypedQuery<Member> findByIdQuery = em
				.createQuery(
						"SELECT DISTINCT m FROM Member m WHERE m.id = :entityId ORDER BY m.id",
						Member.class);
		findByIdQuery.setParameter("entityId", id);
		Member entity;
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
