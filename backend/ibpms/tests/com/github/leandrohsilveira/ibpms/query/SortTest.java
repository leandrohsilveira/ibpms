package com.github.leandrohsilveira.ibpms.query;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SortTest {

	@Test
	public void expressionConstructorTest() {
		Sort sort;
		sort = new Sort("name");
		assertEquals("name", sort.getField());
		assertEquals(SortType.ASC, sort.getType());
		
		sort = new Sort("name,asc");
		assertEquals("name", sort.getField());
		assertEquals(SortType.ASC, sort.getType());
		
		sort = new Sort("name,desc");
		assertEquals("name", sort.getField());
		assertEquals(SortType.DESC, sort.getType());
		
		sort = new Sort("name, asc");
		assertEquals("name", sort.getField());
		assertEquals(SortType.ASC, sort.getType());
		
		sort = new Sort("name, desc");
		assertEquals("name", sort.getField());
		assertEquals(SortType.DESC, sort.getType());
		
		sort = new Sort("name ,asc");
		assertEquals("name", sort.getField());
		assertEquals(SortType.ASC, sort.getType());
		
		sort = new Sort("name ,desc");
		assertEquals("name", sort.getField());
		assertEquals(SortType.DESC, sort.getType());
		
		sort = new Sort("name , asc");
		assertEquals("name", sort.getField());
		assertEquals(SortType.ASC, sort.getType());
		
		sort = new Sort("name , desc");
		assertEquals("name", sort.getField());
		assertEquals(SortType.DESC, sort.getType());
		
		sort = new Sort("name,");
		assertEquals("name", sort.getField());
		assertEquals(SortType.ASC, sort.getType());
	}
	
	@Test
	public void firstAppendToQueryTest() {
		
		Sort sort = new Sort("name", SortType.ASC);
		
		assertEquals("select p from Product as p order by name asc", sort.appendToQuery("select p from Product as p"));
		assertEquals("select p from Product as p where p.name is not null order by name asc", sort.appendToQuery("select p from Product as p where p.name is not null"));
		
	}
	
	@Test
	public void multipleAppendToQueryTest() {
		Sort nameSort = new Sort("name", SortType.ASC);
		Sort uuidSort = new Sort("uuid", SortType.ASC);
		assertEquals("select p from Product as p order by name asc, uuid asc", uuidSort.appendToQuery(nameSort.appendToQuery("select p from Product as p")));
		assertEquals("select p from Product as p where p.name is not null order by name asc, uuid asc", uuidSort.appendToQuery(nameSort.appendToQuery("select p from Product as p where p.name is not null")));
	}
	
	@Test
	public void appendMultipleToQueryTest() {
		Sort nameSort = new Sort("name", SortType.ASC);
		Sort uuidSort = new Sort("uuid", SortType.ASC);
		assertEquals("select p from Product as p order by name asc, uuid asc", Sort.appendMultipleToQuery("select p from Product as p", nameSort, uuidSort));
		assertEquals("select p from Product as p where p.name is not null order by name asc, uuid asc", Sort.appendMultipleToQuery("select p from Product as p where p.name is not null", nameSort, uuidSort));
	}
	
	
	
}
