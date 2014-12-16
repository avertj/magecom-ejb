package persistance.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import org.apache.solr.analysis.ASCIIFoldingFilterFactory;
import org.apache.solr.analysis.EdgeNGramFilterFactory;
import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.PhoneticFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@Entity
@Indexed
@AnalyzerDefs({
		@AnalyzerDef(name = "fr.card", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
				@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				@TokenFilterDef(factory = PhoneticFilterFactory.class, params = { @Parameter(name = "encoder", value = "SOUNDEX") }),
				@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = { @Parameter(name = "language", value = "French") }), }),
		@AnalyzerDef(name = "fr.card.engram", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
				@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				@TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
						@Parameter(name = "maxGramSize", value = "10"),
						@Parameter(name = "minGramSize", value = "3") }) }) })
public class Card implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(updatable = false, nullable = false)
	@Fields({
			@Field(store = Store.YES, analyzer = @Analyzer(definition = "fr.card")),
			@Field(name = "engram", analyzer = @Analyzer(definition = "fr.card.engram")) })
	private String name;

	@Column(updatable = false, nullable = false)
	@Field
	@Analyzer(definition = "fr.card")
	private String type;

	@Column(updatable = false, nullable = true)
	private String edition;

	@Column(columnDefinition = "TEXT", updatable = false, nullable = false)
	@Field
	@Analyzer(definition = "fr.card")
	private String text;

	@Column(columnDefinition = "TEXT", updatable = false, nullable = true)
	private String flavorText;

	public enum Rarity {
		BASIC_LAND, COMMON, UNCOMMON, RARE;
	}

	@Column(updatable = false, nullable = false)
	@Enumerated(EnumType.STRING)
	private Rarity rarity;

	@Column(updatable = false, nullable = false)
	private String artist;

	@Column(updatable = false, nullable = true)
	private Integer power;

	@Column(updatable = false, nullable = true)
	private Integer toughness;

	@Column(updatable = false, nullable = false)
	private Boolean x;

	@Column(updatable = false, nullable = false)
	@Field
	private Integer convertedManaCost;

	@Column(updatable = true, nullable = false)
	private Float price;

	@Embedded
	@IndexedEmbedded
	private Color color;

	@Column(updatable = false, nullable = true)
	private String manaString;

	/*
	 * @OneToMany(mappedBy="card") private Set<CardMember> cardMember ;
	 */

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

	public String getManaString() {
		return manaString;
	}

	public void setManaString(String manaString) {
		this.manaString = manaString;
	}

}