package com.ait.wine;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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
