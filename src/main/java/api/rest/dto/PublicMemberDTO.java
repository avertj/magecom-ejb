package api.rest.dto;

import java.io.Serializable;
import java.util.Iterator;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import persistance.entity.Combo;
import persistance.entity.Deck;
import persistance.entity.Member;
import persistance.entity.tuple.CollectionTuple;

@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PublicMemberDTO extends MemberDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	public PublicMemberDTO() {
	}

	public PublicMemberDTO(final Member entity, boolean loadLists) {
		if (entity != null) {
			this.id = entity.getId();
			this.username = entity.getUsername();
			if (loadLists) {
				Iterator<Deck> iterDecks = entity.getDecks().iterator();
				while (iterDecks.hasNext()) {
					Deck element = iterDecks.next();
					this.decks.add(new DeckDTO(element, false, true));
				}
				Iterator<Combo> iterCombos = entity.getCombos().iterator();
				while (iterCombos.hasNext()) {
					Combo element = iterCombos.next();
					this.combos.add(new ComboDTO(element, false));
				}
				Iterator<CollectionTuple> iterCollection = entity
						.getCollection().iterator();
				while (iterCollection.hasNext()) {
					CollectionTuple element = iterCollection.next();
					this.collection.add(new SimpleTupleDTO(element));
				}
			}
		}
	}
}