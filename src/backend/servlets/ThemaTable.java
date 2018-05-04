package backend.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import backend.core.RESTQuery;
import backend.core.json.JSONCollector;
import backend.core.json.JSONFilter;
import backend.core.json.JSONSchematic;
import backend.html.HTMLFileBuilder;
import backend.html.TableBuilder;
import exceptions.BuildException;

/**
 * Servlet implementation class ThemaTable
 */

//TODO Check git's Rate limit https://developer.github.com/v3/
@WebServlet("/ThemaTable")
public class ThemaTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String[] usernames= {/*"Keras",*/"tensorflow","scikit-learn"};
	private static JsonArrayBuilder fullJson=Json.createArrayBuilder();
	
	private JsonArray filtered;
	private ArrayList<ArrayList<String>> tableFormat;
	
	public static void loadJSONDataFromGit(String[] newUsers)
	{
		for(String newUser : newUsers)
		{
			RESTQuery getFullUserRepos = new RESTQuery("https://api.github.com/users/"+newUser+"/repos");
			JsonArray reposResult=getFullUserRepos.callGETJson();
				
			//build into a JsonObject
			JsonObjectBuilder builder=Json.createObjectBuilder();
				builder.add("name", newUser);
				builder.add("repos", reposResult);
			JsonObject build=builder.build();
				
			fullJson.add(build);
		}
	}
	
    /**
     * @throws ParseException 
     * @see HttpServlet#HttpServlet()
     */
    public ThemaTable() throws ParseException {
        
    	JsonArray build=fullJson.build();
    	System.out.println("Filtering...");
    	JSONSchematic schematic=JSONSchematic.addArray(
    			JSONSchematic.addObject(
    				JSONSchematic.addString("name"),
	    			JSONSchematic.addArray("repos",
	    	    			JSONSchematic.addObject(
	    	    					JSONSchematic.addString("name")
	    	    					)
	    					)
	    			)
    			);
    	filtered=(JsonArray) JSONFilter.filterJson(build, schematic);
    	System.out.println("complete");
        
    	collectData();
      /*  JsonArrayBuilder builder=Json.createArrayBuilder();
        
        System.out.println("Test for getting user repo speed...");
        for(String user : usernames)
        	builder.add(getUserReposAsJson(user));
    
        System.out.println("Building and printing:");
        fullJson=builder.build();
        */
        //JsonArray partialFilter=(JsonArray) filterJson();
        
        //System.out.println(partialFilter);
        
      /*  tableFormat=new ArrayList<ArrayList<String>>();
        
        ArrayList<String> tableRow=new ArrayList<String>();
        
        for(JsonValue el1 : filtered)
        {
        	JsonObject obj1=(JsonObject) el1;
        	
        	tableRow.add(obj1.getString("name"));
        	
        	JsonArray ar2=obj1.getJsonArray("repos");
        	
        	for(JsonValue el2 : ar2)
        	{
        		JsonObject ob1=(JsonObject) el2;
        		
        		tableRow.add(ob1.getString("name"));
        		tableFormat.add((ArrayList<String>)(tableRow.clone()));
        		tableRow.remove(1);
        	}
        	tableRow.remove(0);
        }
        for(ArrayList<String> s : tableFormat)
        {
        	System.out.println(s);
        }
        */
        
    }
    
    public ArrayList<JsonObject> collectData()
    {
    /*	ArrayList<JsonObject> res;
    	res=JSONCollector.collectDataFrom(filtered);
    	System.out.println(res);*/
    	tableFormat=new ArrayList<ArrayList<String>>();
        
        ArrayList<String> tableRow=new ArrayList<String>();
        
        for(JsonValue el1 : filtered)
        {
        	JsonObject obj1=(JsonObject) el1;
        	
        	tableRow.add(obj1.getString("name"));
        	
        	JsonArray ar2=obj1.getJsonArray("repos");
        	
        	for(JsonValue el2 : ar2)
        	{
        		JsonObject ob1=(JsonObject) el2;
        		
        		tableRow.add(ob1.getString("name"));
        		//here 
        		String[] topics=getUserReposTopics(tableRow.get(0),tableRow.get(1));
        		for(String topic : topics)
        		{
        			ArrayList<String> newRow=(ArrayList<String>)tableRow.clone();
        			newRow.add(topic);
        		tableFormat.add(newRow);
        		}
        		tableRow.remove(1);
        	}
        	tableRow.remove(0);
        }
        for(ArrayList<String> s : tableFormat)
        {
        	System.out.println(s);
        }
    	return null;
    }
    private String[] getUserReposTopics(String user,String repo)
    {
    	RESTQuery topicsQuery=new RESTQuery("https://api.github.com/repos",user,repo,"topics");
    	topicsQuery.addHeader("Accept","application/vnd.github.mercy-preview+json");
    	JsonObject topics=topicsQuery.callGETJsonObject();
    	System.out.println(topics);
    	JsonArray topicsArray = topics.getJsonArray("name");
    	if(topicsArray==null || topicsArray.size()==0)
    	{
    		return new String[]{""};
    	}
    	String[] out=new String[topicsArray.size()];
    	int i=0;
    	for(JsonValue value : topicsArray)
    	{
    		//value is expected to be string
    		//check whether it works
    		out[i++]=value.toString();
    	}
    	return out;
    	
    }
   /*
    private JsonValue filterJson() throws ParseException
    {

    	
    	return out;
    }
    
    private JsonValue getUserReposAsJson(String user)
    {

    	String url="https://api.github.com/users/"+user+"/repos";
    	JsonArray jsonArray=new RESTQuery(url).callGETJson();
		
    	System.out.println("Now building");
		JsonObjectBuilder builder=Json.createObjectBuilder();
		builder.add("name", user);
		builder.add("repos", jsonArray);
		JsonValue out=builder.build();
		
		System.out.println("Building comlete");
		return out;
    }
    */
   
    public static void main(String[] args) throws ParseException, BuildException {
    	
    	loadJSONDataFromGit(usernames);
    	
		ThemaTable themas=new ThemaTable();
		themas.collectData();
		
		HTMLFileBuilder htmlFile=new HTMLFileBuilder();
		
		htmlFile.setStyleFromFile("style.css");
		
		TableBuilder table=new TableBuilder();
		String[] headers= {"Username","Repo","Thema"};
		table.setHeaders(headers);
		
		for(ArrayList<String> row : themas.tableFormat)
			table.addDataRow(row.toArray(new String[0]));
		
		table.build();
		
		htmlFile.addLinesInBody(table.getHTMLLines());
	
		htmlFile.build();
		
		for(String line : htmlFile.getHTML())
			System.out.println(line);
	}

    
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
