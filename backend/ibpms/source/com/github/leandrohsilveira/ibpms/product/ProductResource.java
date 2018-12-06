package com.github.leandrohsilveira.ibpms.product;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.github.leandrohsilveira.ibpms.product.exceptions.ProductNotFoundException;
import com.github.leandrohsilveira.ibpms.query.Pagination;
import com.github.leandrohsilveira.ibpms.query.SearchResult;
import com.github.leandrohsilveira.ibpms.query.Sort;

import kikaha.urouting.api.Consumes;
import kikaha.urouting.api.DELETE;
import kikaha.urouting.api.DefaultResponse;
import kikaha.urouting.api.GET;
import kikaha.urouting.api.Mimes;
import kikaha.urouting.api.PATCH;
import kikaha.urouting.api.POST;
import kikaha.urouting.api.Path;
import kikaha.urouting.api.PathParam;
import kikaha.urouting.api.QueryParam;
import kikaha.urouting.api.Response;

@Path("products")
@Singleton
public class ProductResource {

	@Inject
	private ProductService productService;

	@GET
	public SearchResult<Product> search( //
			@QueryParam("code") String code, //
			@QueryParam("name") String name, //
			@QueryParam("page") Integer page, //
			@QueryParam("size") Integer size, //
			@QueryParam("sort") String sortExpression) {
		return productService
				.search(new ProductSearch(code, name, new Pagination(page, size), new Sort(sortExpression)));
	}

	@GET
	@Path("{uuid}")
	public Product findOne(@PathParam("uuid") String uuid) {
		return productService.findOne(UUID.fromString(uuid)).orElseThrow(ProductNotFoundException::new);
	}
	
	@DELETE
	@Path("{uuid}")
	public void delete(@PathParam("uuid") String uuid) {
		Optional.of(UUID.fromString(uuid))
				.filter(productService::delete)
				.orElseThrow(ProductNotFoundException::new);
	}
	
	@PATCH
	@Path("{uuid}")
	@Consumes(Mimes.JSON)
	public Product update(@PathParam("uuid") String uuid, Product product) {
		return productService.update(UUID.fromString(uuid), product).orElseThrow(ProductNotFoundException::new);
	}
	
	@POST
	@Consumes(Mimes.JSON)
	public Response create(Product product) {
		UUID uuid = productService.create(product);
		return DefaultResponse.created(String.format("/products/%s", uuid.toString()));
	}

}
