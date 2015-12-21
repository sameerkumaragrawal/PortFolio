
package org.sameer.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sameer.java.PortFolio;
import org.sameer.java.User;

public class DatabaseConnection {

    //private global members
    private static DatabaseConnection db = null;
	private Connection con = null;
    private static final String DB_USERNAME = "sameer";
    private static final String DB_PASSWORD = "123456";
    private static final String URL = "jdbc:mysql://localhost/webApp";
    /*
    private String CATEGORY = null;
    private String SORT_BY_1 = "popularity";
    private String SORT_BY_2 = null;
    private String ORDER = "desc";
    */

    //**************************************************************************
    //Constructor which initializes the connection
    private DatabaseConnection() {
        try {
            if (con == null) {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static DatabaseConnection getInstance(){
    	if(db == null){
    		db = new DatabaseConnection();
    	}
    	return db;
    }
    
    //check if its a valid login and returns the user details
    public User loginCheck(String username, String password) {
        try{
        	ResultSet rs = null;
            PreparedStatement prepStmt = con.prepareStatement("select id,email from user where email = ? and password = PASSWORD(?)");
            prepStmt.setString(1, username);
            prepStmt.setString(2, password);
            rs = prepStmt.executeQuery();
            if(rs.next()){
            	return new User(rs.getInt(1), rs.getString(2));
            }else{
            	return null;
            }
        }catch(Exception e){
        	e.printStackTrace();
        	return null;
        }
    }
    
    //check whether the email exists
    private boolean checkEmail(String email) throws SQLException {
        try{
	    	PreparedStatement prepStmt = con.prepareStatement("select email from user where email = ?");
	        prepStmt.setString(1, email);
	        ResultSet rs = prepStmt.executeQuery();
	        if(rs.next()){
	            return true;
	        }
	        else{
	            return false;
	        }
        }catch(Exception e){
        	e.printStackTrace();
        	return true;
        }
    }
    //***************************************************************************
    //insert into customer
    public int signupUser(String email, String password){
        try{
        	boolean emailExists = checkEmail(email);
            if(emailExists){
            	return -1;
            }
            PreparedStatement prepStmt = con.prepareStatement("insert into user (email, password) values (?, PASSWORD(?))", Statement.RETURN_GENERATED_KEYS);
            prepStmt.setString(1, email);
            prepStmt.setString(2, password);
            prepStmt.executeUpdate();
            ResultSet rs = prepStmt.getGeneratedKeys();
            if(rs.next()){
            	return rs.getInt(1); 
            }
            return -1;
        }catch(Exception e){
        	e.printStackTrace();
        	return -1;
        }
    }
    
    //insert into investment
    public synchronized int makeInvestment(int user_id, PortFolio pf){
    	int invest_id = 0;
    	try{
			PreparedStatement prepStmt = con.prepareStatement("insert into investment (user_id) values (?)", Statement.RETURN_GENERATED_KEYS);
	        prepStmt.setObject(1, user_id);
	        prepStmt.executeUpdate();
	        ResultSet rs = prepStmt.getGeneratedKeys();
	        
	        if(rs.next())
	        	invest_id = rs.getInt(1);
	        pf.setId(invest_id);
	        insertTransaction(pf);
        }catch(Exception e){
        	e.printStackTrace();
        }
    	return invest_id;
    }
    
    //insert into transactions
    public synchronized void insertTransaction( PortFolio pf){
    	Timestamp tstamp = new Timestamp(new Date().getTime());
    	try{
			PreparedStatement prepStmt = con.prepareStatement("insert into transactions (investment_id, portfolio, timestamp) values (?,?,?)");
			prepStmt.setInt(1, pf.getId());
			prepStmt.setObject(2, pf.toJSON().toString());
			prepStmt.setTimestamp(3, tstamp);
	        prepStmt.executeUpdate();
	        pf.setTimestamp(tstamp);
        }catch(Exception e){
        	e.printStackTrace();
        }
    }

    public List<PortFolio> getInvestments(int user_id){
    	try{
	    	PreparedStatement prepStmt = con.prepareStatement("select id from investment where user_id = ?");
	        prepStmt.setInt(1, user_id);
	        ResultSet rs = prepStmt.executeQuery();
	        List<PortFolio> invests = new ArrayList<>();
			while(rs.next()){
				PortFolio p = new PortFolio(rs.getInt(1));
				invests.add(p);
			}
	        return invests;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
    
    public PortFolio getPortfolio(int invest_id){
    	try{
	    	PreparedStatement prepStmt = con.prepareStatement("select portfolio,timestamp from transactions where investment_id = ? order by timestamp desc limit 1");
	        prepStmt.setInt(1, invest_id);
	        ResultSet rs = prepStmt.executeQuery();
	        PortFolio pf = null;
			while(rs.next()){
				pf = PortFolio.fromString(invest_id, rs.getString(1));
				pf.setTimestamp(rs.getTimestamp(2));
			}
	        return pf;
    	}catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
    }
}
