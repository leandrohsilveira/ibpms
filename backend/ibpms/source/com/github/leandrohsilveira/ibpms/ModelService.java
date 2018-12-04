package com.github.leandrohsilveira.ibpms;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ModelService<T extends Model> implements Serializable {
	
	private static final long serialVersionUID = -4350096482532117780L;
	
	@Inject
	private DataSource dataSource;
	
	protected abstract T toModel(ResultSet set) throws SQLException;
	
	protected void setParamsToStatements(List<Object> params, PreparedStatement... statements) throws SQLException {
		for (PreparedStatement statement : Arrays.asList(statements)) {
			for(int i = 0; i < params.size(); i++) {
				statement.setObject(i+1, params.get(i));
			}
		}
	}
	
	protected Optional<T> getSingleResult(PreparedStatement statement) throws SQLException {
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			return Optional.of(toModel(resultSet));
		}
		return Optional.empty();
	}
	
	protected Long getCountResult(PreparedStatement statement) throws SQLException {
		ResultSet resultSet = statement.executeQuery();
		if(resultSet.next()) {
			resultSet.getLong(1);
		}
		return 0l;
	}
	
	protected List<T> getResultList(PreparedStatement statement) throws SQLException {
		ArrayList<T> output = new ArrayList<T>();
		ResultSet resultSet = statement.executeQuery();
		while(resultSet.next()) {
			output.add(toModel(resultSet));
		}
		return output;
	}
	
	protected <R> R withCommitableConnection(ManagedConnection<R> managedConnectionFunction) {
		return withConnection(connection -> {
			try {
				R result = managedConnectionFunction.applyWhenConnected(connection);
				connection.commit();
				return result;
			} catch (Exception e) {
				rollback(connection);
				throw e;
			}
		});
	}
	
	protected <R> R withConnection(ManagedConnection<R> managedConnectionFunction) {
		try (Connection connection = dataSource.getConnection()) {
			return managedConnectionFunction.applyWhenConnected(connection);
		} catch (Exception e) {
			log.error("Unexpected error {}", e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	private void rollback(Connection connection) {
		try {
			log.warn("Rolling back connection {}", connection);
			connection.rollback();
			log.warn("Connection {} rolled back", connection);
		} catch (SQLException e) {
			log.error("Failed to rollback: {}", e.getMessage(), e);
		}
	}
	
	@FunctionalInterface
	public static interface ManagedConnection<R> {
		
		R applyWhenConnected(Connection connection) throws Exception;
		
	}
	
}
