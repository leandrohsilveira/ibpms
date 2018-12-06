package com.github.leandrohsilveira.ibpms.product.exceptions;

import com.github.leandrohsilveira.ibpms.exceptions.ModelException;

public class ProductNotFoundException extends ModelException {

	public ProductNotFoundException() {
		super("Produto não encontrado", 404);
	}

	private static final long serialVersionUID = 3322348025363682341L;

}
