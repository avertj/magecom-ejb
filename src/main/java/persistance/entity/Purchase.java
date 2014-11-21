package persistance.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Purchase
 *
 */

@Entity
public class Purchase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(name = "last_name", updatable = false, nullable = false)
	private String lastName;

	@Column(name = "first_name", updatable = false, nullable = false)
	private String firstName;

	@Column(updatable = false, nullable = false)
	private String address;

	@Column(name = "additional_information", updatable = false, nullable = true)
	private String additionalInformation;

	@Column(name = "zip_code", updatable = false, nullable = false)
	private String zipCode;

	@Column(updatable = false, nullable = false)
	private String city;

	@Column(updatable = false, nullable = false)
	private String country;

	@Column(updatable = false, nullable = false)
	private Float total;

	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_PURCHASE", updatable = false, nullable = false)
	private java.util.Date datePurchase;

	@ManyToOne
	@JoinColumn(name="MEMBER_ID", updatable = false, nullable=false)
	private Member member;
	
	// ManyToMany cards (+ cardsQuantity);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Float getTotal() {
		return total;
	}

	public void setTotal(Float total) {
		this.total = total;
	}

	public java.util.Date getDatePurchase() {
		return datePurchase;
	}

	public void setDatePurchase(java.util.Date datePurchase) {
		this.datePurchase = datePurchase;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}
