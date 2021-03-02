package com.ait.unit.test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;
import com.ait.validation.ErrorMessages;
import com.ait.validation.WineException;
import com.ait.validation.WineValidationException;
import com.ait.validation.WineValidator;
import com.ait.wine.Wine;

public class WineValidationTest {
	WineValidator wineValidator;
	Wine wine;
	//List<Wine> wines;
	
	@BeforeEach
	public void setUp() {
		wine = new Wine();
		wineValidator = new WineValidator();
		wine.setId(7);
		wine.setName("BODEGA LURTON");
		wine.setCountry("FRANCE");
		wine.setYear("2012");
		wine.setGrapes("Merlot");	
	}
	
	//No exception expect validation works
	@Test
	void testAllFieldsOK() throws WineException {
		wineValidator.validateWine(wine);
	}
	
	@Test
	void testForEmptyNameField() throws WineException {
		wine.setName("");
		Exception exception = assertThrows(WineValidationException.class,()->{
			wineValidator.validateWine(wine);
		});
	assertEquals(ErrorMessages.EMPTY_FIELDS.getMsg(),exception.getMessage());
	}
	
	@Test
	void testForEmptyFieldCountry() throws WineException {
		wine.setCountry("");
		Exception exception = assertThrows(WineValidationException.class,()->{
			wineValidator.validateWine(wine);
		});
	assertEquals(ErrorMessages.EMPTY_FIELDS.getMsg(),exception.getMessage());
	}
	
	@Test
	void testForEmptyFieldGrapes() throws WineException {
		wine.setGrapes("");
		Exception exception = assertThrows(WineValidationException.class,()->{
			wineValidator.validateWine(wine);
		});
	assertEquals(ErrorMessages.EMPTY_FIELDS.getMsg(),exception.getMessage());
	}
	
	@Test
	void testForEmptyFieldYear() throws WineException {
		wine.setYear("");
		Exception exception = assertThrows(WineValidationException.class,()->{
			wineValidator.validateWine(wine);
		});
	assertEquals(ErrorMessages.EMPTY_FIELDS.getMsg(),exception.getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings= {"SPaIN","france","GREECE"})
	void testForValidCountry(String country) throws WineException {
		wine.setCountry(country);
		wineValidator.validateWine(wine);
	}
	
	@Test
	void testForInvalidCountry() throws WineException {
		wine.setCountry("USA");
		Exception exception = assertThrows(WineValidationException.class,()->{
			wineValidator.validateWine(wine);
		});
	assertEquals(ErrorMessages.INVALID_COUNTRY.getMsg(),exception.getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings= {"2001","2005","1998"})
	void testForBadYears(String year) throws WineException {
		wine.setYear(year);
		Exception exception = assertThrows(WineValidationException.class,()->{
			wineValidator.validateWine(wine);
		});
	assertEquals(ErrorMessages.BAD_YEAR.getMsg(),exception.getMessage());
	}
	
	@ParameterizedTest
	@ValueSource(strings= {"2020","2016","2018"})
	void testForWineNotMature(String year) throws WineException {
		wine.setYear(year);
		Exception exception = assertThrows(WineValidationException.class,()->{
			wineValidator.validateWine(wine);
		});
	assertEquals(ErrorMessages.NOT_MATURE.getMsg(),exception.getMessage());
	}
}
