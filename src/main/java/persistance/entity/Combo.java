package persistance.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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

import persistance.entity.tuple.ComboTuple;

@Entity
@Indexed
@AnalyzerDefs({
		@AnalyzerDef(name = "fr.combo", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
				@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				@TokenFilterDef(factory = PhoneticFilterFactory.class, params = { @Parameter(name = "encoder", value = "SOUNDEX") }),
				@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = { @Parameter(name = "language", value = "French") }), }),
		@AnalyzerDef(name = "fr.combo.engram", tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), filters = {
				@TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
				@TokenFilterDef(factory = LowerCaseFilterFactory.class),
				@TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
						@Parameter(name = "maxGramSize", value = "10"),
						@Parameter(name = "minGramSize", value = "3") }) }) })
public class Combo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "combo_sequence")
	@SequenceGenerator(name = "combo_sequence", sequenceName = "combo_sequence", allocationSize = 1)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Column(updatable = true, nullable = false)
	@Fields({
			@Field(store = Store.YES, analyzer = @Analyzer(definition = "fr.combo")),
			@Field(name = "engram", analyzer = @Analyzer(definition = "fr.combo.engram")) })
	private String name;

	@Column(columnDefinition = "TEXT", updatable = true, nullable = true)
	@Field
	@Analyzer(definition = "fr.combo")
	private String description;

	@Temporal(TemporalType.DATE)
	@Column(updatable = false, nullable = false)
	private Date creationDate;

	@Embedded
	@IndexedEmbedded
	private Color color;

	@ManyToOne
	@JoinColumn(updatable = false, nullable = false)
	private Member member;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "combo_id", nullable = false)
	// @OrderBy("card.convertedManaCost asc")
	private Set<ComboTuple> cards = new HashSet<ComboTuple>();

	// ManyToMany cards (+ cardsQuantity);

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Set<ComboTuple> getCards() {
		return cards;
	}

	public void setCards(Set<ComboTuple> cards) {
		// this.cards.clear();
		// this.cards.addAll(cards);
		this.cards = cards;
	}

	public void forceSetCards(Set<ComboTuple> cards) {
		this.cards.clear();
		this.cards.addAll(cards);
		// this.cards = cards;
	}

}
