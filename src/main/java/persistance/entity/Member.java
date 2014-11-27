package persistance.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import persistance.entity.tuple.CollectionTuple;

/**
 * Entity implementation class for Entity: Member
 *
 */

@Entity
public class Member implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "member_sequence")
	@SequenceGenerator(name = "member_sequence", sequenceName = "member_sequence", allocationSize = 1)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(updatable = true, nullable = false)
	private String lastName;

	@Column(updatable = false, nullable = false)
	private String firstName;

	@Column(updatable = true, nullable = false)
	private String address;

	@Column(updatable = true, nullable = true)
	private String additionalInformation;

	@Column(updatable = true, nullable = false)
	private String zipCode;

	@Column(updatable = true, nullable = false)
	private String city;

	@Column(updatable = true, nullable = false)
	private String country;

	@Column(updatable = true, nullable = false)
	private String email;

	@Column(updatable = true, nullable = false)
	private String username;

	@Column(updatable = true, nullable = false)
	private String password;

	@Temporal(TemporalType.DATE)
	@Column(updatable = false, nullable = false)
	private Date creationDate;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
	private Set<Deck> decks = new HashSet<Deck>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
	private Set<Combo> combos = new HashSet<Combo>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "member")
	private Set<Purchase> purchases = new HashSet<Purchase>();

	@OneToMany
	/* @JoinTable(joinColumns = @JoinColumn(name = "MEMBER", nullable = false)) */
	@JoinColumn(name = "member_id", nullable = false)
	private Set<CollectionTuple> collection;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Set<Deck> getDecks() {
		return decks;
	}

	public void setDecks(Set<Deck> decks) {
		this.decks = decks;
	}

	public Set<Combo> getCombos() {
		return combos;
	}

	public void setCombos(Set<Combo> combos) {
		this.combos = combos;
	}

	public Set<Purchase> getPurchases() {
		return purchases;
	}

	public void setPurchases(Set<Purchase> purchases) {
		this.purchases = purchases;
	}

	public Set<CollectionTuple> getCollection() {
		return collection;
	}

	public void setCollection(Set<CollectionTuple> collection) {
		this.collection = collection;
	}

}
