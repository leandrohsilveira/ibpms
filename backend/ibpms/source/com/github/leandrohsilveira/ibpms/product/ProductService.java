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
	
	public UUID create(Product product) {
		return withCommitableConnection(connection -> {
			UUID uuid = UUID.randomUUID();
			PreparedStatement preparedStatement = connection.prepareStatement("insert into product (uuid, name, price) values (?, ?, ?)");
			List<Object> params = Arrays.asList(uuid.toString(), product.getName(), product.getPrice());
			setParamsToStatements(params, preparedStatement);
			preparedStatement.execute();
			return uuid;
		});
	}
	
	public Optional<Product> findOne(UUID uuid) {
		return withConnection(connection -> {
			String searchQuery = "select uuid, name, price from product where uuid = ?";
			PreparedStatement statement = connection.prepareStatement(searchQuery);
			setParamsToStatements(Arrays.asList(uuid.toString()), statement);
			return getSingleResult(statement);
		});
	}

	public SearchResult<Product> search(ProductSearch search) {
		return withConnection(connection -> {
			String where = "where (? is null or upper(uuid) like ?) and (? is null or upper(name) like ?)";
			
			String searchProjection = "select uuid, name, price from product";
			String countProjection = "select count(uuid) from product";
			
			List<Object> params = Arrays.asList(
				QueryUtils.likeContainsUppercase(search.getUuid()), 
				QueryUtils.likeContainsUppercase(search.getUuid()), 
				QueryUtils.likeContainsUppercase(search.getName()), 
				QueryUtils.likeContainsUppercase(search.getName())
			);
			
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
