package com.desire.transformer;

import java.util.HashMap;

public class TransformerFactory {
	public DataTransformer createTransformer(String response, HashMap<String, String> flightsource){
		switch(flightsource.get("format")){
		case "xml":
			return new XMLTransformer(response, flightsource);
		case "json":
			return new JsonTransformer(response, flightsource);
		}
		return null;
	}

}
