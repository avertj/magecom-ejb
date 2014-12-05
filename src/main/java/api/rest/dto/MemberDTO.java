package api.rest.dto;

import java.io.Serializable;

import persistance.entity.Member;

import javax.persistence.EntityManager;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import api.rest.dto.NestedDeckDTO;
import persistance.entity.Deck;

import java.util.Iterator;

import api.rest.dto.NestedSimpleTupleDTO;
import persistance.entity.Combo;
import api.rest.dto.NestedPurchaseDTO;
import persistance.entity.Purchase;
import persistance.entity.tuple.CollectionTuple;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement
public class MemberDTO implements Serializable {

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
	private Set<DeckDTO> decks = new HashSet<DeckDTO>();
	private Set<ComboDTO> combos = new HashSet<ComboDTO>();
	private Set<NestedPurchaseDTO> purchases = new HashSet<NestedPurchaseDTO>();
	private Set<NestedSimpleTupleDTO> collection = new HashSet<NestedSimpleTupleDTO>();

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
			this.password = entity.getPassword();
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
				this.purchases.add(new NestedPurchaseDTO(element));
			}
			Iterator<CollectionTuple> iterCollection = entity.getCollection()
					.iterator();
			while (iterCollection.hasNext()) {
				CollectionTuple element = iterCollection.next();
				this.collection.add(new NestedSimpleTupleDTO(element));
			}
		}
	}

	public Member fromDTO(Member entity, EntityManager em) {
		if (entity == null) {
			entity = new Member();
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
		Iterator<Deck> iterDecks = entity.getDecks().iterator();
		while (iterDecks.hasNext()) {
			boolean found = false;
			Deck deck = iterDecks.next();
			Iterator<DeckDTO> iterDtoDecks = this.getDecks().iterator();
			while (iterDtoDecks.hasNext()) {
				DeckDTO dtoDeck = iterDtoDecks.next();
				if (dtoDeck.getId().equals(deck.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				iterDecks.remove();
			}
		}
		Iterator<DeckDTO> iterDtoDecks = this.getDecks().iterator();
		while (iterDtoDecks.hasNext()) {
			boolean found = false;
			DeckDTO dtoDeck = iterDtoDecks.next();
			iterDecks = entity.getDecks().iterator();
			while (iterDecks.hasNext()) {
				Deck deck = iterDecks.next();
				if (dtoDeck.getId().equals(deck.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				Iterator<Deck> resultIter = em
						.createQuery("SELECT DISTINCT d FROM Deck d",
								Deck.class).getResultList().iterator();
				while (resultIter.hasNext()) {
					Deck result = resultIter.next();
					if (result.getId().equals(dtoDeck.getId())) {
						entity.getDecks().add(result);
						break;
					}
				}
			}
		}
		Iterator<Combo> iterCombos = entity.getCombos().iterator();
		while (iterCombos.hasNext()) {
			boolean found = false;
			Combo combo = iterCombos.next();
			Iterator<ComboDTO> iterDtoCombos = this.getCombos()
					.iterator();
			while (iterDtoCombos.hasNext()) {
				ComboDTO dtoCombo = iterDtoCombos.next();
				if (dtoCombo.getId().equals(combo.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				iterCombos.remove();
			}
		}
		Iterator<ComboDTO> iterDtoCombos = this.getCombos().iterator();
		while (iterDtoCombos.hasNext()) {
			boolean found = false;
			ComboDTO dtoCombo = iterDtoCombos.next();
			iterCombos = entity.getCombos().iterator();
			while (iterCombos.hasNext()) {
				Combo combo = iterCombos.next();
				if (dtoCombo.getId().equals(combo.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				Iterator<Combo> resultIter = em
						.createQuery("SELECT DISTINCT c FROM Combo c",
								Combo.class).getResultList().iterator();
				while (resultIter.hasNext()) {
					Combo result = resultIter.next();
					if (result.getId().equals(dtoCombo.getId())) {
						entity.getCombos().add(result);
						break;
					}
				}
			}
		}
		Iterator<Purchase> iterPurchases = entity.getPurchases().iterator();
		while (iterPurchases.hasNext()) {
			boolean found = false;
			Purchase purchase = iterPurchases.next();
			Iterator<NestedPurchaseDTO> iterDtoPurchases = this.getPurchases()
					.iterator();
			while (iterDtoPurchases.hasNext()) {
				NestedPurchaseDTO dtoPurchase = iterDtoPurchases.next();
				if (dtoPurchase.getId().equals(purchase.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				iterPurchases.remove();
			}
		}
		Iterator<NestedPurchaseDTO> iterDtoPurchases = this.getPurchases()
				.iterator();
		while (iterDtoPurchases.hasNext()) {
			boolean found = false;
			NestedPurchaseDTO dtoPurchase = iterDtoPurchases.next();
			iterPurchases = entity.getPurchases().iterator();
			while (iterPurchases.hasNext()) {
				Purchase purchase = iterPurchases.next();
				if (dtoPurchase.getId().equals(purchase.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				Iterator<Purchase> resultIter = em
						.createQuery("SELECT DISTINCT p FROM Purchase p",
								Purchase.class).getResultList().iterator();
				while (resultIter.hasNext()) {
					Purchase result = resultIter.next();
					if (result.getId().equals(dtoPurchase.getId())) {
						entity.getPurchases().add(result);
						break;
					}
				}
			}
		}
		Iterator<CollectionTuple> iterCollection = entity.getCollection()
				.iterator();
		while (iterCollection.hasNext()) {
			boolean found = false;
			CollectionTuple collectionTuple = iterCollection.next();
			Iterator<NestedSimpleTupleDTO> iterDtoCollection = this
					.getCollection().iterator();
			while (iterDtoCollection.hasNext()) {
				NestedSimpleTupleDTO dtoCollectionTuple = iterDtoCollection
						.next();
				if (dtoCollectionTuple.getId().equals(collectionTuple.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				iterCollection.remove();
			}
		}
		Iterator<NestedSimpleTupleDTO> iterDtoCollection = this.getCollection()
				.iterator();
		while (iterDtoCollection.hasNext()) {
			boolean found = false;
			NestedSimpleTupleDTO dtoCollectionTuple = iterDtoCollection.next();
			iterCollection = entity.getCollection().iterator();
			while (iterCollection.hasNext()) {
				CollectionTuple collectionTuple = iterCollection.next();
				if (dtoCollectionTuple.getId().equals(collectionTuple.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				Iterator<CollectionTuple> resultIter = em
						.createQuery(
								"SELECT DISTINCT c FROM CollectionTuple c",
								CollectionTuple.class).getResultList()
						.iterator();
				while (resultIter.hasNext()) {
					CollectionTuple result = resultIter.next();
					if (result.getId().equals(dtoCollectionTuple.getId())) {
						entity.getCollection().add(result);
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

	public Set<NestedPurchaseDTO> getPurchases() {
		return this.purchases;
	}

	public void setPurchases(final Set<NestedPurchaseDTO> purchases) {
		this.purchases = purchases;
	}

	public Set<NestedSimpleTupleDTO> getCollection() {
		return this.collection;
	}

	public void setCollection(final Set<NestedSimpleTupleDTO> collection) {
		this.collection = collection;
	}
}