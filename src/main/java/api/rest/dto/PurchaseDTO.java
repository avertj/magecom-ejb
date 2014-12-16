package api.rest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;

import persistance.entity.Purchase;
import persistance.entity.tuple.PurchaseTuple;

@XmlRootElement
public class PurchaseDTO implements Serializable {
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
	private MemberDTO member;
	private Set<SimpleTupleDTO> cards = new HashSet<SimpleTupleDTO>();

	public PurchaseDTO() {
	}

	public PurchaseDTO(final Purchase entity) {
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
			this.member = new PublicMemberDTO(entity.getMember(), false);
			Iterator<PurchaseTuple> iterCards = entity.getCards().iterator();
			while (iterCards.hasNext()) {
				PurchaseTuple element = iterCards.next();
				this.cards.add(new SimpleTupleDTO(element));
			}
		}
	}

	public Purchase fromDTO(Purchase entity, EntityManager em) {
		if (entity == null) {
			entity = new Purchase();
			entity.setCreationDate(new Date());
			entity.setMember(this.member.fromDTO(null, em));
		}
		entity.setLastName(this.lastName);
		entity.setFirstName(this.firstName);
		entity.setAddress(this.address);
		entity.setAdditionalInformation(this.additionalInformation);
		entity.setZipCode(this.zipCode);
		entity.setCity(this.city);
		entity.setCountry(this.country);
		entity.setTotal(this.total);
		Set<PurchaseTuple> cards = new HashSet<PurchaseTuple>();
		Iterator<SimpleTupleDTO> iterDtoCards = this.getCards().iterator();
		while (iterDtoCards.hasNext()) {
			cards.add((PurchaseTuple) iterDtoCards.next().fromDTO(
					new PurchaseTuple(), em));
		}
		entity.setCards(cards);
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

	public MemberDTO getMember() {
		return this.member;
	}

	public void setMember(final MemberDTO member) {
		this.member = member;
	}

	public Set<SimpleTupleDTO> getCards() {
		return this.cards;
	}

	public void setCards(final Set<SimpleTupleDTO> cards) {
		this.cards = cards;
	}
}