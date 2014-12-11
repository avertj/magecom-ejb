package api.rest;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import persistance.entity.Member;
import util.AuthUtils;
import api.rest.dto.SessionMemberDTO;

@Stateless
@Path("/auth")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class AuthEndpoint {
	@PersistenceContext(unitName = "magecom-ejb-persistence-unit")
	private EntityManager em;

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(SessionMemberDTO member) {

		TypedQuery<Member> findByUsername = em.createNamedQuery(
				"findByUsername", Member.class);
		findByUsername.setParameter("username", member.getUsername());
		Member m = findByUsername.getSingleResult();
		try {
			String token = AuthUtils.login(m, member.getPassword());
			return Response.ok(new SessionMemberDTO(m, token)).build();
		} catch (LoginException e) {
			return Response.status(401).build(); // erreur de login (code 401 -
													// Unauthorized)
		}
	}

	@POST
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response validate(SessionMemberDTO member) {
		Member m = new Member();
		m.setUsername(member.getUsername());
		try {
			if (AuthUtils.validate(m, member.getToken())) {
				return Response.ok().build();
			} else {
				return Response.status(401).build();
			}
		} catch (LoginException e) {
			return Response.status(403).build();
		}
	}

	@GET
	@Path("/logout")
	// logout côté angular ? delete sessionStorage + member var
	public Response logout(@Context HttpServletRequest req) {
		return Response.ok().build();
	}

}
