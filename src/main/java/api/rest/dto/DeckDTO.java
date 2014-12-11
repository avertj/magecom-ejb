package api.rest.dto;

import java.io.Serializable;

import persistance.entity.Deck;

import javax.persistence.EntityManager;

import java.util.Date;

import api.rest.dto.ColorDTO;
import api.rest.dto.NestedMemberDTO;

import java.util.Set;
import java.util.HashSet;

import api.rest.dto.NestedDeckTupleDTO;
import persistance.entity.tuple.DeckTuple;

import java.util.Iterator;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DeckDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private Date creationDate;
	private ColorDTO color;
	private NestedMemberDTO member;
	private Set<NestedDeckTupleDTO> cards = new HashSet<NestedDeckTupleDTO>();

	public DeckDTO() {
	}

	public DeckDTO(final Deck entity, boolean loadMember, boolean onlyFav) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.creationDate = entity.getCreationDate();
			this.color = new ColorDTO(entity.getColor());
			if (loadMember) {
				this.member = new NestedMemberDTO(entity.getMember());
			}
			Iterator<DeckTuple> iterCards = entity.getCards().iterator();
			while (iterCards.hasNext()) {
				DeckTuple element = iterCards.next();
				if (element.isFavorite() || !onlyFav) {
					this.cards.add(new NestedDeckTupleDTO(element));
				}
			}
		}
	}

	public Deck fromDTO(Deck entity, EntityManager em) {
		if (entity == null) {
			entity = new Deck();
		}
		entity.setName(this.name);
		entity.setDescription(this.description);
		entity.setCreationDate(this.creationDate);
		if (this.color != null) {
			entity.setColor(this.color.fromDTO(entity.getColor(), em));
		}
		if (this.member != null) {
			entity.setMember(this.member.fromDTO(entity.getMember(), em));
		}
		Iterator<DeckTuple> iterCards = entity.getCards().iterator();
		while (iterCards.hasNext()) {
			boolean found = false;
			DeckTuple deckTuple = iterCards.next();
			Iterator<NestedDeckTupleDTO> iterDtoCards = this.getCards()
					.iterator();
			while (iterDtoCards.hasNext()) {
				NestedDeckTupleDTO dtoDeckTuple = iterDtoCards.next();
				if (dtoDeckTuple.getId().equals(deckTuple.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				iterCards.remove();
			}
		}
		Iterator<NestedDeckTupleDTO> iterDtoCards = this.getCards().iterator();
		while (iterDtoCards.hasNext()) {
			boolean found = false;
			NestedDeckTupleDTO dtoDeckTuple = iterDtoCards.next();
			iterCards = entity.getCards().iterator();
			while (iterCards.hasNext()) {
				DeckTuple deckTuple = iterCards.next();
				if (dtoDeckTuple.getId().equals(deckTuple.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				Iterator<DeckTuple> resultIter = em
						.createQuery("SELECT DISTINCT d FROM DeckTuple d",
								DeckTuple.class).getResultList().iterator();
				while (resultIter.hasNext()) {
					DeckTuple result = resultIter.next();
					if (result.getId().equals(dtoDeckTuple.getId())) {
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

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public ColorDTO getColor() {
		return this.color;
	}

	public void setColor(final ColorDTO color) {
		this.color = color;
	}

	public NestedMemberDTO getMember() {
		return this.member;
	}

	public void setMember(final NestedMemberDTO member) {
		this.member = member;
	}

	public Set<NestedDeckTupleDTO> getCards() {
		return this.cards;
	}

	public void setCards(final Set<NestedDeckTupleDTO> cards) {
		this.cards = cards;
	}
}