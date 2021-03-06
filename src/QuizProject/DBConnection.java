package QuizProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class DBConnection {

	static final String MYSQL_USERNAME = MyDBInfo.MYSQL_USERNAME; 
	static final String MYSQL_PASSWORD = MyDBInfo.MYSQL_PASSWORD; 
	static final String MYSQL_DATABASE_SERVER = MyDBInfo.MYSQL_DATABASE_SERVER; 
	static final String MYSQL_DATABASE_NAME = MyDBInfo.MYSQL_DATABASE_NAME;
	
	public static final String DB_CONNECTION_ATTRIBUTE_NAME = "DBConnection"; 
	 
	private Connection connection;
	 
	/**
	 * The constructor class initializes the connection to the SQL database
	 * Here, the colNames ArrayList is initialized with the fixed values for the
	 * column titles in the table.
	 */
	public DBConnection() {
		this.createConnection();
	}
	
	private void createConnection() {
		try { 
			Class.forName("com.mysql.jdbc.Driver"); 
		 	this.connection = DriverManager.getConnection ( "jdbc:mysql://" + MYSQL_DATABASE_SERVER, MYSQL_USERNAME, MYSQL_PASSWORD); 
		}
		catch (SQLException e) { 
			e.printStackTrace(); 
		}
		catch (ClassNotFoundException e) { 
			e.printStackTrace(); 
		} 
	}
	
	public ArrayList<Integer> getIds(String tableName, String searchColumnName, Object searchValue, String resultsColumnName) {
		ResultSet rs = this.executeQuery("SELECT * FROM " + tableName + " WHERE " + searchColumnName + " = " + this.objectToDBString(searchValue) + ";");
		ArrayList<Integer> ids = new ArrayList<Integer>();
		try {
			while (rs.next()) {
				ids.add(rs.getInt(resultsColumnName));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ids;
	}
	
	public String getString(int id, String tableName, String columnName) {
		ResultSet rs = this.executeQuery("SELECT * FROM " + tableName + " WHERE id = " + id + ";");
		try {
			if (rs.next()) {
				return rs.getString(columnName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getInt(int id, String tableName, String columnName) {
		ResultSet rs = this.executeQuery("SELECT * FROM " + tableName + " WHERE id = " + id + ";");
		try {
			if (rs.next()) {
				return rs.getInt(columnName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // not a great error value, but have no other choice
	}
	
	public long getLong(int id, String tableName, String columnName) {
		ResultSet rs = this.executeQuery("SELECT * FROM " + tableName + " WHERE id = " + id + ";");
		try {
			if (rs.next()) {
				return rs.getLong(columnName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; // not a great error value, but have no other choice
	}
	
	public boolean getBoolean(int id, String tableName, String columnName) {
		ResultSet rs = this.executeQuery("SELECT * FROM " + tableName + " WHERE id = " + id + ";");
		try {
			if (rs.next()) {
				return (rs.getInt(columnName) != 0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; // not a great error value, but have no other choice
	}
	
	public ArrayList<String> getCSV(int id, String tableName, String columnName) {
		ResultSet rs = this.executeQuery("SELECT * FROM " + tableName + " WHERE id = " + id + ";");
		try {
			if (rs.next()) {
				String csv = rs.getString(columnName);
				return new ArrayList<String>(Arrays.asList(csv.split(",")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Integer> getIntCSV(int id, String tableName, String columnName) {
		ArrayList<String> stringCSV = this.getCSV(id, tableName, columnName);
		ArrayList<Integer> intCSV = new ArrayList<Integer>();
		for (String string : stringCSV) {
			if(!string.equals("")){
				intCSV.add(Integer.parseInt(string));				
			}
		}
		return intCSV;
	}
	
	public void setValue(int id, String tableName, String columnName, Object value) {
		this.executeUpdate("UPDATE "  + tableName + " SET " + columnName + " = " + this.objectToDBString(value) + " WHERE id = " + id + ";");
	}
	
	public void insert(String tableName, ArrayList<Object> values) {
		String parameters = "0";
		for (Object obj : values) {
			String value = this.objectToDBString(obj);
			if (value != null) {
				parameters += "," + value;
			} else {
				parameters += ",NULL"; /// kosher?
			}
		}
		
		// remove leading comma if necessary
		if (parameters.length() > 0 && parameters.charAt(0) == ',') {
			parameters = parameters.substring(1);
		}
		
		this.executeUpdate("INSERT INTO " + tableName + " VALUES(" + parameters + ");");
	}
	
	public void delete(int id, String tableName) {
		this.executeUpdate("DELETE FROM " + tableName + " WHERE id = " + id);
	}
	
	public ResultSet executeQuery(String query){
		try{
			if (this.connection.isClosed()) {
				this.createConnection(); 
			}
			
			Statement stmt = this.connection.createStatement(); 
			stmt.executeQuery("USE " + MYSQL_DATABASE_NAME); 
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		}	 
		catch (SQLException e) { 
			e.printStackTrace(); 
		}
		return null;  
	}
	
	public void executeUpdate(String update) {		
		try{
			if (this.connection.isClosed()) {
				this.createConnection(); 
			}
			
			Statement stmt = this.connection.createStatement(); 
			stmt.executeQuery("USE " + MYSQL_DATABASE_NAME); 
			stmt.executeUpdate(update);
		}	 
		catch (SQLException e) { 
			e.printStackTrace(); 
		}
	}
	
	private String objectToDBString(Object obj) {
		String string = null;
		
		if (obj != null) {
			if (obj instanceof String) {
				string = this.stringToDBString((String) obj);
			} else if (obj instanceof Integer) {
				string = Integer.toString((Integer) obj);
			} else if (obj instanceof Long) {
				string = Long.toString((Long) obj);
			} else if (obj instanceof Boolean) {
				string = this.booleanToDBString((Boolean) obj);
			} else if (obj instanceof ArrayList<?>) {
				string = this.csvToDBString((ArrayList<?>) obj);
			}
		}
		
		return string;
	}
	
	private String stringToDBString(String str) {
		if (str == null) str = "";
		return "\"" + str + "\"";
	}
	
	private String booleanToDBString(boolean bool) {
		return bool ? "1" : "0";
	}
	
	public String csvToDBString(ArrayList<?> csv) {
		if (csv == null) return "";
		
		String stringValue = "";
		for (Object obj : csv) {
			if (obj instanceof String) {
				stringValue += "," + (String) obj;
			} else if (obj instanceof Integer) {
				stringValue += "," + (Integer) obj;
			}
		}
		
		if (stringValue.length() >= 2 && stringValue.charAt(0) == ',') {
			stringValue = stringValue.substring(1);
		}
		
		return "\"" + stringValue + "\"";
	}
	
	public void retire() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
