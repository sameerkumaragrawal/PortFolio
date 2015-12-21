package org.sameer.java;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class PortFolio {
	public static final String PORTFOLIO = "portfolio";
	private List<Strategy> strategies;
	private int id;
	private Timestamp timestamp;
	
	public PortFolio(){
		strategies = new ArrayList<>();
	}
	
	public PortFolio(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void addStrategy(Strategy s){
		strategies.add(s);
	}

	public List<Strategy> getStrategies() {
		return strategies;
	}
	
	public int getNumberOfStrategies(){
		return strategies.size();
	}
	
	public JSONObject toJSON(){
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray(strategies);
		try{
			obj.put(PORTFOLIO, arr);
		}catch(Exception e){
			
		}
		return obj;
	}
	
	public static PortFolio fromString(int id, String obj){
		try{
			PortFolio pf = new PortFolio();
			pf.setId(id);
			JSONObject json = new JSONObject(obj);
			JSONArray arr = json.getJSONArray(PORTFOLIO);
			for(int i=0;i<arr.length();i++){
				JSONObject stObj = arr.getJSONObject(i);
				pf.addStrategy(Strategy.fromJson(stObj));
			}
			return pf;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
}
