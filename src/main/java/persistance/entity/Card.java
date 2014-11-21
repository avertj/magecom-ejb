package persistance.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


/**
 * Entity implementation class for Entity: Card
 *
 */

@Entity
public class Card implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(updatable = false, nullable = false)
	private String name;

	@Column(updatable = false, nullable = false)
	private String picturePath;

	@Column(updatable = false, nullable = false)
	private String type;

	@Column(updatable = false, nullable = false)
	private String edition;

	@Column(updatable = false, nullable = false)
	private String text;

	@Column(name = "flavor_text", updatable = false, nullable = false)
	private String flavorText;

	public enum Rarity {
		COMMON, UNCOMMON, RARE, MYTHIC_RARE;
	}

	@Column(updatable = false, nullable = false)
	@Enumerated(EnumType.STRING)
	private Rarity rarity;

	@Column(updatable = false, nullable = false)
	private String artist;

	@Column(updatable = false, nullable = false)
	private Integer power;

	@Column(updatable = false, nullable = false)
	private Integer toughness;

	@Column(updatable = false, nullable = false)
	private Boolean x;

	@Column(name = "converted_mana_cost", updatable = false, nullable = false)
	private Integer convertedManaCost;

	@Column(updatable = true, nullable = false)
	private Float price;

	@Embedded
	private Color color;
	
	@OneToMany(mappedBy="card")
    private Set<CardMember> cardMember ;

	// ManyToMany decks (+ cardsQuantity);

	// ManyToMany combos (+ cardsQuantity);

	// ManyToMany purchases (+ cardsQuantity);

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

	public String getPicturePath() {
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEdition() {
		return edition;
	}

	public void setEdition(String edition) {
		this.edition = edition;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFlavorText() {
		return flavorText;
	}

	public void setFlavorText(String flavorText) {
		this.flavorText = flavorText;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public void setRarity(Rarity rarity) {
		this.rarity = rarity;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public Integer getPower() {
		return power;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public Integer getToughness() {
		return toughness;
	}

	public void setToughness(Integer toughness) {
		this.toughness = toughness;
	}

	public Boolean getX() {
		return x;
	}

	public void setX(Boolean x) {
		this.x = x;
	}

	public Integer getConvertedManaCost() {
		return convertedManaCost;
	}

	public void setConvertedManaCost(Integer convertedManaCost) {
		this.convertedManaCost = convertedManaCost;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}