package com.github.leandrohsilveira.ibpms.product;

import javax.inject.Singleton;

import com.github.leandrohsilveira.ibpms.Error;
import com.github.leandrohsilveira.ibpms.exceptions.ModelException;
import com.github.leandrohsilveira.ibpms.exceptions.ModelUnhandledException;

import kikaha.urouting.api.DefaultResponse;
import kikaha.urouting.api.ExceptionHandler;
import kikaha.urouting.api.Response;
import lombok.extern.slf4j.Slf4j;

@Singleton
@Slf4j
public class ProductExceptionHandler implements ExceptionHandler<ModelException> {

	@Override
	public Response handle(ModelException exception) {
		if(exception instanceof ModelUnhandledException) {
			log.error("Unhandled exception", exception);
		} else {
			log.error(exception.getMessage());
		}
		return DefaultResponse.response(exception.getStatus()).entity(new Error(exception.getMessage()));
	}

}
