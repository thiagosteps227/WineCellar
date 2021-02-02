package com.ait.wine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.PreparedStatement;


public class WineDAO {

    public List<Wine> findAll() {
        List<Wine> list = new ArrayList<Wine>();
        Connection c = null;
    	String sql = "SELECT * FROM wine ORDER BY name";
        try {
            c = ConnectionHelper.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                list.add(processRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
        return list;
    }
    
    public Wine findById(int id) {
    	String sql = "SELECT * FROM wine WHERE id=?";
    	Wine wine = null;
    	Connection c = null;
    	try {
    		c = ConnectionHelper.getConnection();
    		PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
    		ps.setInt(1, id);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			wine = processRow(rs);
    		}
    	} catch (Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	} finally {
    		ConnectionHelper.close(c);
    	}
    	return wine;
    }
    
    public List<Wine> findByName(String name) {
    	List<Wine> list = new ArrayList<Wine>();
    	Connection c= null;
    	String sql = "SELECT * FROM wine AS e WHERE UPPER(name) LIKE ? ORDER BY name";
    	try {
    		c = ConnectionHelper.getConnection();
    		java.sql.PreparedStatement ps = c.prepareStatement(sql);
    		ps.setString(1, "%"+name.toUpperCase()+"%");
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			list.add(processRow(rs));
    		}
    	} catch (SQLException e){
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	} finally {
    		ConnectionHelper.close(c);
    	}
    	return list;
    }
    
    public List<Wine> findByCountryAndGrapes(String country, String grapes){
    	List<Wine> list = new ArrayList<Wine>();
    	Connection c= null;
    	String sql = "SELECT * FROM wine AS e WHERE UPPER(country) LIKE ? AND UPPER(grapes) LIKE ? "
    			+ "ORDER BY name";
    	try {
    		c = ConnectionHelper.getConnection();
    		PreparedStatement ps = (PreparedStatement) c.prepareStatement(sql);
    		ps.setString(1, "%"+country.toUpperCase()+"%");
    		ps.setString(2, "%"+grapes.toUpperCase()+"%");
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			list.add(processRow(rs));
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		throw new RuntimeException(e);
    	} finally {
    	ConnectionHelper.close(c);
    	}
    	return list;
    }


    
   
    protected Wine processRow(ResultSet rs) throws SQLException {
        Wine wine = new Wine();
        wine.setId(rs.getInt("id"));
        wine.setName(rs.getString("name"));
        wine.setGrapes(rs.getString("grapes"));
        wine.setCountry(rs.getString("country"));
        wine.setRegion(rs.getString("region"));
        wine.setYear(rs.getString("year"));
        wine.setPicture(rs.getString("picture"));
        wine.setDescription(rs.getString("description"));
        return wine;
    }
    
}
