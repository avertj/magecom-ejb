package api.rest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import persistance.entity.Combo;
import persistance.entity.Deck;
import persistance.entity.Member;
import persistance.entity.Purchase;
import persistance.entity.tuple.CollectionTuple;

@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class MemberDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Long id;
	protected String lastName;
	protected String firstName;
	protected String address;
	protected String additionalInformation;
	protected String zipCode;
	protected String city;
	protected String country;
	protected String email;
	protected String username;
	protected String password;
	protected String token;
	protected Date creationDate;
	protected Set<DeckDTO> decks = new HashSet<DeckDTO>();
	protected Set<ComboDTO> combos = new HashSet<ComboDTO>();
	protected Set<PurchaseDTO> purchases = new HashSet<PurchaseDTO>();
	protected Set<SimpleTupleDTO> collection = new HashSet<SimpleTupleDTO>();

	public MemberDTO() {
	}

	public MemberDTO(final Member entity) {
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
			// this.password = entity.getPassword();
			this.creationDate = entity.getCreationDate();
			Iterator<Deck> iterDecks = entity.getDecks().iterator();
			while (iterDecks.hasNext()) {
				Deck element = iterDecks.next();
				this.decks.add(new DeckDTO(element, false, true));
			}
			Iterator<Combo> iterCombos = entity.getCombos().iterator();
			while (iterCombos.hasNext()) {
				Combo element = iterCombos.next();
				this.combos.add(new ComboDTO(element, false));
			}
			Iterator<Purchase> iterPurchases = entity.getPurchases().iterator();
			while (iterPurchases.hasNext()) {
				Purchase element = iterPurchases.next();
				this.purchases.add(new PurchaseDTO(element));
			}
			Iterator<CollectionTuple> iterCollection = entity.getCollection()
					.iterator();
			while (iterCollection.hasNext()) {
				CollectionTuple element = iterCollection.next();
				this.collection.add(new SimpleTupleDTO(element));
			}
		}
	}

	public Member fromDTO(Member entity, EntityManager em) {
		if (entity == null) {
			if (this.id != null)
				entity = em.find(Member.class, this.id);
			else {
				entity = new Member();
				entity.setCreationDate(new Date());
				entity.setEmail(this.email);
				entity.setUsername(this.username);

			}
		}
		if (this.lastName != null)
			entity.setLastName(this.lastName);
		if (this.firstName != null)
			entity.setFirstName(this.firstName);
		if (this.address != null)
			entity.setAddress(this.address);
		if (this.additionalInformation != null) {
			if (this.additionalInformation.equals(""))
				entity.setAdditionalInformation(null);
			else
				entity.setAdditionalInformation(this.additionalInformation);
		}
		if (this.zipCode != null)
			entity.setZipCode(this.zipCode);
		if (this.city != null)
			entity.setCity(this.city);
		if (this.country != null)
			entity.setCountry(this.country);
		if (this.password != null)
			entity.setPassword(this.password);
		// entity.setCreationDate(this.creationDate);
		/*
		 * Iterator<Deck> iterDecks = entity.getDecks().iterator(); while
		 * (iterDecks.hasNext()) { boolean found = false; Deck deck =
		 * iterDecks.next(); Iterator<DeckDTO> iterDtoDecks =
		 * this.getDecks().iterator(); while (iterDtoDecks.hasNext()) { DeckDTO
		 * dtoDeck = iterDtoDecks.next(); if
		 * (dtoDeck.getId().equals(deck.getId())) { found = true; break; } } if
		 * (found == false) { iterDecks.remove(); } } Iterator<DeckDTO>
		 * iterDtoDecks = this.getDecks().iterator(); while
		 * (iterDtoDecks.hasNext()) { boolean found = false; DeckDTO dtoDeck =
		 * iterDtoDecks.next(); iterDecks = entity.getDecks().iterator(); while
		 * (iterDecks.hasNext()) { Deck deck = iterDecks.next(); if
		 * (dtoDeck.getId().equals(deck.getId())) { found = true; break; } } if
		 * (found == false) { Iterator<Deck> resultIter = em
		 * .createQuery("SELECT DISTINCT d FROM Deck d",
		 * Deck.class).getResultList().iterator(); while (resultIter.hasNext())
		 * { Deck result = resultIter.next(); if
		 * (result.getId().equals(dtoDeck.getId())) {
		 * entity.getDecks().add(result); break; } } } } Iterator<Combo>
		 * iterCombos = entity.getCombos().iterator(); while
		 * (iterCombos.hasNext()) { boolean found = false; Combo combo =
		 * iterCombos.next(); Iterator<ComboDTO> iterDtoCombos =
		 * this.getCombos().iterator(); while (iterDtoCombos.hasNext()) {
		 * ComboDTO dtoCombo = iterDtoCombos.next(); if
		 * (dtoCombo.getId().equals(combo.getId())) { found = true; break; } }
		 * if (found == false) { iterCombos.remove(); } } Iterator<ComboDTO>
		 * iterDtoCombos = this.getCombos().iterator(); while
		 * (iterDtoCombos.hasNext()) { boolean found = false; ComboDTO dtoCombo
		 * = iterDtoCombos.next(); iterCombos = entity.getCombos().iterator();
		 * while (iterCombos.hasNext()) { Combo combo = iterCombos.next(); if
		 * (dtoCombo.getId().equals(combo.getId())) { found = true; break; } }
		 * if (found == false) { Iterator<Combo> resultIter = em
		 * .createQuery("SELECT DISTINCT c FROM Combo c",
		 * Combo.class).getResultList().iterator(); while (resultIter.hasNext())
		 * { Combo result = resultIter.next(); if
		 * (result.getId().equals(dtoCombo.getId())) {
		 * entity.getCombos().add(result); break; } } } } Iterator<Purchase>
		 * iterPurchases = entity.getPurchases().iterator(); while
		 * (iterPurchases.hasNext()) { boolean found = false; Purchase purchase
		 * = iterPurchases.next(); Iterator<PurchaseDTO> iterDtoPurchases =
		 * this.getPurchases() .iterator(); while (iterDtoPurchases.hasNext()) {
		 * NestedPurchaseDTO dtoPurchase = iterDtoPurchases.next(); if
		 * (dtoPurchase.getId().equals(purchase.getId())) { found = true; break;
		 * } } if (found == false) { iterPurchases.remove(); } }
		 * Iterator<NestedPurchaseDTO> iterDtoPurchases = this.getPurchases()
		 * .iterator(); while (iterDtoPurchases.hasNext()) { boolean found =
		 * false; NestedPurchaseDTO dtoPurchase = iterDtoPurchases.next();
		 * iterPurchases = entity.getPurchases().iterator(); while
		 * (iterPurchases.hasNext()) { Purchase purchase = iterPurchases.next();
		 * if (dtoPurchase.getId().equals(purchase.getId())) { found = true;
		 * break; } } if (found == false) { Iterator<Purchase> resultIter = em
		 * .createQuery("SELECT DISTINCT p FROM Purchase p",
		 * Purchase.class).getResultList().iterator(); while
		 * (resultIter.hasNext()) { Purchase result = resultIter.next(); if
		 * (result.getId().equals(dtoPurchase.getId())) {
		 * entity.getPurchases().add(result); break; } } } }
		 * Iterator<CollectionTuple> iterCollection = entity.getCollection()
		 * .iterator(); while (iterCollection.hasNext()) { boolean found =
		 * false; CollectionTuple collectionTuple = iterCollection.next();
		 * Iterator<SimpleTupleDTO> iterDtoCollection = this.getCollection()
		 * .iterator(); while (iterDtoCollection.hasNext()) { SimpleTupleDTO
		 * dtoCollectionTuple = iterDtoCollection.next(); if
		 * (dtoCollectionTuple.getId().equals(collectionTuple.getId())) { found
		 * = true; break; } } if (found == false) { iterCollection.remove(); } }
		 * Iterator<SimpleTupleDTO> iterDtoCollection = this.getCollection()
		 * .iterator(); while (iterDtoCollection.hasNext()) { boolean found =
		 * false; SimpleTupleDTO dtoCollectionTuple = iterDtoCollection.next();
		 * iterCollection = entity.getCollection().iterator(); while
		 * (iterCollection.hasNext()) { CollectionTuple collectionTuple =
		 * iterCollection.next(); if
		 * (dtoCollectionTuple.getId().equals(collectionTuple.getId())) { found
		 * = true; break; } } if (found == false) { Iterator<CollectionTuple>
		 * resultIter = em .createQuery(
		 * "SELECT DISTINCT c FROM CollectionTuple c",
		 * CollectionTuple.class).getResultList() .iterator(); while
		 * (resultIter.hasNext()) { CollectionTuple result = resultIter.next();
		 * if (result.getId().equals(dtoCollectionTuple.getId())) {
		 * entity.getCollection().add(result); break; } } } } entity =
		 * em.merge(entity);
		 */
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

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Set<DeckDTO> getDecks() {
		return this.decks;
	}

	public void setDecks(final Set<DeckDTO> decks) {
		this.decks = decks;
	}

	public Set<ComboDTO> getCombos() {
		return this.combos;
	}

	public void setCombos(final Set<ComboDTO> combos) {
		this.combos = combos;
	}

	public Set<PurchaseDTO> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(final Set<PurchaseDTO> purchases) {
		this.purchases = purchases;
	}

	public Set<SimpleTupleDTO> getCollection() {
		return this.collection;
	}

	public void setCollection(final Set<SimpleTupleDTO> collection) {
		this.collection = collection;
	}
}