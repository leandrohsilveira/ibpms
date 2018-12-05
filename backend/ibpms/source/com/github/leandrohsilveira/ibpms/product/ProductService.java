package com.github.leandrohsilveira.ibpms.product;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Singleton;

import com.github.leandrohsilveira.ibpms.ModelService;
import com.github.leandrohsilveira.ibpms.query.SearchResult;

@Singleton
public class ProductService extends ModelService<Product> {

	private static final long serialVersionUID = -9176173448022189383L;
	
	public UUID create(Product product) {
		return withCommitableConnection(connection -> {
			return new ProductDAO(connection).create(product);
		});
	}
	
	public Optional<Product> update(UUID uuid, Product product) {
		return withCommitableConnection(connection -> {
			return new ProductDAO(connection).update(uuid, product);
		});
	}
	
	public Optional<Product> findOne(UUID uuid) {
		return withConnection(connection -> {
			return new ProductDAO(connection).findOne(uuid);
		});
	}

	public SearchResult<Product> search(ProductSearch search) {
		return withConnection(connection -> {
			return new ProductDAO(connection).search(search);
		});
	}

	
	
	
}
