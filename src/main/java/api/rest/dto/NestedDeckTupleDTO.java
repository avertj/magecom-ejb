package api.rest.dto;

import java.io.Serializable;
import persistance.entity.tuple.DeckTuple;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class NestedDeckTupleDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private int quantity;
	private boolean favorite;
	private CardDTO card;

	public NestedDeckTupleDTO() {
	}

	public NestedDeckTupleDTO(final DeckTuple entity) {
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
		if (this.id != null) {
			TypedQuery<DeckTuple> findByIdQuery = em
					.createQuery(
							"SELECT DISTINCT d FROM DeckTuple d WHERE d.id = :entityId",
							DeckTuple.class);
			findByIdQuery.setParameter("entityId", this.id);
			try {
				entity = findByIdQuery.getSingleResult();
			} catch (javax.persistence.NoResultException nre) {
				entity = null;
			}
			return entity;
		}
		entity.setQuantity(this.quantity);
		entity.setFavorite(this.favorite);
		entity = em.merge(entity);
		return entity;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final int quantity) {
		this.quantity = quantity;
	}

	public boolean getFavorite() {
		return this.favorite;
	}

	public void setFavorite(final boolean favorite) {
		this.favorite = favorite;
	}

	public CardDTO getCard() {
		return card;
	}

	public void setCard(CardDTO card) {
		this.card = card;
	}

}