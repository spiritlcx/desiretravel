package com.desire.acquisition;

import java.util.ArrayList;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;

public class SparqlExtractor {
	public void extract() {

	}

	public ArrayList<String> sparqlQuery(String endpoint, String rdfgraph) {
		Query query = QueryFactory.create(rdfgraph);
		ArrayList<String> instances = new ArrayList<String>();
		QueryExecution qexec = QueryExecutionFactory.sparqlService(endpoint, query);
		ResultSet results = null;
		try {
			results = qexec.execSelect();
			while(results.hasNext()){
				QuerySolution soln =results.nextSolution();
				String instance = soln.get("instances").toString();
				instances.add(instance);
			}
			return instances;
		} catch (Exception e) {
			System.out.println("query fails");
			return null;
		} finally {
			qexec.close();
		}
	}
}