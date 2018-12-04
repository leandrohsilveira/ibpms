package com.github.leandrohsilveira.ibpms.query;

import java.io.Serializable;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class Pagination implements Serializable {

	private static final long serialVersionUID = 4595120558810469324L;
	
	@NonNull
	private Integer page;
	
	@NonNull
	private Integer limit;
	
	public Integer getFirst() {
		return page * limit - limit;
	}
	
	public String appendToQuery(String query) {
		return new StringBuilder(query).append(" offset ").append(getFirst()).append(" limit ").append(getLimit()).toString();
	}
	
}
