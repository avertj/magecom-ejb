package api.rest.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import persistance.entity.Member;

@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SessionMemberDTO extends MemberDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public SessionMemberDTO() {
	}

	public SessionMemberDTO(final Member entity, String token) {
		if (entity != null) {
			this.id = entity.getId();
			this.lastName = entity.getLastName();
			this.firstName = entity.getFirstName();
			this.email = entity.getEmail();
			this.username = entity.getUsername();
			this.token = token;
		}
	}
}