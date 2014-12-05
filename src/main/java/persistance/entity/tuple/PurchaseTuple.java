package persistance.entity.tuple;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "purchasetuple")
public class PurchaseTuple extends SimpleTuple implements Serializable {
	private static final long serialVersionUID = 1L;
}
