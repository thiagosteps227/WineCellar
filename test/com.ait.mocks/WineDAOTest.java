package com.ait.mocks;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.anyString;
import com.ait.wine.ConnectionHelper;
import com.ait.wine.Wine;
import com.ait.wine.WineDAO;
  //mock ConnectionHelper and JDBC Driver to test WineDAO
class WineDAOTest {
    WineDAO wineDAO;
    Wine wine;
    ConnectionHelper connectionHelper;
    ResultSet resultSet;
    Connection connection;
    Statement statement;
    
    @BeforeEach
    void setUp() throws Exception {
    	  wine=new Wine();
    	    wine.setCountry("GREECE");
    	    wine.setGrapes("Grenache");
    	    wine.setCountry("1984");
    	    wine.setName("NEW");
    	connectionHelper = Mockito.mock(ConnectionHelper.class);
    	wineDAO=new WineDAO();
    	resultSet = Mockito.mock(ResultSet.class);
		connection = Mockito.mock(Connection.class);
		statement = Mockito.mock(Statement.class);
		Mockito.when(connectionHelper.getConnection()).thenReturn(connection);
		Mockito.when(connection.createStatement()).thenReturn(statement);
		Mockito.when(statement.executeQuery(anyString())).thenReturn(resultSet);
    }
	@Test
	void testFindAllWines() throws Exception{
		Mockito.when(resultSet.next()).thenReturn(true).thenReturn(true).thenReturn(true).thenReturn(false);
		Mockito.when(resultSet.getInt("id")).thenReturn(1).thenReturn(2).thenReturn(3);
		Mockito.when(resultSet.getString("name")).thenReturn("ONE").thenReturn("TWO").thenReturn("THREE");
		Mockito.when(resultSet.getString("grapes")).thenReturn("Merlot").thenReturn("Chardonnay").thenReturn("Grenache");
		Mockito.when(resultSet.getString("country")).thenReturn("SPAIN").thenReturn("FRANCE").thenReturn("GREECE");
		Mockito.when(resultSet.getString("year")).thenReturn("1995").thenReturn("2002").thenReturn("2010");
		List<Wine> wineList = wineDAO.findAll();
		//Response.Status.BAD_REQUEST
		assertEquals(3,wineList.size());
		Wine wine=wineList.get(0);
		assertEquals("ONE",wine.getName());
		wine=wineList.get(1);
		assertEquals("TWO",wine.getName());
		wine=wineList.get(2);
		assertEquals("THREE",wine.getName());
		assertEquals("Grenache",wine.getGrapes());
		assertEquals("GREECE",wine.getCountry());
		assertEquals("2010",wine.getYear());
		
	}
	
	@Test
	void testFindAllWinesException() throws Exception{
		Mockito.when(connection.createStatement()).thenThrow(SQLException.class);
		assertThrows(Exception.class,()->{
			wineDAO.findAll();
		});

}
	
	@Test
	void testCreateWine() throws Exception{
    PreparedStatement ps=Mockito.mock(PreparedStatement.class);
	String[] ids={"ID"};
	Mockito.when(connection.prepareStatement("INSERT INTO wine (name, grapes, country, year) VALUES (?, ?, ?, ?)",ids)).thenReturn(ps);
	resultSet = Mockito.mock(ResultSet.class);
    Mockito.when(ps.getGeneratedKeys()).thenReturn(resultSet);
    Mockito.when(resultSet.getInt(1)).thenReturn(5);
    wineDAO=new WineDAO();
    Wine createdWine= wineDAO.create(wine);
    assertEquals(5,createdWine.getId());
    assertEquals("NEW", createdWine.getName());

}
	
	@Test
	void testCreateWineException() throws Exception{
		Connection connection = Mockito.mock(Connection.class);
		Mockito.when(connectionHelper.getConnection()).thenReturn(connection);
		String[] ids={"ID"};
		 PreparedStatement ps=Mockito.mock(PreparedStatement.class);
		Mockito.when(connection.prepareStatement("INSERT INTO wine (name, grapes, country, year) VALUES (?, ?, ?, ?)",ids)).thenThrow(SQLException.class);
		assertThrows(Exception.class,()->{
			wineDAO.create(wine);
		});

}
}
