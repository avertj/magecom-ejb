package persistance.entity.tuple;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "decktuple")
public class DeckTuple extends SimpleTuple implements Serializable {
	private static final long serialVersionUID = 1L;

	private Boolean favorite;

	public Boolean isFavorite() {
		return favorite;
	}

	public void setFavorite(Boolean favorite) {
		this.favorite = favorite;
	}

}
