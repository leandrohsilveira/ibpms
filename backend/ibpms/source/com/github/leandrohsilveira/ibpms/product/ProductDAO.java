package com.github.leandrohsilveira.ibpms.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.leandrohsilveira.ibpms.ModelDAO;
import com.github.leandrohsilveira.ibpms.product.exceptions.ProductCodeConflictException;
import com.github.leandrohsilveira.ibpms.query.QueryUtils;
import com.github.leandrohsilveira.ibpms.query.SearchResult;

public class ProductDAO extends ModelDAO<Product> {

	public ProductDAO(Connection connection) {
		super(connection);
	}

	@Override
	protected Product toModel(ResultSet set) {
		return execute(() -> {
			return Product.builder().uuid(UUID.fromString(set.getString("uuid"))).code(set.getString("code"))
					.name(set.getString("name")).price(set.getBigDecimal("price")).build();
		});
	}

	@Override
	protected Map<String, Object> mergeChanges(Product current, Product next) {
		return execute(() -> {
			Map<String, Object> updateParamsMap = new HashMap<>();
			Optional.ofNullable(next.getCode()).filter(QueryUtils.isNotEqual(current.getCode()))
					.ifPresent(code -> updateParamsMap.put("code", code));

			Optional.ofNullable(next.getName()).filter(QueryUtils.isNotEqual(current.getName()))
					.ifPresent(name -> updateParamsMap.put("name", name));

			Optional.ofNullable(next.getPrice()).filter(QueryUtils.isNotEqual(current.getPrice()))
					.ifPresent(price -> updateParamsMap.put("price", price));
			return updateParamsMap;
		});
	}

	public UUID create(Product product) {
		return execute(() -> {
			if (exists(product.getCode()))
				throw new ProductCodeConflictException(product.getCode());
			UUID uuid = UUID.randomUUID();
			PreparedStatement preparedStatement = connection
					.prepareStatement("insert into product (uuid, code, name, price) values (?, ?, ?, ?)");
			List<Object> params = Arrays.asList(uuid, product.getCode(), product.getName(), product.getPrice());
			setParamsToStatements(params, preparedStatement);
			preparedStatement.execute();
			return uuid;
		});
	}

	public Optional<Product> update(UUID uuid, Product product) {
		return execute(() -> {
			Optional<Product> findOne = findOne(uuid);
			if (findOne.isPresent()) {
				if (Optional.ofNullable(product.getCode()) //
						.flatMap(this::findOne) //
						.map(Product::getUuid) //
						.filter(QueryUtils.isNotEqual(uuid)) // There is a product with same code, but different UUID.
						.isPresent()) {
					throw new ProductCodeConflictException(product.getCode());
				}

				Product current = findOne.get();
				Map<String, Object> changes = mergeChanges(current, product);
				if (!changes.isEmpty()) {
					String updateQuery = QueryUtils.buildChangesQuery("product", changes, "uuid = ?");
					PreparedStatement statement = connection.prepareStatement(updateQuery);
					Stream<Object> updateParams = changes.entrySet().stream().map(Entry::getValue);
					Stream<UUID> whereParams = Arrays.asList(uuid).stream();
					List<Object> params = Stream.concat(updateParams, whereParams).collect(Collectors.toList());
					setParamsToStatements(params, statement);
					statement.executeUpdate();
				}
				return findOne(uuid);
			}
			return Optional.empty();
		});
	}

	public boolean delete(UUID uuid) {
		return execute(() -> {
			String deleteQuery = "delete from product where uuid = ?";
			PreparedStatement statement = connection.prepareStatement(deleteQuery);
			setParamsToStatements(Arrays.asList(uuid), statement);
			return statement.executeUpdate() > 0;
		});
	}

	public Optional<Product> findOne(UUID uuid) {
		return execute(() -> {
			String searchQuery = "select uuid, code, name, price from product where uuid = ?";
			PreparedStatement statement = connection.prepareStatement(searchQuery);
			setParamsToStatements(Arrays.asList(uuid), statement);
			return getSingleResult(statement);
		});
	}

	public Optional<Product> findOne(String code) {
		return execute(() -> {
			String searchQuery = "select uuid, code, name, price from product where code = ?";
			PreparedStatement statement = connection.prepareStatement(searchQuery);
			setParamsToStatements(Arrays.asList(code), statement);
			return getSingleResult(statement);
		});
	}

	public boolean exists(String code) {
		return execute(() -> {
			String searchQuery = "select count(code) as count from product where code = ?";
			PreparedStatement statement = connection.prepareStatement(searchQuery);
			setParamsToStatements(Arrays.asList(code), statement);
			return getCountResult(statement, "count") > 0;
		});
	}

	public SearchResult<Product> search(ProductSearch search) {
		return execute(() -> {
			String where = "where (? is null or upper(code) like ?) and (? is null or upper(name) like ?)";

			String searchProjection = "select uuid, code, name, price from product";
			String countProjection = "select count(code) as count from product";

			List<Object> params = Arrays.asList(QueryUtils.likeContainsUppercase(search.getCode()),
					QueryUtils.likeContainsUppercase(search.getCode()),
					QueryUtils.likeContainsUppercase(search.getName()),
					QueryUtils.likeContainsUppercase(search.getName()));

			PreparedStatement searchStatement = connection.prepareStatement(
					QueryUtils.buildQuery(searchProjection, where, search.getPagination(), search.getSort()));
			PreparedStatement countStatement = connection
					.prepareStatement(QueryUtils.buildQuery(countProjection, where, null, null));

			setParamsToStatements(params, searchStatement, countStatement);

			return new SearchResult<>(getResultList(searchStatement), getCountResult(countStatement, "count"));
		});
	}

}
