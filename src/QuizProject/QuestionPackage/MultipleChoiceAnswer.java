package QuizProject.QuestionPackage;

import QuizProject.DBConnection;

public class MultipleChoiceAnswer extends Answer {

	public MultipleChoiceAnswer(int id, DBConnection connection) {
		super(id, connection);
	}
	
	public String getAnswerContent(){
		return this.getString("answer_content");
	}
	
	public int getScore(int answer){
		if (Integer.parseInt(this.getAnswerContent()) == answer) return 1;
		return 0;
	}	
}
