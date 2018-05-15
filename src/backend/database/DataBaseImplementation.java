package backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import backend.BasicUser;
import backend.GitUser;

public class DataBaseImplementation implements DataBaseI {

	private String database;
	
	
	private static final String DBPass="TooMuchSwag69";
	
	@Override
	public void useDatabase(String dbName) {
		database=dbName;

	}

	@Override
	public String getDatabase() {
		return database;
	}

	@Override
	public void insertInto(String tableName, Object data) {
		if(database==null)
			throw new RuntimeException("Database not set");
		
		if(data instanceof BasicUser)
			insertInto(tableName,(BasicUser)data);
		
		if(data instanceof GitUser)
			insertInto(tableName,(GitUser)data);
	}
	
	private void insertInto(String tableName,BasicUser user)
	{
		String mysqlQuery="INSERT INTO "+database+"."+tableName+" (user,type,password) VALUES ('"
				+user.username+"','site','"+user.password+"');";
		try {
			executeUpdate(mysqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		mysqlQuery ="insert into user_roles (user,role_name)" + 
				"values ('"+user.username+"','user');";
		try {
			executeUpdate(mysqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void insertInto(String tableName,GitUser user)
	{
		
		String mysqlQuery="INSERT INTO "+database+"."+tableName+" (user,type,password,git_user,git_pic,last_access_token,git_hash) VALUES ('"
				+user.user+"','git','"+user.password+"','"+user.gitUser+"','"+user.gitPic+"','"+user.lastAT+"','"+user.hash+"');";
		System.out.println(mysqlQuery);
		
		try {
			executeUpdate(mysqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		mysqlQuery ="insert into user_roles (user,role_name)" + 
				"values ('"+user.user+"','user');";
		try {
			executeUpdate(mysqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public List<Object> getFrom(String tableName, Object match) {
		if(database==null)
			throw new RuntimeException("Database not set");
		if(match instanceof BasicUser)
			return getFrom(tableName,(BasicUser)match);
		
		if(match instanceof GitUser)
			return getFrom(tableName,(GitUser)match);
		
		return null;
	}
	/*
	public static void main(String[] args) {
		DataBaseImplementation manager=new DataBaseImplementation();
		manager.useDatabase("project");
		ResultSet sres=manager.getFrom("userS", new BasicUser("sercho",null));
	}*/
	
	private  List<Object> getFrom(String tableName, BasicUser match) {
		//ignores password
		String mysqlQuery="SELECT * FROM "+database+"."+tableName+" WHERE user='"
				+match.username+"';";
		ResultSet res=null;
		try {
			res=executeQuery(mysqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Object> out=new ArrayList<Object>();
		try {
			while(res.next())
			{
				out.add(new BasicUser(res.getString(1),null));
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}
	
	private  List<Object> getFrom(String tableName, GitUser match) {
		//ignores password
		String mysqlQuery="SELECT * FROM "+database+"."+tableName+" WHERE ";
		
		if(match.user!=null)
			mysqlQuery+="user='"+match.user+"';";
		else if(match.gitUser!=null)
		{
			if(match.user!=null)
				mysqlQuery+=" AND ";
			
			mysqlQuery+= "git_user='"+match.gitUser+"';";
			
			
		}
		else
			return null;
		
		ResultSet res=null;
		try {
			res=executeQuery(mysqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Object> out=new ArrayList<Object>();
		try {
			while(res.next())
			{
				out.add(new GitUser(res.getString(1),res.getString(4),res.getString(3),res.getString(5),res.getString(6),res.getString(7)));
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}

	@Override
	public void deleteTable(String tableName) {
		if(database==null)
			throw new RuntimeException("Database not set");
		
		String mysqlQuery="DROP TABLE "+database+"."+tableName;
		try {
			executeUpdate(mysqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void copyTableToDatabase(String tableName, String dataBaseDestination) {
		if(database==null)
			throw new RuntimeException("Database not set");
		
		String mysqlQuery="CREATE TABLE "+dataBaseDestination+"." + tableName+" SELECT * from "+database+"."+tableName;
		try {
			executeUpdate(mysqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private ResultSet executeQuery(String query) throws SQLException
	{
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost/"+database, "root", DBPass);

		Statement statement = connection.createStatement();
		
		ResultSet result = statement.executeQuery(query);
		//TODO
		//connection.close();
		return result;
	}
	
	private int executeUpdate(String query) throws SQLException
	{
		Connection connection = DriverManager.getConnection(
				"jdbc:mysql://localhost/"+database, "root", DBPass);

		Statement statement = connection.createStatement();
		
		int result = statement.executeUpdate(query);
	
		connection.close();
		return result;
	}

}
