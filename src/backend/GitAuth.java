package backend;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

import backend.core.RESTQuery;
import backend.core.managers.LoginManager;

/**
 * Servlet implementation class GitAuth
 */
@WebServlet("/GitAuth")
public class GitAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String CLIENT_ID="099b3b26927004bc8273";
	private static final String CLIENT_SECRET="221fb6a5ad8c6aa01f1f7efdb32ae6dd1667f3bc";
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code=request.getParameter("code");
		System.out.println("Got code " + code);
		
		
		
		System.out.println("Reposting ...");
		
		try {
			JsonObject object=repostAndGetToken(code,CLIENT_ID,CLIENT_SECRET);

			//postToJSecurity(
					GitUser user=LoginManager.gitAuthLoginEvent(object, response);
			response.sendRedirect("http://localhost:8080/WebProject/user/Login?"
					+ "j_username="+user.user+"&j_password="+user.password+"");			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		
		

	}
	
	/*private void postToJSecurity(GitUser user) throws URISyntaxException, ClientProtocolException, IOException
	{
		CloseableHttpClient defClient=HttpClients.createDefault();
		HttpPost post=new HttpPost("http://localhost:8080/WebProject/Login");
	
		
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		System.out.println("j_username = "+user.user+" password = "+user.password);
		params.add(new BasicNameValuePair("j_username", user.user));
		params.add(new BasicNameValuePair("j_password", user.password));

		URI uri = new URIBuilder(post.getURI()).addParameters(params).build();
        post.setURI(uri);
		defClient.execute(post);

		defClient.close();
	}*/
	
	
	private String repostAndGetTokenJSON(String code,String clientId,String clientSecret) throws IOException{
		
		return null;
	}
	
	private JsonObject repostAndGetToken(String code,String clientId,String clientSecret) throws IOException, URISyntaxException
	{
		CloseableHttpClient defClient=HttpClients.createDefault();
		HttpGet post=new HttpGet("https://github.com/login/oauth/access_token");
	
		
		//setting parameters for post method
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("client_id", clientId));
		params.add(new BasicNameValuePair("client_secret", clientSecret));
		params.add(new BasicNameValuePair("code", code));
		//params.add(new BasicNameValuePair("state", "hfksj4j3dfvscro"));
		//params.add(new BasicNameValuePair("redirect_uri ", ""));

		post.setHeader("Accept", "application/json");
		URI uri = new URIBuilder(post.getURI()).addParameters(params).build();
        post.setURI(uri);
		
		System.out.println(post);
		HttpResponse response=null;

		response = defClient.execute(post);

		HttpEntity entity = response.getEntity();
		
		String inputLine = null ;
		
		if (entity != null) {
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
			//not safe code block
			inputLine=br.readLine();
			//
	        br.close();

		}
		
		JsonReader reader= Json.createReader(new StringReader(inputLine));
		JsonObject object=reader.readObject();
		
		System.out.println(inputLine);
		
		defClient.close();
		
		return object;
	}

}
