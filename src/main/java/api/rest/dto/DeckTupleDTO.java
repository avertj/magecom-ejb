package api.rest.dto;

import java.io.Serializable;

import javax.persistence.EntityManager;

import persistance.entity.tuple.DeckTuple;

public class DeckTupleDTO extends SimpleTupleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean favorite;

	public DeckTupleDTO() {
	}

	public DeckTupleDTO(final DeckTuple entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.quantity = entity.getQuantity();
			this.favorite = entity.isFavorite();
			this.card = new CardDTO(entity.getCard());
		}
	}

	public DeckTuple fromDTO(DeckTuple entity, EntityManager em) {
		if (entity == null) {
			entity = new DeckTuple();
		}
		entity.setId(this.id);
		entity.setCard(this.card.fromDTO(null, em));
		entity.setQuantity(this.quantity);
		// if(this.favorite)
		entity.setFavorite(this.favorite);
		// entity = em.merge(entity);
		return entity;
	}

	public boolean getFavorite() {
		return this.favorite;
	}

	public void setFavorite(final boolean favorite) {
		this.favorite = favorite;
	}
}