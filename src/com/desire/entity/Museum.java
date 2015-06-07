package com.desire.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Museum extends Characteristic{
	private String foundingDate;
	private String visitors;
	private String collection;

	public void setFoundingDate(String foundingDate){
		this.foundingDate = foundingDate;
	}
	public void setVisitors(String visitors){
		this.visitors = visitors;
	}
	public void setCollection(String collection){
		this.collection = collection;
	}
	
	public String getFoundingDate(){
		return foundingDate;
	}
	public String geVisitors(){
		return visitors;
	}
	public String getCollection(){
		return collection;
	}
	public double getMark(){
		try{
			int numvisitor = 0;
			if(visitors != null){
				Pattern p = Pattern.compile("\\d+");
				Matcher m = p.matcher(visitors);
				if(m.find()){
					numvisitor = Integer.parseInt(m.group());
				}
			}
			int numcollection = 0;
	
			if(collection != null){
				Pattern p = Pattern.compile("\\d+");
				Matcher m = p.matcher(collection);
				if(m.find())
					numcollection = Integer.parseInt(m.group());
			}
			
			return 0.8*numvisitor + 0.2*numcollection;
		}catch(Exception e){
			return 0;
		}
	}
}
