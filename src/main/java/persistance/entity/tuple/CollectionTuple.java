package persistance.entity.tuple;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "collectiontuple")
public class CollectionTuple extends SimpleTuple implements Serializable {
	private static final long serialVersionUID = 1L;
}
