package QuizProject.UserPackage.AchievementPackage;

import QuizProject.UserPackage.User;

public class PracticeMakesPerfectAchievement extends Achievement {

	public  String name(){
		return "Practice Makes Perfect";
	}
	
	public  String iconURL(){
		return "AchievementIcons/turquoise.png";
	}
	
	public String toolTip() {
		return "Took a quiz in practice mode";
	}
	
	public static boolean isEligible(User user){
		if (user.hasPracticeMakesPerfectAchievement()) return false;
		
		if (user.hasDonePracticeMode()) return true;
		return false;
	}
	
}
