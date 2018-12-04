package com.github.leandrohsilveira.ibpms.query;

import java.util.stream.Stream;

public enum SortType {

	ASC,
	DESC;
	
	@Override
	public String toString() {
		return name().toLowerCase();
	}
	
	public static SortType fromString(String type) {
		return Stream.of(values()) //
					.filter(s -> type.trim().equalsIgnoreCase(s.name())) //
					.findFirst() //
					.orElse(null);
	}
	
}
