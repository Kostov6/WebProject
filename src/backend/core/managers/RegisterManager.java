package backend.core.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import backend.BasicUser;
import backend.database.DataBaseManager;

public class RegisterManager {

	public static void registerInDatabase(String email, String password)
	{
		DataBaseManager manager=new DataBaseManager();
		manager.useDatabase("project");
		manager.insertInto("users", new BasicUser(email,password));
		
	}
	
	public static boolean checkUsernameAlreadyTaken(String username) throws SQLException
	{
		DataBaseManager manager=new DataBaseManager();
		manager.useDatabase("project");
		List<Object> res=manager.getFrom("users", new BasicUser(username,null));
		return !res.isEmpty();
	}
	
	/*
	public static void main(String[] args) throws SQLException {
		boolean res=checkUsernameAlreadyTaken("tempUsers");
		System.out.println(res);
	}*/
}
