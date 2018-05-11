package backend;

import javax.json.JsonObject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;

import backend.core.RESTQuery;

public class LoginManager {
	
	public static void gitAuthLoginEvent(JsonObject loginData,HttpServletResponse response)
	{
		//checks login data 
		
		String accessToken=loginData.getString("access_token");
		
		BasicUser user=authenticateUserFromAccessToken(accessToken);
		String hash=getUserHashFromDB(user);
		System.out.println(hash);
		writeUserHashOnSessionCookie(response,hash);
	}
	
	
	
	private static BasicUser authenticateUserFromAccessToken(String accessToken)
	{
		RESTQuery user=new RESTQuery("https://api.github.com/user?access_token="+accessToken);
		user.addHeader("Accepts", "application/vnd.github.jean-grey-preview+json");
		
		JsonObject userJson=user.callGETJsonObject();
		
		System.out.println(userJson);
		
		String username=userJson.getString("login");
		
		return new BasicUser(username);
		
	}
	
	private static String getUserHashFromDB(BasicUser user)
	{
		
		return DigestUtils.sha256Hex(user.username);
	}
	
	private static void writeUserHashOnSessionCookie(HttpServletResponse response,String hash)
	{
		Cookie cookie=new Cookie("user_session",hash);
		response.addCookie(cookie);
	}
	
	
	private void gitAuthLoginFailed()
	{
		
	}
}
