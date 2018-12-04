package com.github.leandrohsilveira.ibpms.query;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public final class QueryUtils {
	
	public static String buildQuery(String projection, String where, Pagination pagination, Sort sort) {
		return Optional.of(String.format("%s %s", projection, where)) //
				.map(query -> Optional.ofNullable(pagination).map(p -> p.appendToQuery(query)).orElse(query)) //
				.map(query -> Optional.ofNullable(sort).map(s -> s.appendToQuery(query)).orElse(query)) //
				.get();
	}
	
}
