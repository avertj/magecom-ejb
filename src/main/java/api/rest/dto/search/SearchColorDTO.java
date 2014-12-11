package api.rest.dto.search;

import java.io.Serializable;

import persistance.entity.Color;

import javax.persistence.EntityManager;
import javax.ws.rs.QueryParam;

public class SearchColorDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@QueryParam("red")
	private Boolean red;
	@QueryParam("green")
	private Boolean green;
	@QueryParam("blue")
	private Boolean blue;
	@QueryParam("black")
	private Boolean black;
	@QueryParam("white")
	private Boolean white;

	public SearchColorDTO() {
	}

	public Boolean getRed() {
		return this.red;
	}

	public void setRed(final Boolean red) {
		this.red = red;
	}

	public Boolean getGreen() {
		return this.green;
	}

	public void setGreen(final Boolean green) {
		this.green = green;
	}

	public Boolean getBlue() {
		return this.blue;
	}

	public void setBlue(final Boolean blue) {
		this.blue = blue;
	}

	public Boolean getBlack() {
		return this.black;
	}

	public void setBlack(final Boolean black) {
		this.black = black;
	}

	public Boolean getWhite() {
		return this.white;
	}

	public void setWhite(final Boolean white) {
		this.white = white;
	}
}