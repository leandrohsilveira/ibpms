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
		if(value != null) {
			return String.format("%%%s", value);
		}
		return null;
	}
	
	public static String likeEndsWith(String value) {
		if(value != null) {
			return String.format("%s%%", value);
		}
		return null;
	}
	
	public static String likeContains(String value) {
		if(value != null) {
			return String.format("%%%s%%", value);
		}
		return null;
	}

	public static Object likeStartsUppercaseWith(String value) {
		if(value != null) {
			return likeStartsWith(value).toUpperCase();
		}
		return null;
	}

	public static Object likeEndsUppercaseWith(String value) {
		if(value != null) {
			return likeEndsWith(value).toUpperCase();
		}
		return null;
	}

	public static Object likeContainsUppercase(String value) {
		if(value != null) {
			return likeContains(value).toUpperCase();
		}
		return null;
	}
	
}
