package com.github.leandrohsilveira.ibpms;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Function;

import javax.inject.Inject;
import javax.sql.DataSource;

import com.github.leandrohsilveira.ibpms.exceptions.ModelException;
import com.github.leandrohsilveira.ibpms.exceptions.ModelUnhandledException;

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
			} catch (ModelException e) {
				rollback(connection);
				throw e;
			} catch (SQLException e) {
				rollback(connection);
				throw new ModelUnhandledException(e);
			}
		});
	}
	
	protected <R> R withConnection(ManagedConnection<R> managedConnectionFunction) {
		try (Connection connection = dataSource.getConnection()) {
			return managedConnectionFunction.applyWhenConnected(connection);
		} catch (SQLException e) {
			throw new ModelUnhandledException(e);
		}
	}
	
	public <R, D extends ModelDAO<T>> R withDAO(Function<Connection, D> daoSupplier, ManagedDAO<R, T, D> dao) {
		return withConnection(connection -> {
			return dao.applyWithDAO(daoSupplier.apply(connection));
		});
	}
	
	public <R, D extends ModelDAO<T>> R withCommitableDAO(Function<Connection, D> daoSupplier, ManagedDAO<R, T, D> dao) {
		return withCommitableConnection(connection -> {
			return dao.applyWithDAO(daoSupplier.apply(connection));
		});
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
	public static interface ManagedDAO<R, M extends Model, D extends ModelDAO<M>> {
		
		R applyWithDAO(D dao);
		
	}
	
	@FunctionalInterface
	public static interface ManagedConnection<R> {
		
		R applyWhenConnected(Connection connection);
		
	}
	
}
