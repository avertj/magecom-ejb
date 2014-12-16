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
	private MemberDTO member;
	private Set<SimpleTupleDTO> cards = new HashSet<SimpleTupleDTO>();

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
				this.member = new PublicMemberDTO(entity.getMember(), false);
			}
			Iterator<ComboTuple> iterCards = entity.getCards().iterator();
			while (iterCards.hasNext()) {
				ComboTuple element = iterCards.next();
				this.cards.add(new SimpleTupleDTO(element));
			}
		}
	}

	public Combo fromDTO(Combo entity, EntityManager em) {
		if (entity == null) {
			entity = new Combo();
			entity.setCreationDate(new Date());
			entity.setMember(this.member.fromDTO(null, em));
		}
		if (this.name != null)
			entity.setName(this.name);
		if (this.description != null)
			entity.setDescription(this.description);
		if (this.color != null)
			entity.setColor(this.color.fromDTO(entity.getColor(), em));
		if (this.cards != null && this.cards.size() > 0) {
			Set<ComboTuple> cards = new HashSet<ComboTuple>();
			Iterator<SimpleTupleDTO> iterDtoCards = this.getCards().iterator();
			while (iterDtoCards.hasNext()) {
				cards.add((ComboTuple) iterDtoCards.next().fromDTO(
						new ComboTuple(), em));
			}
			entity.forceSetCards(cards);
		}
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