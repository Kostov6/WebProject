package backend.database;

import java.util.Random;

import cli.CLI;

public class DataBaseCLI extends CLI{
	
	public static void main(String[] args) {
	//	CLI.main(new String[] {"backend.database.DataBaseCLI"});
	}
	
	private DataBaseManager database;
	
	public DataBaseCLI() {
		this.database = new DataBaseManager();
	}

	public void copyTableToDatabase(String tableName,String dataBaseDestination)
	{
		database.copyTableToDatabase(tableName, dataBaseDestination);
	}
	
	public void deleteTable(String tableName)
	{
		database.deleteTable(tableName);
	}
	
	public void useDatabase(String dbName)
	{
		database.useDatabase(dbName);
	}

	//TODO copied
	public String getRandomHexString(int numchars) {
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		while (sb.length() < numchars) {
			sb.append(Integer.toHexString(r.nextInt()));
		}

		return sb.toString().substring(0, numchars);
	}
	
}
