package com.desire.store;

public class Triple {
	private String subject;
	private String predicate;
	private String object;

	public Triple(String subject, String property, String object){
		this.subject = subject;
		this.predicate = property;
		this.object = object;
	}
	
	public String toString(){
		return subject + " " + predicate + " " + object;
	}
	
	public void setSubject(String subject){
		this.subject = subject;
	}
	public void setPredicate(String predicate){
		this.predicate = predicate;
	}
	public void setObject(String object){
		this.object = object;
	}
	public String getSubject(){
		return subject;
	}
	public String getPredicate(){
		return predicate;
	}
	public String getObject(){
		return object;
	}

}
