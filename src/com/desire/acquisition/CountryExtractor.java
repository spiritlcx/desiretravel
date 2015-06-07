package com.desire.acquisition;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.desire.assist.Constant;
import com.desire.store.StoreManager;
import com.desire.store.Triple;


public class CountryExtractor {
	public static void main(String[] args){
		CountryExtractor csv = new CountryExtractor();
		csv.extract();
	}
	
	StoreManager storeManager = new StoreManager();

	public CountryExtractor(){
		storeManager.create();
	}
		
		
	public void extract(){
		
		String csvFile = "resources/Countries-Continents-csv.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		ArrayList<Triple> triples = new ArrayList<Triple>();
		try{
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] row = line.split(cvsSplitBy);
				
				String subject = Constant.continent + row[0];
				String predicate = Constant.contintentof;
				String object = row[1];
				object = object.replace(" ", "_");
				subject = subject.replace(" ", "_");
								
				triples.add(new Triple(subject, predicate, object));
			}
			storeManager.store(triples);

		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}