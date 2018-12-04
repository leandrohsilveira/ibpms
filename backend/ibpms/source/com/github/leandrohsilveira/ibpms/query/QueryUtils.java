package com.github.leandrohsilveira.ibpms.query;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
public final class QueryUtils {
	
	public static String buildQuery(String projection, String where, Pagination pagination, Sort sort) {
		return Optional.of(String.format("%s %s", projection, where)) //
				.map(query -> Optional.ofNullable(sort).map(s -> s.appendToQuery(query)).orElse(query)) //
				.map(query -> Optional.ofNullable(pagination).map(p -> p.appendToQuery(query)).orElse(query)) //
				.get();
	}
	
	public static String likeStartsWith(String value) {
		return String.format("%%%s", value);
	}
	
	public static String likeEndsWith(String value) {
		return String.format("%s%%", value);
	}
	
	public static String likeContains(String value) {
		return String.format("%%%s%%", value);
	}

	public static Object likeStartsUppercaseWith(String value) {
		return likeStartsWith(value).toUpperCase();
	}

	public static Object likeEndsUppercaseWith(String value) {
		return likeEndsWith(value).toUpperCase();
	}

	public static Object likeContainsUppercase(String value) {
		return likeContains(value).toUpperCase();
	}
	
}
