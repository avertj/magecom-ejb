package api.rest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import persistance.entity.Deck;
import persistance.entity.tuple.DeckTuple;

@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class DeckDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private Date creationDate;
	private ColorDTO color;
	private MemberDTO member;
	private Set<DeckTupleDTO> cards = new HashSet<DeckTupleDTO>();

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
				this.member = new PublicMemberDTO(entity.getMember(), false);
			}
			Iterator<DeckTuple> iterCards = entity.getCards().iterator();
			while (iterCards.hasNext()) {
				DeckTuple element = iterCards.next();
				if (element.isFavorite() || !onlyFav) {
					this.cards.add(new DeckTupleDTO(element));
				}
			}
		}
	}

	public Deck fromDTO(Deck entity, EntityManager em) {
		if (entity == null) {
			entity = new Deck();
			entity.setCreationDate(new Date());
			entity.setMember(this.member.fromDTO(null, em));
		}
		if (this.name != null)
			entity.setName(this.name);
		if (this.description != null)
			entity.setDescription(this.description);
		if (this.color != null) {
			System.out.println("COLOR NOT NULL");
			entity.setColor(this.color.fromDTO(entity.getColor(), em));
		}
		if (this.cards != null && this.cards.size() > 0) {
			Set<DeckTuple> cards = new HashSet<DeckTuple>();
			Iterator<DeckTupleDTO> iterDtoCards = this.getCards().iterator();
			while (iterDtoCards.hasNext()) {
				cards.add(iterDtoCards.next().fromDTO(new DeckTuple(), em));
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

	public Set<DeckTupleDTO> getCards() {
		return this.cards;
	}

	public void setCards(final Set<DeckTupleDTO> cards) {
		this.cards = cards;
	}
}