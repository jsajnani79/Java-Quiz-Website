package QuizProject.UserPackage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import QuizProject.DBConnection;


public class UserTest2 {

	User saam;
	DBConnection connection;
	
	@Before
	public void setUp() throws Exception{
		connection = new DBConnection();
		saam = new User(0,connection);
		saam.createModelObject("saam", "stanford");
	}
	
	@Test
	public void test1(){
		assertEquals(true,User.checkPassword("saam", "stanford", connection));
	}
	
}
