package persistance.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.hibernate.search.annotations.Field;

@Embeddable
public class Color {

	@Column(updatable = true, nullable = false)
	@Field
	private Boolean red;

	@Column(updatable = true, nullable = false)
	@Field
	private Boolean green;

	@Column(updatable = true, nullable = false)
	@Field
	private Boolean blue;

	@Column(updatable = true, nullable = false)
	@Field
	private Boolean black;

	@Column(updatable = true, nullable = false)
	@Field
	private Boolean white;

	public Boolean getRed() {
		return red;
	}

	public void setRed(Boolean red) {
		this.red = red;
	}

	public Boolean getGreen() {
		return green;
	}

	public void setGreen(Boolean green) {
		this.green = green;
	}

	public Boolean getBlue() {
		return blue;
	}

	public void setBlue(Boolean blue) {
		this.blue = blue;
	}

	public Boolean getBlack() {
		return black;
	}

	public void setBlack(Boolean black) {
		this.black = black;
	}

	public Boolean getWhite() {
		return white;
	}

	public void setWhite(Boolean white) {
		this.white = white;
	}
}
