package backend.core.json;
import java.text.ParseException;

import javax.json.JsonArray;
import javax.json.JsonValue;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jsonp.JsonProcessingFeature;


public class JSONTest {

	private static final String REST_URI 
    = "https://api.github.com/users/Kostov6/repos";

	private static Client client = ClientBuilder.newClient();
	
	public static void main(String[] args) throws ParseException {
		
		// TODO check
		WebTarget webTarget = client//.register(JsonProcessingFeature.class)
				.target(REST_URI);
		
		JsonArray jsonArray = webTarget
		    .request(MediaType.APPLICATION_JSON_TYPE).get(JsonArray.class);
		printJson(jsonArray);
		
		System.out.println();
		System.out.println("----------------------------------------");
		System.out.println();
		
		
		JSONSchematic schematic = JSONSchematic.addArray(
				JSONSchematic.addObject(
						JSONSchematic.addString("description"),
						JSONSchematic.addNumber("id"),
						JSONSchematic.addString("name")
						)
				);
		
		schematic.print();
		System.out.println();
		
		JsonValue filtered=JSONFilter.filterJson(jsonArray, schematic);
		printJson(filtered);
		
		/*for(int i=0;i<jsonArray.size();i++)
		{
			try{
			System.out.println(jsonArray.getJsonObject(i).getString("description"));}
			catch(RuntimeException e)
			{
				e.printStackTrace();
			}
		}*/
		
	}
	
	public static void printJson(JsonValue json)
	{
		String textJson=json.toString();
		for(int i=0;i<textJson.length();i++)
		{
			System.out.print(textJson.charAt(i));
			if(textJson.charAt(i)==',' || textJson.charAt(i)=='{')
				System.out.println();
		}
		
	}

}
