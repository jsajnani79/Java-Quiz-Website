package QuizProject.UserPackage.AchievementPackage;

import QuizProject.UserPackage.User;

public class ProlificAuthorAchievement extends Achievement {

	public static final int THRESHOLD = 5;
	
	public  String name(){
		return "Prolific Author";
	}
	
	public  String iconURL(){
		return "AchievementIcons/red.png";
	}
	
	public String toolTip() {
		return "Created 5 quizzes";
	}
	
	public static boolean isEligible(User user){
		if (user.hasProlificAuthorAchievement()) return false;
		
		if (user.getNumQuizzesCreated() >= THRESHOLD) return true;
		return false;
	}
}
