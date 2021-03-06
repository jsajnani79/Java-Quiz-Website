package QuizProject.QuestionPackage;

import java.util.ArrayList;

import QuizProject.DBConnection;

public class MultiMultipleChoiceAnswer extends MultiTextAnswer {

	public MultiMultipleChoiceAnswer(int id, DBConnection connection) {
		super(id, connection);
	}
	
	/*public ArrayList<String> getAnswerContent(){
		return this.getCSV("answer_content");
	}*/

	public int getScore(ArrayList<Integer> answer){
		Boolean answerCorrect = true;		
		
		for (int i = 0; i < answer.size(); i++){
			if (!this.getAnswerContent().get(i).equals(Integer.toString(answer.get(i)))){
				answerCorrect = false;
			}
		}
		
		if (answerCorrect) return 1;
		return 0;
	}
	
}
