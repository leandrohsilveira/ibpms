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
		return withCommitableDAO(ProductDAO::new, productDAO -> {
			return productDAO.create(product);
		});
	}
	
	public Optional<Product> update(UUID uuid, Product product) {
		return withCommitableDAO(ProductDAO::new, productDAO -> {
			return productDAO.update(uuid, product);
		});
	}
	
	public boolean delete(UUID uuid) {
		return withCommitableDAO(ProductDAO::new, productDAO -> {
			return productDAO.delete(uuid);
		});
	}
	
	public Optional<Product> findOne(UUID uuid) {
		return withDAO(ProductDAO::new, productDAO -> {
			return productDAO.findOne(uuid);
		});
	}

	public SearchResult<Product> search(ProductSearch search) {
		return withDAO(ProductDAO::new, productDAO -> {
			return productDAO.search(search);
		});
	}

}
