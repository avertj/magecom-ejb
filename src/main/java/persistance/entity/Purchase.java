package persistance.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import persistance.entity.tuple.PurchaseTuple;

@Entity
public class Purchase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "purchase_sequence")
	@SequenceGenerator(name = "purchase_sequence", sequenceName = "purchase_sequence", allocationSize = 1)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(updatable = false, nullable = false)
	private String lastName;

	@Column(updatable = false, nullable = false)
	private String firstName;

	@Column(updatable = false, nullable = false)
	private String address;

	@Column(updatable = false, nullable = true)
	private String additionalInformation;

	@Column(updatable = false, nullable = false)
	private String zipCode;

	@Column(updatable = false, nullable = false)
	private String city;

	@Column(updatable = false, nullable = false)
	private String country;

	@Column(updatable = false, nullable = false)
	private Float total;

	@Temporal(TemporalType.DATE)
	@Column(updatable = false, nullable = false)
	private Date creationDate;

	@ManyToOne
	@JoinColumn(updatable = false, nullable = false)
	private Member member;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "purchase_id", nullable = false)
	private Set<PurchaseTuple> cards = new HashSet<PurchaseTuple>();

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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Set<PurchaseTuple> getCards() {
		return cards;
	}

	public void setCards(Set<PurchaseTuple> cards) {
		this.cards = cards;
	}

}
