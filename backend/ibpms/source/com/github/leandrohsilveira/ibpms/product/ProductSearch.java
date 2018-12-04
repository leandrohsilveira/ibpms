package com.github.leandrohsilveira.ibpms.product;

import com.github.leandrohsilveira.ibpms.query.Pagination;
import com.github.leandrohsilveira.ibpms.query.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Getter
public class ProductSearch {
	
	private String uuid;
	
	private String name;
	
	@NonNull
	private Pagination pagination;
	
	@NonNull
	private Sort sort;
	
}
