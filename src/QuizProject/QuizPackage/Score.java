package QuizProject.QuizPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;
import QuizProject.ModelObject;
import QuizProject.UserPackage.User;

public class Score extends ModelObject implements Comparable<Score> {
	
	//constructor
	
	public Score(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public void createModelObject(int numericalScore, int quizId, int takerId, long dateTaken, long duration) {
		this.createModelObject(numericalScore, quizId, takerId, dateTaken, duration, null);
	}
	
	protected void createModelObject(int numericalScore, int quizId, int takerId, long dateTaken, long duration, ArrayList<Object> additionalParameters) {
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList(numericalScore, quizId, takerId, dateTaken, duration));
		
		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}
		
		super.createModelObject(parameters);
	}
	
	public static ArrayList<Score> getAllScores(DBConnection connection) {
		ArrayList<Score> scores = new ArrayList<Score>();
		ResultSet rs = connection.executeQuery("SELECT * FROM scores ORDER BY date_taken DESC");
		try {
			while (rs.next()) {
				scores.add(new Score(rs.getInt("id"), connection));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return scores;
	}
	
	//getters and setters
		
	public int getNumericalScore(){
		return this.getInt("numerical_score");
	}
	
	public int getQuizId() {
		return this.getInt("quiz_id");
	}
	
	public Quiz getQuiz() {
		return new Quiz(this.getQuizId(), this.getConnection());
	}
	
	public int getTakerId() {
		return this.getInt("quiz_taker_id");
	}
	
	public User getTaker() {
		return new User(this.getTakerId(), this.getConnection());
	}
	
	public long getDateTaken() {
		return this.getLong("date_taken");
	}
	
	public long getDuration(){
		return this.getLong("duration");
	}
	
	public String getTableName(){
		return "scores";
	}

	@Override
	public int compareTo(Score other) {
		if (this.getNumericalScore() != other.getNumericalScore()) {
			return this.getNumericalScore() > other.getNumericalScore() ? -1 : 1;
		}

		if (this.getDuration() != other.getDuration()) {
			return this.getDuration() < other.getDuration() ? -1 : 1; 
		}

		return 0;
	}	
	
	
	
}
