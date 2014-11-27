package persistance.entity.tuple;

import java.io.Serializable;

import javax.persistence.*;

import persistance.entity.Card;

/**
 * Entity implementation class for Entity: DeckTuple
 *
 */
@Entity
public class DeckTuple implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "decktuple_sequence")
	@SequenceGenerator(name = "decktuple_sequence", sequenceName = "decktuple_sequence", allocationSize = 1)
	private Long id;

	@ManyToOne
	private Card card;

	private int quantity;
	private boolean favorite;

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

	public boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

}
