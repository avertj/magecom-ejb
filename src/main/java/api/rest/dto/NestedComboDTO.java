package api.rest.dto;

import java.io.Serializable;
import java.util.Date;

import persistance.entity.Combo;

public class NestedComboDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String description;
	private Date creationDate;
	private ColorDTO color;

	public NestedComboDTO() {
	}

	public NestedComboDTO(final Combo entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.description = entity.getDescription();
			this.creationDate = entity.getCreationDate();
			this.color = new ColorDTO(entity.getColor());
		}
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