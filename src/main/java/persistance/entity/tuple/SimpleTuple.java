package persistance.entity.tuple;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import persistance.entity.Card;

@MappedSuperclass
public abstract class SimpleTuple {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tuple_sequence")
	@SequenceGenerator(name = "tuple_sequence", sequenceName = "tuple_sequence", allocationSize = 1)
	private Long id;

	@ManyToOne
	private Card card;
	private Integer quantity;

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}
