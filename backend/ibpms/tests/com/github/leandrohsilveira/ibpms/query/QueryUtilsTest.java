package com.github.leandrohsilveira.ibpms.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class QueryUtilsTest {

	private Pagination pagination = Pagination.builder().page(1).limit(10).build();
	private Sort sort = new Sort("name", SortType.ASC);
	
	@Test
	public void basicTest() {
		String query = QueryUtils.buildQuery("select name from ibpms.product", "where uuid = ?", null, null);
		assertEquals("select name from ibpms.product where uuid = ?", query);
	}
	
	@Test
	public void paginationTest() {
		String query = QueryUtils.buildQuery("select name from ibpms.product", "where name like %?%", pagination, null);
		assertEquals("select name from ibpms.product where name like %?% offset 0 limit 10", query);
	}
	
	@Test
	public void sortTest() {
		String query = QueryUtils.buildQuery("select name from ibpms.product", "where name like %?%", null, sort);
		assertEquals("select name from ibpms.product where name like %?% order by name asc", query);
	}
	
	@Test
	public void paginationAndSortTest() {
		String query = QueryUtils.buildQuery("select name from ibpms.product", "where name like %?%", pagination, sort);
		assertEquals("select name from ibpms.product where name like %?% offset 0 limit 10 order by name asc", query);
	}
	
}
