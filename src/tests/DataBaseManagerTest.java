package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import backend.database.DataBaseManager;

class DataBaseManagerTest {



	@Test
	void testException() {
		DataBaseManager db=new DataBaseManager();
		db.deleteTable("ta");
	}

}
