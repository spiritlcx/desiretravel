package com.desire.entity;

public class Place implements Comparable<Place>{
	private String name;
	private double mark;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMark() {
		return mark;
	}
	public void setMark(double mark) {
		this.mark = mark;
	}
	@Override
	public int compareTo(Place place) {
		// TODO Auto-generated method stub
		return (int) (place.getMark() - mark);
	}
}
