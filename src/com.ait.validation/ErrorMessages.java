package com.ait.validation;

public enum ErrorMessages {
	EMPTY_FIELDS("One or more empty fields"),
	ALREADY_EXISTS("Wine with given name and year already exists"),
	INVALID_COUNTRY("Invalid Country"),
	BAD_YEAR("That was a bad year for wine"),
	NOT_MATURE("Wine is not mature enough");
	
	private String errorMessage;
	
	ErrorMessages(String errMsg){
		this.errorMessage=errMsg;
	}
	
	public String getMsg(){
		return errorMessage;
	}
}
