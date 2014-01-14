package QuizProject.UserPackage.AchievementPackage;

import QuizProject.UserPackage.User;

public class ProdigiousAuthorAchievement extends Achievement {
	
	public static final int THRESHOLD = 10;
	
	public  String name(){
		return "Prodigious Author";
	}
	
	public  String iconURL(){
		return "AchievementIcons/purple.png";
	}
	
	public String toolTip() {
		return "Created 10 quizzes";
	}
	
	public static boolean isEligible(User user){
		if (user.hasProdigiousAuthorAchievement()) return false;
		
		if (user.getNumQuizzesCreated() >= THRESHOLD) return true;
		return false;
	}
	
}
