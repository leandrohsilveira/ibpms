package com.github.leandrohsilveira.ibpms.product.exceptions;

import java.text.MessageFormat;

import com.github.leandrohsilveira.ibpms.exceptions.ModelException;

public class ProductCodeConflictException extends ModelException {

	private static final long serialVersionUID = 8286403325325614450L;

	public ProductCodeConflictException(String code) {
		super(MessageFormat.format("Já existe um produto cadastrado com o código {0}", code), 409);
	}

}
