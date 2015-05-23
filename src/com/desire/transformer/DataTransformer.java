package com.desire.transformer;

import java.util.ArrayList;
import java.util.HashMap;

import com.desire.store.Triple;

public class DataTransformer {
	protected String response;
	protected HashMap<String, String> flightsource;
	public DataTransformer(String response, HashMap<String, String> flightsource){
		this.response = response;
		this.flightsource = flightsource;
	}
	public ArrayList<Triple> transform(){
		return null;
	}
}
