package api.rest.dto.search;

import java.io.Serializable;

import javax.ws.rs.BeanParam;
import javax.ws.rs.QueryParam;

public class ComboSearchDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@QueryParam("name")
	private String name;
	@QueryParam("description")
	private String description;

	@BeanParam
	private SearchColorDTO color;

	public ComboSearchDTO() {
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

	public SearchColorDTO getColor() {
		return color;
	}

	public void setColor(SearchColorDTO color) {
		this.color = color;
	}

}