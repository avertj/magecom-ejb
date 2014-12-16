package api.rest.dto;

import java.io.Serializable;

import javax.persistence.EntityManager;

import persistance.entity.tuple.CollectionTuple;
import persistance.entity.tuple.ComboTuple;
import persistance.entity.tuple.PurchaseTuple;
import persistance.entity.tuple.SimpleTuple;

public class SimpleTupleDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Long id;
	protected CardDTO card;
	protected Integer quantity;

	public SimpleTupleDTO() {
	}

	public SimpleTupleDTO(final SimpleTuple entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.card = new CardDTO(entity.getCard());
			this.quantity = entity.getQuantity();
		}
	}

	public SimpleTuple fromDTO(PurchaseTuple entity, EntityManager em) {
		if (entity == null) {
			entity = new PurchaseTuple();
		}
		buildEntityTuple(entity, em);
		return entity;
	}

	public SimpleTuple fromDTO(ComboTuple entity, EntityManager em) {
		if (entity == null) {
			entity = new ComboTuple();
		}
		buildEntityTuple(entity, em);
		return entity;
	}

	public SimpleTuple fromDTO(CollectionTuple entity, EntityManager em) {
		if (entity == null) {
			entity = new CollectionTuple();
		}
		buildEntityTuple(entity, em);
		return entity;
	}

	private void buildEntityTuple(SimpleTuple entity, EntityManager em) {
		entity.setId(this.id);
		entity.setCard(this.card.fromDTO(null, em));
		entity.setQuantity(this.quantity);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CardDTO getCard() {
		return card;
	}

	public void setCard(CardDTO card) {
		this.card = card;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}