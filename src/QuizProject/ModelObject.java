package QuizProject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ModelObject {
	
	private int id;
	private DBConnection connection;
	
	// pass in zero as id when don't have an id (when object has not yet been inserted into DB)
	public ModelObject(int id, DBConnection connection){
		this.id = id;
		this.connection = connection;
	}
	
	protected void createModelObject(ArrayList<Object> additionalParameters) {
		this.connection.insert(this.getTableName(), additionalParameters);
		ResultSet rs = this.getConnection().executeQuery("SELECT * FROM " + this.getTableName() + " ORDER BY id DESC LIMIT 1;");
		try {
			if (rs.next()) {
				this.setID(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected String getTableName(){
		return null;
	}
	
	public int getID(){
		return this.id;
	}
	
	protected void setID(int id) {
		this.id = id;
	}
	
	public DBConnection getConnection(){
		return this.connection;
	}
	
	/**
	 * A convenience method that combines two arrays.
	 * @param first
	 * @param second
	 * @return an array built by appending the contents of the second array to that of the first
	 */
	protected ArrayList<Object> combinedList(ArrayList<Object> first, ArrayList<Object> second) {
		first.addAll(second);
		return first;
	}
	
	// Getters and setters for properties in the DB
	
	public String getString(String columnName) {
		return this.getConnection().getString(this.getID(), this.getTableName(), columnName);
	}
	
	public int getInt(String columnName) {
		return this.getConnection().getInt(this.getID(), this.getTableName(), columnName);
	}
	
	public long getLong(String columnName) {
		return this.getConnection().getLong(this.getID(), this.getTableName(), columnName);
	}
	
	public boolean getBoolean(String columnName) {
		return this.getConnection().getBoolean(this.getID(), this.getTableName(), columnName);
	}
	
	public ArrayList<String> getCSV(String columnName) {
		return this.getConnection().getCSV(this.getID(), this.getTableName(), columnName);
	}
	
	public ArrayList<Integer> getIntCSV(String columnName) {
		return this.getConnection().getIntCSV(this.getID(), this.getTableName(), columnName);
	}
	
	public void setValue(String columnName, Object value) {
		this.getConnection().setValue(this.getID(), this.getTableName(), columnName, value);
	}
	
	public void delete() {
		this.getConnection().delete(this.getID(), this.getTableName());
	}
	
	public boolean exists() {
		ResultSet rs = this.getConnection().executeQuery("SELECT * FROM " + this.getTableName() + " WHERE id = " + this.getID());
		try {
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public boolean equals(Object other) {
		// standard two checks for equals()
		if (this == other) return true;
		if (!(other instanceof ModelObject)) return false;
			// check if other point same as us
		ModelObject otherModelObject = (ModelObject)other;
		return this.getID() == otherModelObject.getID();
	}
	
}
