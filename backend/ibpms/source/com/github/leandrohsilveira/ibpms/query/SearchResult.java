package com.github.leandrohsilveira.ibpms.query;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SearchResult<T> implements Serializable {

	private static final long serialVersionUID = -1554510105711368808L;

	private List<T> items;
	
	private Long count;
	
}
