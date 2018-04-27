package backend;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

/**
 * Servlet implementation class GitAuth
 */
@WebServlet("/GitAuth")
public class GitAuth extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String CLIENT_ID="099b3b26927004bc8273";
	private static final String CLIENT_SECRET="221fb6a5ad8c6aa01f1f7efdb32ae6dd1667f3bc";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GitAuth() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String code=request.getParameter("code");
		System.out.println("Got code" + code);
		
		System.out.println("Reposting ...");
		
		repost(code,CLIENT_ID,CLIENT_SECRET);
		
	}
	
	private void repost(String code,String clientId,String clientSecret) throws IOException
	{
		CloseableHttpClient defClient=HttpClients.createDefault();
		HttpPost post=new HttpPost("https://github.com/login/oauth/access_token");
		
		//setting parameters for post method
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		params.add(new BasicNameValuePair("client_id", clientId));
		params.add(new BasicNameValuePair("client_secret", clientSecret));
		params.add(new BasicNameValuePair("code", code));
		params.add(new BasicNameValuePair("accept ", "json"));
		try {
			post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		HttpResponse response;
		try {
			response = defClient.execute(post);
			HttpEntity entity = response.getEntity();
			
			if (entity != null) {
				   String inputLine ;
				   BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
				   try {
				         while ((inputLine = br.readLine()) != null) {
				                System.out.println(inputLine);
				         }
				         br.close();
				    } catch (IOException e) {
				         e.printStackTrace();
				    }
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		defClient.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
