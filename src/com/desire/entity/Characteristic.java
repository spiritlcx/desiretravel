package com.desire.entity;

public class Characteristic {
	private String name;
	private String city;
	private String uri;

	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return name;
	}

	public double getMark(){
		return 0;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
}
