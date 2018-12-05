package com.github.leandrohsilveira.ibpms;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ModelService<T extends Model> implements Serializable {
	
	private static final long serialVersionUID = -4350096482532117780L;
	
	@Inject
	private DataSource dataSource;
	
	protected <R> R withCommitableConnection(ManagedConnection<R> managedConnectionFunction) {
		return withConnection(connection -> {
			try {
				R result = managedConnectionFunction.applyWhenConnected(connection);
				if(!connection.getAutoCommit()) {
					connection.commit();
				}
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
			if(!connection.getAutoCommit()) {
				connection.rollback();
			}
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
