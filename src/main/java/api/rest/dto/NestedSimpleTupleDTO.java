package api.rest.dto;

import java.io.Serializable;

import persistance.entity.Card;
import persistance.entity.Combo;
import persistance.entity.tuple.SimpleTuple;

import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.TypedQuery;

import java.util.Date;

import api.rest.dto.ColorDTO;

public class NestedSimpleTupleDTO implements Serializable {

	private Long id;
	private CardDTO card;
	private Integer quantity;

	public NestedSimpleTupleDTO() {
	}

	public NestedSimpleTupleDTO(final SimpleTuple entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.card = new CardDTO(entity.getCard());
			this.quantity = entity.getQuantity();
		}
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