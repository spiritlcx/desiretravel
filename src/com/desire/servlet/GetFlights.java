package com.desire.servlet;

import java.util.ArrayList;

import com.desire.entity.Flight;
import com.desire.recommendation.TripleReader;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.opensymphony.xwork2.ActionSupport;


public class GetFlights extends ActionSupport {
	private String characteristic;
	private String continent;
	private String location;
	private String startDate;
	private String endDate;
	private int days;
	
	public String execute() {
    	TripleReader triplerReader = new TripleReader();
    	ArrayList<Flight> flights = triplerReader.read(location,characteristic, continent,  startDate, endDate, days);
    	return "success";
    }

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(String characteristic) {
		this.characteristic = characteristic;
	}

	public String getContinent() {
		return continent;
	}

	public void setContinent(String continent) {
		this.continent = continent;
	}

    public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

}
