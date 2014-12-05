package api.rest.dto;

import java.io.Serializable;
import persistance.entity.Purchase;
import javax.persistence.EntityManager;
import java.util.Date;
import api.rest.dto.NestedMemberDTO;
import java.util.Set;
import java.util.HashSet;
import persistance.entity.tuple.PurchaseTuple;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PurchaseDTO implements Serializable {

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
	private NestedMemberDTO member;
	private Set<NestedSimpleTupleDTO> cards = new HashSet<NestedSimpleTupleDTO>();

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
			this.member = new NestedMemberDTO(entity.getMember());
			Iterator<PurchaseTuple> iterCards = entity.getCards().iterator();
			while (iterCards.hasNext()) {
				PurchaseTuple element = iterCards.next();
				this.cards.add(new NestedSimpleTupleDTO(element));
			}
		}
	}

	public Purchase fromDTO(Purchase entity, EntityManager em) {
		if (entity == null) {
			entity = new Purchase();
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
		if (this.member != null) {
			entity.setMember(this.member.fromDTO(entity.getMember(), em));
		}
		Iterator<PurchaseTuple> iterCards = entity.getCards().iterator();
		while (iterCards.hasNext()) {
			boolean found = false;
			PurchaseTuple purchaseTuple = iterCards.next();
			Iterator<NestedSimpleTupleDTO> iterDtoCards = this.getCards()
					.iterator();
			while (iterDtoCards.hasNext()) {
				NestedSimpleTupleDTO dtoPurchaseTuple = iterDtoCards.next();
				if (dtoPurchaseTuple.getId().equals(purchaseTuple.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				iterCards.remove();
			}
		}
		Iterator<NestedSimpleTupleDTO> iterDtoCards = this.getCards()
				.iterator();
		while (iterDtoCards.hasNext()) {
			boolean found = false;
			NestedSimpleTupleDTO dtoPurchaseTuple = iterDtoCards.next();
			iterCards = entity.getCards().iterator();
			while (iterCards.hasNext()) {
				PurchaseTuple purchaseTuple = iterCards.next();
				if (dtoPurchaseTuple.getId().equals(purchaseTuple.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				Iterator<PurchaseTuple> resultIter = em
						.createQuery("SELECT DISTINCT p FROM PurchaseTuple p",
								PurchaseTuple.class).getResultList().iterator();
				while (resultIter.hasNext()) {
					PurchaseTuple result = resultIter.next();
					if (result.getId().equals(dtoPurchaseTuple.getId())) {
						entity.getCards().add(result);
						break;
					}
				}
			}
		}
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

	public NestedMemberDTO getMember() {
		return this.member;
	}

	public void setMember(final NestedMemberDTO member) {
		this.member = member;
	}

	public Set<NestedSimpleTupleDTO> getCards() {
		return this.cards;
	}

	public void setCards(final Set<NestedSimpleTupleDTO> cards) {
		this.cards = cards;
	}
}