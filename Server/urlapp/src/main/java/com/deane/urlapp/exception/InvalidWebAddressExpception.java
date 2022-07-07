package com.deane.urlapp.exception;

public class InvalidWebAddressExpception extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public InvalidWebAddressExpception(String message) {
		super(message);
	}
}
