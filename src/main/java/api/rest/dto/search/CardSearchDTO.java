package api.rest.dto.search;

import java.io.Serializable;

import javax.ws.rs.BeanParam;
import javax.ws.rs.QueryParam;

import persistance.entity.Card;
import persistance.entity.Card.Rarity;

public class CardSearchDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@QueryParam("name")
	private String name;
	@QueryParam("type")
	private String type;
	@QueryParam("text")
	private String text;

	@BeanParam
	private SearchColorDTO color;

	public CardSearchDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public SearchColorDTO getColor() {
		return color;
	}

	public void setColor(SearchColorDTO color) {
		this.color = color;
	}

}