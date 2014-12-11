package api.rest.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import persistance.entity.Purchase;

public class NestedPurchaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String lastName;
	private String firstName;
	private String address;
	private String additionalInformation;
	private String zipCode;
	private String city;
	private String country;
	private Float total;
	private Date creationDate;

	public NestedPurchaseDTO() {
	}

	public NestedPurchaseDTO(final Purchase entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.lastName = entity.getLastName();
			this.firstName = entity.getFirstName();
			this.address = entity.getAddress();
			this.additionalInformation = entity.getAdditionalInformation();
			this.zipCode = entity.getZipCode();
			this.city = entity.getCity();
			this.country = entity.getCountry();
			this.total = entity.getTotal();
			this.creationDate = entity.getCreationDate();
		}
	}

	public Purchase fromDTO(Purchase entity, EntityManager em) {
		if (entity == null) {
			entity = new Purchase();
		}
		if (this.id != null) {
			TypedQuery<Purchase> findByIdQuery = em.createQuery(
					"SELECT DISTINCT p FROM Purchase p WHERE p.id = :entityId",
					Purchase.class);
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
		entity.setTotal(this.total);
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

	public Float getTotal() {
		return this.total;
	}

	public void setTotal(final Float total) {
		this.total = total;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}
}