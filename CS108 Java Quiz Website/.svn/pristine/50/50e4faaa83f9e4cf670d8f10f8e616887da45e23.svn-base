package QuizProject.QuestionPackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;

public class MultiTextAnswer extends Answer {
	
	public MultiTextAnswer(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public void createModelObject(int questionId, int mapIndex, ArrayList<String> answerContent) {
		super.createModelObject(questionId, mapIndex, new ArrayList<String>());
		
		for (String possibleAnswer : answerContent) {
			this.getConnection().insert("possible_answers", new ArrayList<Object>(Arrays.asList(this.getID(), possibleAnswer)));
		}
	}
	
	public ArrayList<String> getAnswerContent() {
		ArrayList<String> content = new ArrayList<String>();
		
		ResultSet rs = this.getConnection().executeQuery("SELECT * FROM possible_answers WHERE answer_id = " + this.getID());
		try {
			while (rs.next()) {
				content.add(rs.getString("text"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return content;
	}

}
