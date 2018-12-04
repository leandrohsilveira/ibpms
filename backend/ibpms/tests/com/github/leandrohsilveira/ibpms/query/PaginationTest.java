package com.github.leandrohsilveira.ibpms.query;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class PaginationTest {

	@Test
	public void appendToQueryTest() {
		String pagination;
		pagination = Pagination.builder().page(1).limit(10).build().appendToQuery("");
		assertNotNull(pagination);
		assertEquals(" offset 0 limit 10", pagination);
		
		pagination = Pagination.builder().page(2).limit(10).build().appendToQuery("");
		assertNotNull(pagination);
		assertEquals(" offset 10 limit 10", pagination);
		
		pagination = Pagination.builder().page(3).limit(10).build().appendToQuery("");
		assertNotNull(pagination);
		assertEquals(" offset 20 limit 10", pagination);
	}
	
	@Test(expected=NullPointerException.class)
	public void buildWithoutPageTest() {
		Pagination.builder().limit(10).build();
	}
	
	@Test(expected=NullPointerException.class)
	public void buildWithoutLimitTest() {
		Pagination.builder().page(1).build();
	}
	
}
