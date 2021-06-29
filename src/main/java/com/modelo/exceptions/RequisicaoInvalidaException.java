package com.modelo.exceptions;

public class RequisicaoInvalidaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RequisicaoInvalidaException(String msg) {
		super("Requisicao Invalida Exception: " + msg);
	}

}
