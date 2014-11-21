package persistance.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: CardMember
 *
 */
@Entity
public class CardMember implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;

	@OneToOne(optional=false)
    @JoinColumn(name="MEMBER", unique=true, nullable=false, updatable=false)
	private Member member;
	
	@ManyToOne(optional=false) 
    @JoinColumn(name="CARD", nullable=false, updatable=false)
	private Card card;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}
	
	
}
