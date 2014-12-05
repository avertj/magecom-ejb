package api.rest.dto;

import java.io.Serializable;
import persistance.entity.Combo;
import javax.persistence.EntityManager;
import java.util.Date;
import api.rest.dto.ColorDTO;
import api.rest.dto.NestedMemberDTO;
import java.util.Set;
import java.util.HashSet;
import persistance.entity.tuple.ComboTuple;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlRootElement;

public class NestedComboDTO implements Serializable {

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