package com.ait.unit.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.ait.validation.CheckVintageExists;
import com.ait.validation.WineException;
import com.ait.validation.WineValidationException;
import com.ait.validation.WineVintageExistsException;
import com.ait.wine.Wine;

class CheckVintageExistsTest {
	Wine wineOne=createWine(1,"BLOCK NINE", "SPAIN","Merlot","2004");
	Wine wineTwo=createWine(2,"PINOT NOIR", "FRANCE","Grenache","2002");;
	Wine wineThree=createWine(3,"FAUSTINO", "GREECE","Shiraz","2000");
	List<Wine> wines=new ArrayList<Wine>();
	CheckVintageExists checkVintageExists;
	
	
	@BeforeEach
	void setUp() {
		wines.add(wineOne);
		wines.add(wineTwo);
		wines.add(wineThree);
		checkVintageExists = new CheckVintageExists();
		
	}

	@Test
	void testVintageYearExistsOK() throws WineException {
		Wine wine = new Wine();
		wine.setId(4);
		wine.setCountry("SPAIN");
		wine.setGrapes("Merlot");
		wine.setName("SANTA ROSA");
		wine.setYear("2004");
		checkVintageExists.checkForVintage(wine,wines);
	}
	
	@Test
	void testVintageNameExistsOK() throws WineException {
		Wine wine = new Wine();
		wine.setId(4);
		wine.setCountry("SPAIN");
		wine.setGrapes("Merlot");
		wine.setName("BLOCK NINE");
		wine.setYear("2003");
		checkVintageExists.checkForVintage(wine,wines);
	}
	
	@Test
	void testVintageExistsException() throws WineException {
		Wine wine = new Wine();
		wine.setId(4);
		wine.setCountry("SPAIN");
		wine.setGrapes("Merlot");
		wine.setName("BLOCK NINE");
		wine.setYear("2004");
		Exception exception = assertThrows(WineVintageExistsException.class,()->{
			checkVintageExists.checkForVintage(wine,wines);
		});
		
	}
	
	private Wine createWine(int id, String name, String country, String grapes, String year) {
		Wine wine=new Wine();
		wine.setId(id);
		wine.setCountry(country);
		wine.setName(name);
		wine.setYear(year);
		return wine;
	}

}
