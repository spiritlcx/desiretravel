package com.desire.assist;

public class JsonModel {
	String content = "{";
	public void add(String key, String value){
		content += key + ":" + value + ',';
	}
	public String build(){
		content += "}";
		content = content.replace(",}", "}");
		return content;
	}	
}
