package com.modelo.exceptions;

public class RequisicaoInvalidaExceptionBadRequest extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RequisicaoInvalidaExceptionBadRequest(String msg) {
		super("Requisicao Invalida Exception: " + msg);
	}

}
