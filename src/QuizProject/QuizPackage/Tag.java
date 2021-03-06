package QuizProject.QuizPackage;

import java.util.ArrayList;
import java.util.Arrays;

import QuizProject.DBConnection;
import QuizProject.ModelObject;

public class Tag extends ModelObject {

	public Tag(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public static Tag newTag(DBConnection connection, String name, Quiz quiz) {
		Tag tag = new Tag(0, connection);
		tag.createModelObject(name, quiz, null);
		return tag;
	}
	
	protected void createModelObject(String name, Quiz quiz, ArrayList<Object> additionalParameters) {
		ArrayList<Object> parameters = new ArrayList<Object>(Arrays.asList(name, quiz.getID()));
		
		if (additionalParameters != null) {
			parameters.addAll(additionalParameters);
		}
		
		super.createModelObject(parameters);
	}
	
	public Quiz getQuiz() {
		return new Quiz(this.getInt("quiz_id"), this.getConnection());
	}
	
	@Override
	public String getTableName() {
		return "tags";
	}

}
