package com.ait.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ait.utils.UtilsDAO;
import com.ait.validation.ErrorMessage;
import com.ait.wine.Wine;
import com.ait.wine.WineResource;

class IntegrationTest {
	
	WineResource wineResource;
	
	@BeforeEach
	public void setUp() throws Exception{
		wineResource = new WineResource();
		UtilsDAO utilsDAO = new UtilsDAO();
		List<Wine> wines= new ArrayList<Wine>();
		Wine wine= new Wine();
		wine.setCountry("SPAIN");
		wine.setGrapes("Merlot");
		wine.setYear("2012");
		wine.setName("BLOCK NINE");
		wines.add(wine);
		wine = new Wine();
		wine.setCountry("FRANCE");
		wine.setGrapes("Chardonay");
		wine.setYear("2010");
		wine.setName("PINOT NOIR");
		wines.add(wine);
		utilsDAO.resetTable(wines);
		
	}

	@Test
	void testGetAllWines() {
		Response response= wineResource.findAll();
		List<Wine> wineList = (List<Wine>) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		assertEquals(wineList.size(), 2);
		Wine wine=wineList.get(0);
		assertEquals("SPAIN",wine.getCountry());
		wine=wineList.get(1);
		assertEquals("FRANCE",wine.getCountry());
		assertEquals("Chardonay",wine.getGrapes());
		assertEquals("2010",wine.getYear());
		assertEquals("PINOT NOIR",wine.getName());
	}
	
	@Test
	public void testAddWine() {
		Wine wine = new Wine();
		wine.setCountry("GREECE");
		wine.setGrapes("green");
		wine.setName("NEW WINE");
		wine.setYear("2009");
		Response response=wineResource.create(wine);
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		wine = (Wine) response.getEntity();
		//assertEquals(wine.getId(), 3);
		assertEquals(wine.getName(), "NEW WINE");
		response= (Response) wineResource.findAll();
		List<Wine> wineList = (List<Wine>) response.getEntity();
		assertEquals(wineList.size(), 3);
	}
	
	@Test
	public void testAddWineWithValidationException() {
		Wine wine = new Wine();
		wine.setCountry("GREECE");
		wine.setGrapes("green");
		wine.setName("NEW WINE");
		wine.setYear("2019");
		Response response= wineResource.create(wine);
		assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatus());
		ErrorMessage message = (ErrorMessage) response.getEntity();
		assertEquals(message.getErrorMessage(), "Wine is not mature enough");
		response= (Response) wineResource.findAll();
		List<Wine> wineList = (List<Wine>) response.getEntity();
		assertEquals(wineList.size(), 2);
	}
	
	@Test
	public void testAddWineWithVintageExistsException() {
		Wine wine = new Wine();
		wine.setCountry("SPAIN");
		wine.setGrapes("green");
		wine.setName("BLOCK NINE");
		wine.setYear("2012");
		Response response=wineResource.create(wine);
		assertEquals(HttpStatus.SC_FORBIDDEN, response.getStatus());
		ErrorMessage message = (ErrorMessage) response.getEntity();
		assertEquals(message.getErrorMessage(), "Wine with given name and year already exists");
		response= (Response) wineResource.findAll();
		List<Wine> wineList = (List<Wine>) response.getEntity();
		assertEquals(wineList.size(), 2);
	}
}
