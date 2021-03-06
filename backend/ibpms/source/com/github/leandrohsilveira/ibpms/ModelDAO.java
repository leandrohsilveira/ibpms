package com.github.leandrohsilveira.ibpms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.github.leandrohsilveira.ibpms.exceptions.ModelException;
import com.github.leandrohsilveira.ibpms.exceptions.ModelUnhandledException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ModelDAO<T extends Model> {
	
	@NonNull
	protected Connection connection;
	
	protected abstract T toModel(ResultSet set) throws SQLException;
	
	protected abstract Map<String, Object> mergeChanges(T current, T next);
	
	protected void setParamsToStatements(List<Object> params, PreparedStatement... statements) {
		execute(() -> {
			for (PreparedStatement statement : Arrays.asList(statements)) {
				for(int i = 0; i < params.size(); i++) {
					Object value = params.get(i);
					if(value != null) statement.setObject(i + 1, value);
					else statement.setNull(i + 1, Types.VARCHAR);
				}
			}
			return null;
		});
	}
	
	protected Optional<T> getSingleResult(PreparedStatement statement) {
		return execute(() -> {
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				return Optional.of(toModel(resultSet));
			}
			return Optional.empty();
		});
	}
	
	protected Long getCountResult(PreparedStatement statement) {
		return execute(() -> getCountResult(statement, null));
	}
	
	protected Long getCountResult(PreparedStatement statement, String columnName) {
		return execute(() -> {
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				if(columnName != null) {
					return resultSet.getLong(columnName);
				} else {
					return resultSet.getLong(1);
				}
			}
			return 0l;
		});
	}
	
	protected List<T> getResultList(PreparedStatement statement) {
		return execute(() -> {
			ArrayList<T> output = new ArrayList<T>();
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				output.add(toModel(resultSet));
			}
			return output;
		});
	}
	
	protected <R> R execute(ManagedExecution<R> execution) {
		try {
			return execution.execute();
		} catch (ModelException e) {
			throw e;
		} catch (Exception e) {
			throw new ModelUnhandledException(e);
		}
	}
	
	public static interface ManagedExecution<R> {
		
		R execute() throws Exception;
		
	}
	
}
