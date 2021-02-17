package com.ait.validation;

public class WineVintageExistsException extends WineException {

	private static final long serialVersionUID = 334051992916748022L;

	public WineVintageExistsException(final String errorMessage) {
		super(errorMessage);
	}

}

