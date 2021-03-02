package com.ait.mocks;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.ait.wine.Wine;
import com.ait.wine.WineDAO;
import com.ait.wine.WineResource;
import org.apache.http.HttpStatus;


class WineResourceTest {
	
	 List<Wine> wines;
	 WineResource wineResource;
	@Mock 
	private WineDAO wineDAO;

	@BeforeEach
	void test() {
		MockitoAnnotations.initMocks(this);
		wineResource = new WineResource();
		wineResource.wineDao=wineDAO;
		Wine wine=new Wine();
	    wine.setCountry("GREECE");
	    wine.setGrapes("Grenache");
	    wine.setCountry("1984");
	    wine.setName("NEW");
	    wines = new ArrayList<Wine>();
	    wines.add(wine);
	    Wine wine1=new Wine();
	    wine1.setCountry("FRANCE");
	    wine1.setGrapes("Merlot");
	    wine1.setCountry("1989");
	    wine1.setName("NEWTWO");
	    wines.add(wine1);
	    
	    
	}
	
	@Test
	public void findAllWines() {
		Mockito.when(wineDAO.findAll()).thenReturn(wines);
		Response response=wineResource.findAll();
		List<Wine> wineList = (List<Wine>) response.getEntity();
		assertEquals(HttpStatus.SC_OK, response.getStatus());
		assertEquals(wineList.size(), 2);
		Wine wine=wineList.get(0);
		assertEquals("NEW",wine.getName());
		wine=wineList.get(1);
		assertEquals("NEWTWO",wine.getName());

	}

}
