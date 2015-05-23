package com.desire.transformer;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.desire.assist.Constant;
import com.desire.store.Triple;

public class JsonTransformer extends DataTransformer{

	public JsonTransformer(String response, HashMap<String, String> flightsource) {
		super(response, flightsource);
		// TODO Auto-generated constructor stub
	}
	public ArrayList<Triple> transform(){
		ArrayList<Triple> triples = new ArrayList<Triple>();
		JSONObject jsonRes = new JSONObject(response);
		JSONArray flights = (JSONArray)jsonRes.get("trip");
		for(int i = 0; i < flights.length(); i++){
			JSONObject flight = (JSONObject)flights.get(i);
			String flightcode = flight.get(flightsource.get("flightcode")).toString();
			triples.add(new Triple(flightcode, Constant.type, Constant.flight));
			triples.add(new Triple(flightcode, Constant.arrivaltime, flight.get(flightsource.get("arrivaltime")).toString()));
			triples.add(new Triple(flightcode, Constant.depaturetime, flight.get(flightsource.get("departuretime")).toString()));
			triples.add(new Triple(flightcode, Constant.price, flight.get(flightsource.get("price")).toString()));
		}
		return triples;
	}
}
