package com.desire.acquisition;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpRequest;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.desire.xml.MetaRead;

public class ApiExtractor {
	
	public ApiExtractor(){
		
	}
	public void sendHttpRequest(String targetURL, String content){
		HttpURLConnection connection = null;  
		  try {
			  System.out.println(targetURL);
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection)url.openConnection();
		    connection.setRequestMethod("GET");
		    connection.setRequestProperty("Content-Type", 
		        "application/json");

		    connection.setUseCaches(false);
		    connection.setDoOutput(true);

		    //Send request
		    DataOutputStream wr = new DataOutputStream (
		        connection.getOutputStream());
		    wr.writeBytes(content);
		    wr.close();

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuilder response = new StringBuilder();
		    String line;
		    while((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }
		    rd.close();
		    
		    System.out.println(response);
		    
		  } catch (Exception e) {
		    e.printStackTrace();
		    return;
		  } finally {
		    if(connection != null) {
		      connection.disconnect(); 
		    }
		  }	}
}
