package com.github.leandrohsilveira.ibpms.product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Singleton;

import com.github.leandrohsilveira.ibpms.ModelService;
import com.github.leandrohsilveira.ibpms.query.QueryUtils;
import com.github.leandrohsilveira.ibpms.query.SearchResult;

@Singleton
public class ProductService extends ModelService<Product> {

	private static final long serialVersionUID = -9176173448022189383L;
	
	public Optional<Product> findOne(UUID uuid) {
		return withConnection(connection -> {
			String searchQuery = "select uuid, name, price from ibpms.product where uuid = ?";
			PreparedStatement statement = connection.prepareStatement(searchQuery);
			statement.setString(0, uuid.toString());
			return getSingleResult(statement);
		});
	}

	public SearchResult<Product> search(ProductSearch search) {
		return withConnection(connection -> {
			String where = "where (? is null or uuid like %?%) and (? is null or upper(name) like %?%)";
			
			String searchProjection = "select uuid, name, price from ibpms.product";
			String countProjection = "select count(uuid) from ibpms.product";
			
			List<Object> params = Arrays.asList(search.getUuid(), search.getUuid(), search.getName(), search.getName());
			
			PreparedStatement searchStatement = connection.prepareStatement(QueryUtils.buildQuery(searchProjection, where, search.getPagination(), search.getSort()));
			PreparedStatement countStatement = connection.prepareStatement(QueryUtils.buildQuery(countProjection, where, null, null));
			
			setParamsToStatements(params, searchStatement, countStatement);
			
			return new SearchResult<>(getResultList(searchStatement), getCountResult(countStatement));
		});
	}

	@Override
	protected Product toModel(ResultSet set) throws SQLException {
		return Product.builder()
				.uuid(UUID.fromString(set.getString("uuid")))
				.name(set.getString("name"))
				.price(set.getBigDecimal("price"))
				.build();
	}
	
	
}