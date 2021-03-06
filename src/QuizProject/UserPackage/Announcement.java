package QuizProject.UserPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;
import QuizProject.ModelObject;

public class Announcement extends ModelObject {

	//constructors
	
	public Announcement(int id, DBConnection connection){
		super(id,connection);
	}
	
	public void createModelObject(String title, String message, int creatorId, long dateCreated){
		this.createModelObject(title, message, creatorId, dateCreated, null);
	}
	
	public void createModelObject(String title, String message, int creatorId, long dateCreated, ArrayList<Object> additionalParameters){
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList(title, message, creatorId, dateCreated));
		
		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}
		
		super.createModelObject(parameters);
	}
	
	public static ArrayList<Announcement> getAllAnnouncements(DBConnection connection) {
		ArrayList<Announcement> announcements = new ArrayList<Announcement>();
		ResultSet rs = connection.executeQuery("SELECT * FROM announcements ORDER BY date_created DESC");
		try {
			while (rs.next()) {
				announcements.add(new Announcement(rs.getInt("id"), connection));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return announcements;
	}
	
	//getters and setters
	
	public String getTableName(){
		return "announcements";
	}
	
	public String getTitle(){
		return this.getString("title");
	}
	
	public String getMessage(){
		return this.getString("message");
	}
	
	public int getCreatorId(){
		return this.getInt("creator_id");
	}
	
	public long getDateCreated(){
		return this.getLong("date_created");
	}
}
