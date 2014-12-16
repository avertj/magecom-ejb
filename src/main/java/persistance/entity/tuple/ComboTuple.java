package persistance.entity.tuple;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "combotuple")
public class ComboTuple extends SimpleTuple implements Serializable {
	private static final long serialVersionUID = 1L;
}
