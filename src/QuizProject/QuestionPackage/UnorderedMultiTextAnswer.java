package QuizProject.QuestionPackage;

import QuizProject.DBConnection;

public class UnorderedMultiTextAnswer extends MultiTextAnswer {

	public UnorderedMultiTextAnswer(int id, DBConnection connection) {
		super(id, connection);
	}
	
	/*public ArrayList<String> getAnswerContent(){
		return this.getCSV("answer_content");
	}*/
	
	public int getMapIndex(){
		return this.getInt("map_index");
	}
	
	public int getScore(String answer){
		if (this.getAnswerContent().contains(answer)) return 1;
		return 0;
	}
}
