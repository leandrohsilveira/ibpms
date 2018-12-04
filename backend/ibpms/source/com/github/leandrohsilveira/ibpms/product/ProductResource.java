package com.github.leandrohsilveira.ibpms.product;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.github.leandrohsilveira.ibpms.query.Pagination;
import com.github.leandrohsilveira.ibpms.query.SearchResult;
import com.github.leandrohsilveira.ibpms.query.Sort;

import kikaha.urouting.api.GET;
import kikaha.urouting.api.Path;
import kikaha.urouting.api.PathParam;
import kikaha.urouting.api.QueryParam;

@Path("products")
@Singleton
public class ProductResource {

	@Inject
	private ProductService productService;

	@GET
	public SearchResult<Product> search( //
			@QueryParam("uuid") String uuid, //
			@QueryParam("name") String name, //
			@QueryParam("page") Integer page, //
			@QueryParam("size") Integer size, //
			@QueryParam("sort") String sortExpression) {
		return productService
				.search(new ProductSearch(uuid, name, new Pagination(page, size), new Sort(sortExpression)));
	}

	@GET
	@Path("{uuid}")
	public Product find(@PathParam("uuid") String uuid) {
		return productService.findOne(UUID.fromString(uuid)).orElse(null);
	}

}
