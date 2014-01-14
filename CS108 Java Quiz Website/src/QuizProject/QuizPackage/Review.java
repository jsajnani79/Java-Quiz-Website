package QuizProject.QuizPackage;


import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;
import QuizProject.ModelObject;

public class Review extends ModelObject {

	//constructors
	
	public Review(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public void createModelObject(int quizId, int rating, String text) {
		this.createModelObject(quizId, rating, text, null);
	}
	
	protected void createModelObject(int quizId, int rating, String text, ArrayList<Object> additionalParameters) {
		long date = System.currentTimeMillis();
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList(quizId, rating, text, date));
		
		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}
		
		super.createModelObject(parameters);
	}
	
	//getters
	
	public int getRating(){
		return this.getInt("rating");
	}
	
	public String getText(){
		return this.getString("text");
	}
	
	public int getReviewedQuizID(){
		return this.getInt("quiz_id");
	}
	
	public long getDate() {
		return this.getLong("date");
	}
	
	@Override
	public String getTableName() {
		return "reviews";
	}
	
}
