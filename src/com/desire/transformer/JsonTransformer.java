package com.desire.transformer;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.desire.assist.Constant;
import com.desire.entity.Flight;
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
			triples.add(new Triple(flightcode, Constant.departuretime, flight.get(flightsource.get("departuretime")).toString()));
			triples.add(new Triple(flightcode, Constant.price, flight.get(flightsource.get("price")).toString()));
		}
		return triples;
	}
	public ArrayList<Flight> transformToFlight(){
		ArrayList<Flight> flights = new ArrayList<Flight>();
		JSONObject jsonRes = new JSONObject(response);
		JSONArray flight = (JSONArray)jsonRes.get("trip");
		for(int i = 0; i < flight.length(); i++){
			JSONObject fligh = (JSONObject)flight.get(i);
			String flightcode = fligh.get(flightsource.get("flightcode")).toString();
			Flight fli = new Flight();
			fli.setArrivaltime(fligh.get(flightsource.get("arrivaltime")).toString());
			fli.setDeparturetime(fligh.get(flightsource.get("departuretime")).toString());
			fli.setPrice(fligh.get(flightsource.get("price")).toString());
			flights.add(fli);
		}
		return flights;
	}
}
