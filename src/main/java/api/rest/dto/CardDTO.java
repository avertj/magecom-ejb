package api.rest.dto;

import java.io.Serializable;

import persistance.entity.Card;

import javax.persistence.EntityManager;

import persistance.entity.Card.Rarity;
import api.rest.dto.ColorDTO;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@XmlRootElement
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CardDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String type;
	private String edition;
	private String[] text;
	private String[] flavorText;
	private Rarity rarity;
	private String artist;
	private Integer power;
	private Integer toughness;
	private Boolean x;
	private Integer convertedManaCost;
	private Float price;
	private ColorDTO color;
	private String manaString;

	public CardDTO() {
	}

	public CardDTO(final Card entity) {
		if (entity != null) {
			this.id = entity.getId();
			this.name = entity.getName();
			this.type = entity.getType();
			this.edition = entity.getEdition();
			this.text = entity.getText().split("\\|");
			this.flavorText = entity.getFlavorText().split("\\|");
			this.rarity = entity.getRarity();
			this.artist = entity.getArtist();
			this.power = entity.getPower();
			this.toughness = entity.getToughness();
			this.x = entity.getX();
			this.convertedManaCost = entity.getConvertedManaCost();
			this.price = entity.getPrice();
			this.color = new ColorDTO(entity.getColor());
			this.manaString = entity.getManaString();
		}
	}

	public Card fromDTO(Card entity, EntityManager em) {
		if (entity == null) {
			entity = new Card();
		}
		entity.setId(this.id);
		entity.setName(this.name);
		entity.setType(this.type);
		entity.setEdition(this.edition);
		entity.setText(join(this.text, "|"));
		entity.setFlavorText(join(this.flavorText, "|"));
		entity.setRarity(this.rarity);
		entity.setArtist(this.artist);
		entity.setPower(this.power);
		entity.setToughness(this.toughness);
		entity.setX(this.x);
		entity.setConvertedManaCost(this.convertedManaCost);
		entity.setPrice(this.price);
		if (this.color != null) {
			entity.setColor(this.color.fromDTO(entity.getColor(), em));
		}
		entity.setManaString(this.manaString);
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

	public String getType() {
		return this.type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getEdition() {
		return this.edition;
	}

	public void setEdition(final String edition) {
		this.edition = edition;
	}

	public String[] getText() {
		return this.text;
	}

	public void setText(final String[] text) {
		this.text = text;
	}

	public String[] getFlavorText() {
		return this.flavorText;
	}

	public void setFlavorText(final String[] flavorText) {
		this.flavorText = flavorText;
	}

	public Rarity getRarity() {
		return this.rarity;
	}

	public void setRarity(final Rarity rarity) {
		this.rarity = rarity;
	}

	public String getArtist() {
		return this.artist;
	}

	public void setArtist(final String artist) {
		this.artist = artist;
	}

	public Integer getPower() {
		return this.power;
	}

	public void setPower(final Integer power) {
		this.power = power;
	}

	public Integer getToughness() {
		return this.toughness;
	}

	public void setToughness(final Integer toughness) {
		this.toughness = toughness;
	}

	public Boolean getX() {
		return this.x;
	}

	public void setX(final Boolean x) {
		this.x = x;
	}

	public Integer getConvertedManaCost() {
		return this.convertedManaCost;
	}

	public void setConvertedManaCost(final Integer convertedManaCost) {
		this.convertedManaCost = convertedManaCost;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(final Float price) {
		this.price = price;
	}

	public ColorDTO getColor() {
		return this.color;
	}

	public void setColor(final ColorDTO color) {
		this.color = color;
	}

	public String getManaString() {
		return this.manaString;
	}

	public void setManaString(final String manaString) {
		this.manaString = manaString;
	}

	public String join(String r[], String d) {
		if (r.length == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		int i;
		for (i = 0; i < r.length - 1; i++) {
			sb.append(r[i]);
			sb.append(d);
		}
		sb.append(r[i]);
		return sb.toString();
	}
}