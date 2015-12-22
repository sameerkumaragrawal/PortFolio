package org.sameer.java;

import org.json.JSONObject;

public class Strategy {
	private int id;
	private double weight;
	
	public Strategy(int id, double wt){
		this.id = id;
		this.weight = wt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public static Strategy fromJson(JSONObject obj){
		try{
			int id = obj.getInt("id");
			double wt = obj.getDouble("weight");
			return new Strategy(id, wt);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
