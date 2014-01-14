package QuizProject.QuestionPackage;

import QuizProject.DBConnection;

public class MatchingAnswer extends Answer {

	public MatchingAnswer(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public String getAnswerContent(){
		return this.getString("answer_content");
	}
	
	public int getMapIndex(){
		return this.getInt("map_index");
	}
	
	public int getScore(String answer){
		if (answer.equals(this.getAnswerContent())) return 1;
		return 0;
	}

}
