package api.rest.dto;

import java.io.Serializable;
import persistance.entity.Member;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;

public class NestedMemberDTO implements Serializable {

	private Long id;
	private String lastName;
	private String firstName;
	private String address;
	private String additionalInformation;
	private String zipCode;
	private String city;
	private String country;
	private String email;
	private String username;
	private String password;
	private Date creationDate;

	public NestedMemberDTO() {
	}

	public NestedMemberDTO(final Member entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.lastName = entity.getLastName();
			this.firstName = entity.getFirstName();
			this.address = entity.getAddress();
			this.additionalInformation = entity.getAdditionalInformation();
			this.zipCode = entity.getZipCode();
			this.city = entity.getCity();
			this.country = entity.getCountry();
			this.email = entity.getEmail();
			this.username = entity.getUsername();
			this.password = entity.getPassword();
			this.creationDate = entity.getCreationDate();
		}
	}

	public Member fromDTO(Member entity, EntityManager em) {
		if (entity == null) {
			entity = new Member();
		}
		if (this.id != null) {
			TypedQuery<Member> findByIdQuery = em.createQuery(
					"SELECT DISTINCT m FROM Member m WHERE m.id = :entityId",
					Member.class);
			findByIdQuery.setParameter("entityId", this.id);
			try {
				entity = findByIdQuery.getSingleResult();
			} catch (javax.persistence.NoResultException nre) {
				entity = null;
			}
			return entity;
		}
		entity.setLastName(this.lastName);
		entity.setFirstName(this.firstName);
		entity.setAddress(this.address);
		entity.setAdditionalInformation(this.additionalInformation);
		entity.setZipCode(this.zipCode);
		entity.setCity(this.city);
		entity.setCountry(this.country);
		entity.setEmail(this.email);
		entity.setUsername(this.username);
		entity.setPassword(this.password);
		entity.setCreationDate(this.creationDate);
		entity = em.merge(entity);
		return entity;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(final String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public String getAdditionalInformation() {
		return this.additionalInformation;
	}

	public void setAdditionalInformation(final String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}
}