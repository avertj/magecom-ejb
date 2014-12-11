package api.rest.dto;

import java.io.Serializable;
import persistance.entity.Deck;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Date;
import api.rest.dto.ColorDTO;

public class NestedDeckDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private Date creationDate;
	private ColorDTO color;

	public NestedDeckDTO() {
	}

	public NestedDeckDTO(final Deck entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.creationDate = entity.getCreationDate();
			this.color = new ColorDTO(entity.getColor());
		}
	}

	public Deck fromDTO(Deck entity, EntityManager em) {
		if (entity == null) {
			entity = new Deck();
		}
		if (this.id != null) {
			TypedQuery<Deck> findByIdQuery = em.createQuery(
					"SELECT DISTINCT d FROM Deck d WHERE d.id = :entityId",
					Deck.class);
			findByIdQuery.setParameter("entityId", this.id);
			try {
				entity = findByIdQuery.getSingleResult();
			} catch (javax.persistence.NoResultException nre) {
				entity = null;
			}
			return entity;
		}
		entity.setName(this.name);
		entity.setDescription(this.description);
		entity.setCreationDate(this.creationDate);
		if (this.color != null) {
			entity.setColor(this.color.fromDTO(entity.getColor(), em));
		}
		entity = em.merge(entity);
		return entity;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return this.creationDate;
	}

	public void setCreationDate(final Date creationDate) {
		this.creationDate = creationDate;
	}

	public ColorDTO getColor() {
		return this.color;
	}

	public void setColor(final ColorDTO color) {
		this.color = color;
	}
}