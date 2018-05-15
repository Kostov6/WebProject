package backend.database;

import java.sql.ResultSet;
import java.util.List;

public interface DataBaseI {

	public void useDatabase(String dbName);
	
	public String getDatabase();
	
	public void insertInto(String tableName,Object data);
	
	public List<Object> getFrom(String tableName,Object match);
	
	public void copyTableToDatabase(String tableName,String dataBaseDestination);
	
	public void deleteTable(String tableName);
	
}
