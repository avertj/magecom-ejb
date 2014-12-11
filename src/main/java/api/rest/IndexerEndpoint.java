package api.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import persistance.entity.Card;
import api.rest.dto.CardDTO;
import api.rest.dto.search.CardSearchDTO;

@Stateless
@Path("/index")
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class IndexerEndpoint {
	@PersistenceContext(unitName = "magecom-ejb-persistence-unit")
	private EntityManager em;

	@GET
	public Response index() {
		FullTextEntityManager fullTextEntityManager = Search
				.getFullTextEntityManager(em);
		try {
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Response.ok().build();
	}

	@GET
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CardDTO> search(@BeanParam CardSearchDTO search) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
				.getFullTextEntityManager(em);
		// em.getTransaction().begin();

		BooleanQuery bq = new BooleanQuery();

		QueryBuilder qb = fullTextEntityManager.getSearchFactory()
				.buildQueryBuilder().forEntity(Card.class).get();
		if (search.getName() != null) {
			org.apache.lucene.search.Query nameCriteria = qb.keyword()
					.onField("name").andField("engram").boostedTo(5)
					.matching(search.getName()).createQuery();
			bq.add(nameCriteria, BooleanClause.Occur.MUST);
		}
		if (search.getType() != null) {
			org.apache.lucene.search.Query nameCriteria = qb.phrase()
					.withSlop(2).onField("type").sentence(search.getType())
					.createQuery();
			bq.add(nameCriteria, BooleanClause.Occur.MUST);
		}
		if (search.getText() != null) {
			org.apache.lucene.search.Query nameCriteria = qb.phrase()
					.withSlop(4).onField("text").sentence(search.getText())
					.createQuery();
			bq.add(nameCriteria, BooleanClause.Occur.MUST);
		}
		if (search.getColor() != null) {
			if (search.getColor().getBlack() != null) {
				org.apache.lucene.search.Query manaCriteria = qb.keyword()
						.onField("color.black")
						.matching(search.getColor().getBlack()).createQuery();

				bq.add(manaCriteria, BooleanClause.Occur.MUST);
			}
			if (search.getColor().getBlue() != null) {
				org.apache.lucene.search.Query manaCriteria = qb.keyword()
						.onField("color.blue")
						.matching(search.getColor().getBlue()).createQuery();

				bq.add(manaCriteria, BooleanClause.Occur.MUST);
			}
			if (search.getColor().getGreen() != null) {
				org.apache.lucene.search.Query manaCriteria = qb.keyword()
						.onField("color.green")
						.matching(search.getColor().getGreen()).createQuery();

				bq.add(manaCriteria, BooleanClause.Occur.MUST);
			}
			if (search.getColor().getRed() != null) {
				org.apache.lucene.search.Query manaCriteria = qb.keyword()
						.onField("color.red")
						.matching(search.getColor().getRed()).createQuery();

				bq.add(manaCriteria, BooleanClause.Occur.MUST);
			}
			if (search.getColor().getWhite() != null) {
				org.apache.lucene.search.Query manaCriteria = qb.keyword()
						.onField("color.white")
						.matching(search.getColor().getWhite()).createQuery();

				bq.add(manaCriteria, BooleanClause.Occur.MUST);
			}
		}
		javax.persistence.Query jpaQuery = fullTextEntityManager
				.createFullTextQuery(bq, Card.class);

		final List<Card> searchResults = jpaQuery.getResultList();
		final List<CardDTO> results = new ArrayList<CardDTO>();
		for (Card searchResult : searchResults) {
			CardDTO dto = new CardDTO(searchResult);
			results.add(dto);
		}
		// em.getTransaction().commit();
		// em.close();
		return results;
	}
}
