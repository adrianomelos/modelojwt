package com.modelo.exceptions;

public class RequisicaoInvalidaExceptionForbiden extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RequisicaoInvalidaExceptionForbiden(String msg) {
		super("Requisicao Invalida Exception: " + msg);
	}

}
