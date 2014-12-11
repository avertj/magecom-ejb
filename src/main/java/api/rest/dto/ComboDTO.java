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
import persistance.entity.tuple.ComboTuple;

@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ComboDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private Date creationDate;
	private ColorDTO color;
	private NestedMemberDTO member;
	private Set<NestedSimpleTupleDTO> cards = new HashSet<NestedSimpleTupleDTO>();

	public ComboDTO() {
	}

	public ComboDTO(final Combo entity, boolean loadMember) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.creationDate = entity.getCreationDate();
			this.color = new ColorDTO(entity.getColor());
			if (loadMember) {
				this.member = new NestedMemberDTO(entity.getMember());
			}
			Iterator<ComboTuple> iterCards = entity.getCards().iterator();
			while (iterCards.hasNext()) {
				ComboTuple element = iterCards.next();
				this.cards.add(new NestedSimpleTupleDTO(element));
			}
		}
	}

	public Combo fromDTO(Combo entity, EntityManager em) {
		if (entity == null) {
			entity = new Combo();
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
		Iterator<ComboTuple> iterCards = entity.getCards().iterator();
		while (iterCards.hasNext()) {
			boolean found = false;
			ComboTuple comboTuple = iterCards.next();
			Iterator<NestedSimpleTupleDTO> iterDtoCards = this.getCards()
					.iterator();
			while (iterDtoCards.hasNext()) {
				NestedSimpleTupleDTO dtoComboTuple = iterDtoCards.next();
				if (dtoComboTuple.getId().equals(comboTuple.getId())) {
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
			NestedSimpleTupleDTO dtoComboTuple = iterDtoCards.next();
			iterCards = entity.getCards().iterator();
			while (iterCards.hasNext()) {
				ComboTuple comboTuple = iterCards.next();
				if (dtoComboTuple.getId().equals(comboTuple.getId())) {
					found = true;
					break;
				}
			}
			if (found == false) {
				Iterator<ComboTuple> resultIter = em
						.createQuery("SELECT DISTINCT c FROM ComboTuple c",
								ComboTuple.class).getResultList().iterator();
				while (resultIter.hasNext()) {
					ComboTuple result = resultIter.next();
					if (result.getId().equals(dtoComboTuple.getId())) {
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

	public Set<NestedSimpleTupleDTO> getCards() {
		return this.cards;
	}

	public void setCards(final Set<NestedSimpleTupleDTO> cards) {
		this.cards = cards;
	}
}