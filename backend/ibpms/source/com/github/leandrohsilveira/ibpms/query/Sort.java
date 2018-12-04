package com.github.leandrohsilveira.ibpms.query;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public class Sort {
	
	public Sort(String expression) {
		if(expression != null) {
			String[] sortSplit = expression.split(",");
			if(sortSplit.length >= 1) {
				field = sortSplit[0].trim();
				if(sortSplit.length > 1) {
					type = SortType.fromString(sortSplit[1]);
				}
			}
		}
	}
	
	@NonNull
	private String field;
	
	@NonNull
	private SortType type = SortType.ASC;
	
	public String appendToQuery(String query) {
		if(query != null) {
			StringBuilder builder = new StringBuilder(query);
			if(query.contains("order by")) {
				builder.append(", ");
			} else {
				builder.append(" order by ");
			}
			builder.append(field).append(" ").append(type);
			return builder.toString();
		}
		return null;
	}
	
	public static String appendMultipleToQuery(String query, Sort...sorts) {
		List<Sort> sortArray = Arrays.asList(sorts);
		return appendMultipleToQuery(query, sortArray);
	}

	public static String appendMultipleToQuery(String query, List<Sort> sortArray) {
		if(query != null) {
			String appendedQuery = query;
			for (Sort sort : sortArray) {
				appendedQuery = sort.appendToQuery(appendedQuery);
			}
			return appendedQuery;
		}
		return null;
	}
	
}
