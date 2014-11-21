package persistance.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: Combo
 *
 */

@Entity
public class Combo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Long id;
	
	@Column(updatable = true, nullable = false)
	private String name;
	
	@Column(updatable = true, nullable = true)
	private String description;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "DATE_CREATION", updatable = false, nullable = false)
	private java.util.Date dateCreation;
	
	@Embedded
	private Color color;
	
	@ManyToOne
	@JoinColumn(name="MEMBER_ID", updatable = false, nullable=false)
	private Member member;
	
	//ManyToMany cards (+ cardsQuantity);

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public java.util.Date getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(java.util.Date dateCreation) {
		this.dateCreation = dateCreation;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}
