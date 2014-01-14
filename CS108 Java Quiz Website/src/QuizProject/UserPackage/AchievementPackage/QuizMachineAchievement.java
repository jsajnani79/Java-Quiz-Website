package QuizProject.UserPackage.AchievementPackage;

import QuizProject.UserPackage.User;

public class QuizMachineAchievement extends Achievement {
	
	public static final int THRESHOLD = 10;
	
	public  String name(){
		return "Quiz Machine";
	}
	
	public  String iconURL(){
		return "AchievementIcons/yellow.png";
	}
	
	public String toolTip() {
		return "Took 10 quizzes";
	}
	
	public static boolean isEligible(User user){
		if (user.hasQuizMachineAchievement()) return false;
		
		if (user.getNumQuizzesTaken() >= THRESHOLD) return true;
		return false;
	}
}
