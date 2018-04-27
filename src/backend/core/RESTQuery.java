package backend.core;

import javax.json.JsonArray;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class RESTQuery 
{
	private String url;
	
	public static boolean DEBUG=true; 
	
	public RESTQuery(String url)
	{
		this.url=url;
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
		JsonArray jsonResponse = webTarget
					.request(MediaType.APPLICATION_JSON_TYPE)
					.get(JsonArray.class);
		
		if(DEBUG)
			System.out.println("REST query completed");
			
		
		return jsonResponse;
			
	}
}
