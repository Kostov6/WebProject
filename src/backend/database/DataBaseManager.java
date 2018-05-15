package backend.database;

import java.util.List;

public class DataBaseManager extends DataBaseImplementation implements DataBaseI{

	public DataBaseManager(){
		super();
	    try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	

}
