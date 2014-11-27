package persistance.entity.tuple;

import java.io.Serializable;

import javax.persistence.*;

import persistance.entity.Card;

/**
 * Entity implementation class for Entity: CollectionTuple
 *
 */
@Entity

public class CollectionTuple implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "collectiontuple_sequence")
	@SequenceGenerator(name = "collectiontuple_sequence", sequenceName = "collectiontuple_sequence", allocationSize = 1) 
	private Long id;

	@ManyToOne
	private Card card;
	private int quantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
