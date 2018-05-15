package backend.core.managers;

import java.util.Random;

import javax.json.JsonObject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;

import backend.BasicUser;
import backend.GitUser;
import backend.core.RESTQuery;
import backend.database.DataBaseManager;

public class LoginManager {
	
	public static GitUser gitAuthLoginEvent(JsonObject loginData,HttpServletResponse response)
	{
		//checks login data 
		
		String accessToken=loginData.getString("access_token");
		
		GitUser user=authenticateUserFromAccessToken(accessToken);
		//String hash=getUserHashFromDB(user);
		System.out.println(user.hash);
		//TODO
		if(existsInDB(user))
		{

			DataBaseManager manager=new DataBaseManager();
			manager.useDatabase("project");
			user=(GitUser) manager.getFrom("users", user).get(0);
			updateAccessToken(user);
		}
		else
		{
			addInDb(user);
		}
		//TODO
		writeUserHashOnSessionCookie(response,user.hash);
		
		System.out.println(user);
		return user;
	}
	
	private static void updateAccessToken(GitUser user)
	{
		
		System.out.println("Updating access token");
	}
	
	private static void addInDb(GitUser user)
	{
		DataBaseManager manager=new DataBaseManager();
		manager.useDatabase("project");
		//find GitUserxxxx
		Random r=new Random();
		String code;
		do {
			code=""+r.nextInt(9999);
			user.user="GitUser"+code;
		}while (!manager.getFrom("users",user).isEmpty());
		
		user.password=getRandomHexString(30);
		
		manager.insertInto("users", user);
		
		
	}
	
	 private static String getRandomHexString(int numchars){
	        Random r = new Random();
	        StringBuffer sb = new StringBuffer();
	        while(sb.length() < numchars){
	            sb.append(Integer.toHexString(r.nextInt()));
	        }

	        return sb.toString().substring(0, numchars);
	    }
	
	private static boolean existsInDB(GitUser user)
	{
		DataBaseManager manager=new DataBaseManager();
		manager.useDatabase("project");
		return !manager.getFrom("users", user).isEmpty();
	}
	
	private static GitUser authenticateUserFromAccessToken(String accessToken)
	{
		RESTQuery user=new RESTQuery("https://api.github.com/user?access_token="+accessToken);
		user.addHeader("Accepts", "application/vnd.github.jean-grey-preview+json");
		
		JsonObject userJson=user.callGETJsonObject();
		
		System.out.println(userJson);
		
		String username=userJson.getString("login");
		String picUrl=userJson.getString("avatar_url");
		return new GitUser(null,username,null,picUrl,accessToken,getUserHashFromDB(username));
		
	}
	
	private static String getUserHashFromDB(String user)
	{
		
		return DigestUtils.sha256Hex(user);
	}
	
	private static void writeUserHashOnSessionCookie(HttpServletResponse response,String hash)
	{
		Cookie cookie=new Cookie("git_hash",hash);
		response.addCookie(cookie);
	}
	
	
	private void gitAuthLoginFailed()
	{
		
	}
}
