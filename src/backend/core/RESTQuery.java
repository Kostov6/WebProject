package backend.core;

import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class RESTQuery 
{
	private String url;
	
	private ArrayList<String> headerNames;
	private ArrayList<String> headerValues;
	
	public static boolean DEBUG=true; 
	
	public RESTQuery(String url)
	{
		headerNames = new ArrayList<String>();
		headerValues = new ArrayList<String>();
		
		this.url=url;
	}
	
	public RESTQuery(String url,String... parameters)
	{
		headerNames = new ArrayList<String>();
		headerValues = new ArrayList<String>();
		
		StringBuilder builder=new StringBuilder(url);
		for(String param : parameters)
			builder.append('/').append(param);
		
		this.url=builder.toString();
	}
	
	
	public Object callGET(Class responseType)
	{
		if(responseType.equals(JsonArray.class))
			return callGETJson();
		//TODO add cases if needed
		else if(false)
		{}
		
		return null;
		
	}
	
	public JsonArray callGETJson()
	{
		if(DEBUG)
			System.out.println("From REST : GET "+url +" ...");
			
		//TODO Check if registration nessessary
		WebTarget webTarget = ClientBuilder.newClient()//.register(JsonProcessingFeature.class)
								.target(url);
		Builder responseBuilder=webTarget
				.request(MediaType.APPLICATION_JSON_TYPE);
		
		for(int i=0;i<headerNames.size();i++)
			responseBuilder.header(headerNames.get(i),headerValues.get(i));
		
		JsonArray jsonResponse =responseBuilder 
					.get(JsonArray.class);
		
		if(DEBUG)
			System.out.println("REST query completed");
			
		
		return jsonResponse;
			
	}
	
	public JsonObject callGETJsonObject()
	{
		if(DEBUG)
			System.out.println("From REST : GET "+url +" ...");
			
		//TODO Check if registration nessessary
		WebTarget webTarget = ClientBuilder.newClient()//.register(JsonProcessingFeature.class)
								.target(url);
		Builder responseBuilder=webTarget
				.request(MediaType.APPLICATION_JSON_TYPE);
		
		for(int i=0;i<headerNames.size();i++)
			responseBuilder.header(headerNames.get(i),headerValues.get(i));
		JsonObject jsonResponse;
		try {
			jsonResponse =responseBuilder 
					.get(JsonObject.class);
		}
		catch(RuntimeException e)
		{
			return Json.createObjectBuilder().add("name", Json.createArrayBuilder().build()).build();
		}
		if(DEBUG)
			System.out.println("REST query completed");
			
		
		return jsonResponse;
			
	}

	//headers
	
	public void addHeader(String name, String value) {
		headerNames.add(name);
		headerValues.add(value);
	}
	

}
