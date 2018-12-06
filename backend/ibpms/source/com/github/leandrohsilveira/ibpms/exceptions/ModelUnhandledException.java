package com.github.leandrohsilveira.ibpms.exceptions;

public class ModelUnhandledException extends ModelException {

	private static final long serialVersionUID = 8372459960814357633L;

	public ModelUnhandledException(Throwable cause) {
		super("Ocorreu um erro inesperado no servidor", 500, cause);
	}

}
