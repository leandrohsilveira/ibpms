package com.github.leandrohsilveira.ibpms.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.leandrohsilveira.ibpms.DAO;
import com.github.leandrohsilveira.ibpms.query.QueryUtils;
import com.github.leandrohsilveira.ibpms.query.SearchResult;

public class ProductDAO extends DAO<Product> {

	public ProductDAO(Connection connection) {
		super(connection);
	}

	@Override
	protected Product toModel(ResultSet set) throws SQLException {
		return Product.builder()
				.uuid(UUID.fromString(set.getString("uuid")))
				.name(set.getString("name"))
				.price(set.getBigDecimal("price"))
				.build();
	}
	
	@Override
	protected Map<String, Object> mergeChanges(Product current, Product next) {
		Map<String, Object> updateParamsMap = new HashMap<>();
		Optional.ofNullable(next.getName())
			.filter(QueryUtils.isNotEqual(current.getName()))
			.ifPresent(name -> updateParamsMap.put("name", name));
	
		Optional.ofNullable(next.getPrice())
			.filter(QueryUtils.isNotEqual(current.getPrice()))
			.ifPresent(price -> updateParamsMap.put("price", price));
		return updateParamsMap;
	}
	
	public UUID create(Product product) throws SQLException {
		UUID uuid = UUID.randomUUID();
		PreparedStatement preparedStatement = connection.prepareStatement("insert into product (uuid, name, price) values (?, ?, ?)");
		List<Object> params = Arrays.asList(uuid.toString(), product.getName(), product.getPrice());
		setParamsToStatements(params, preparedStatement);
		preparedStatement.execute();
		return uuid;
	}
	
	public Optional<Product> update(UUID uuid, Product product) throws SQLException {
		Optional<Product> findOne = findOne(uuid);
		if(findOne.isPresent()) {
			Product current = findOne.get();
			Map<String, Object> changes = mergeChanges(current, product);
			String updateQuery = QueryUtils.buildChangesQuery("product", changes, "uuid = ?");
			PreparedStatement statement = connection.prepareStatement(updateQuery);
			Stream<Object> updateParams = changes.entrySet().stream().map(Entry::getValue);
			Stream<String> whereParams = Arrays.asList(uuid.toString()).stream();
			List<Object> params = Stream.concat(updateParams, whereParams).collect(Collectors.toList());
			setParamsToStatements(params, statement);
			statement.executeUpdate();
			return findOne(uuid);
		}
		return Optional.empty();
	}
	
	public boolean delete(UUID uuid) throws SQLException {
		String deleteQuery = "delete from product where uuid = ?";
		PreparedStatement statement = connection.prepareStatement(deleteQuery);
		setParamsToStatements(Arrays.asList(uuid.toString()), statement);
		return statement.executeUpdate() > 0;
	}
	
	public Optional<Product> findOne(UUID uuid) throws SQLException {
		String searchQuery = "select uuid, name, price from product where uuid = ?";
		PreparedStatement statement = connection.prepareStatement(searchQuery);
		setParamsToStatements(Arrays.asList(uuid.toString()), statement);
		return getSingleResult(statement);
	}

	public SearchResult<Product> search(ProductSearch search) throws SQLException {
		String where = "where (? is null or upper(uuid) like ?) and (? is null or upper(name) like ?)";
		
		String searchProjection = "select uuid, name, price from product";
		String countProjection = "select count(uuid) as count from product";
		
		List<Object> params = Arrays.asList(
				QueryUtils.likeContainsUppercase(search.getUuid()), 
				QueryUtils.likeContainsUppercase(search.getUuid()), 
				QueryUtils.likeContainsUppercase(search.getName()), 
				QueryUtils.likeContainsUppercase(search.getName())
				);
		
		PreparedStatement searchStatement = connection.prepareStatement(QueryUtils.buildQuery(searchProjection, where, search.getPagination(), search.getSort()));
		PreparedStatement countStatement = connection.prepareStatement(QueryUtils.buildQuery(countProjection, where, null, null));
		
		setParamsToStatements(params, searchStatement, countStatement);
		
		return new SearchResult<>(getResultList(searchStatement), getCountResult(countStatement, "count"));
	}
	
}
