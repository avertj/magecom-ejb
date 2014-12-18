package api.rest;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import persistance.entity.Member;
import persistance.entity.Purchase;
import util.AuthUtils;
import api.rest.dto.PurchaseDTO;

@Stateless
@Path("/purchase")
public class PurchaseEndpoint {
	@PersistenceContext(unitName = "magecom-ejb-persistence-unit")
	private EntityManager em;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response create(PurchaseDTO dto) {
		Member member = em.find(Member.class, dto.getMember().getId());
		if (AuthUtils.validate(member, dto.getMember().getToken())) {
			Purchase entity = dto.fromDTO(null, em);
			em.persist(entity);
			return Response.created(
					UriBuilder.fromResource(DeckEndpoint.class)
							.path(String.valueOf(entity.getId())).build())
					.build();
		} else {
			return Response.status(Status.UNAUTHORIZED).build();
		}
	}

	/*
	 * @DELETE
	 * 
	 * @Path("/{id:[0-9][0-9]*}") public Response deleteById(@PathParam("id")
	 * Long id) { Purchase entity = em.find(Purchase.class, id); if (entity ==
	 * null) { return Response.status(Status.NOT_FOUND).build(); }
	 * em.remove(entity); return Response.noContent().build(); }
	 * 
	 * @GET
	 * 
	 * @Path("/{id:[0-9][0-9]*}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public Response
	 * findById(@PathParam("id") Long id) { TypedQuery<Purchase> findByIdQuery =
	 * em .createQuery(
	 * "SELECT DISTINCT p FROM Purchase p LEFT JOIN FETCH p.member LEFT JOIN FETCH p.cards WHERE p.id = :entityId ORDER BY p.id"
	 * , Purchase.class); findByIdQuery.setParameter("entityId", id); Purchase
	 * entity; try { entity = findByIdQuery.getSingleResult(); } catch
	 * (NoResultException nre) { entity = null; } if (entity == null) { return
	 * Response.status(Status.NOT_FOUND).build(); } PurchaseDTO dto = new
	 * PurchaseDTO(entity); return Response.ok(dto).build(); }
	 * 
	 * @GET
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public List<PurchaseDTO> listAll(
	 * 
	 * @QueryParam("start") Integer startPosition,
	 * 
	 * @QueryParam("max") Integer maxResult) { TypedQuery<Purchase> findAllQuery
	 * = em .createQuery(
	 * "SELECT DISTINCT p FROM Purchase p LEFT JOIN FETCH p.member LEFT JOIN FETCH p.cards ORDER BY p.id"
	 * , Purchase.class); if (startPosition != null) {
	 * findAllQuery.setFirstResult(startPosition); } if (maxResult != null) {
	 * findAllQuery.setMaxResults(maxResult); } final List<Purchase>
	 * searchResults = findAllQuery.getResultList(); final List<PurchaseDTO>
	 * results = new ArrayList<PurchaseDTO>(); for (Purchase searchResult :
	 * searchResults) { PurchaseDTO dto = new PurchaseDTO(searchResult);
	 * results.add(dto); } return results; }
	 * 
	 * @PUT
	 * 
	 * @Path("/{id:[0-9][0-9]*}")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON) public Response
	 * update(@PathParam("id") Long id, PurchaseDTO dto) { TypedQuery<Purchase>
	 * findByIdQuery = em .createQuery(
	 * "SELECT DISTINCT p FROM Purchase p LEFT JOIN FETCH p.member LEFT JOIN FETCH p.cards WHERE p.id = :entityId ORDER BY p.id"
	 * , Purchase.class); findByIdQuery.setParameter("entityId", id); Purchase
	 * entity; try { entity = findByIdQuery.getSingleResult(); } catch
	 * (NoResultException nre) { entity = null; } entity = dto.fromDTO(entity,
	 * em); try { entity = em.merge(entity); } catch (OptimisticLockException e)
	 * { return Response.status(Response.Status.CONFLICT)
	 * .entity(e.getEntity()).build(); } return Response.noContent().build(); }
	 */
}
