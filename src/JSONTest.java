import javax.json.JsonArray;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jsonp.JsonProcessingFeature;


public class JSONTest {

	private static final String REST_URI 
    = "https://api.github.com/users/Kostov6/repos";

	private static Client client = ClientBuilder.newClient();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		WebTarget webTarget = client.register(JsonProcessingFeature.class).target(REST_URI);
		JsonArray jsonArray = webTarget
		    .request(MediaType.APPLICATION_JSON_TYPE).get(JsonArray.class);
		
		for(int i=0;i<jsonArray.size();i++)
		{
			try{
			System.out.println(jsonArray.getJsonObject(i).getString("description"));}
			catch(RuntimeException e)
			{
				e.printStackTrace();
			}
		}
		
	}

}
