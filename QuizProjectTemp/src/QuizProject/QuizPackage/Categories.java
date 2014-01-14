package QuizProject.QuizPackage;

import java.util.ArrayList;

public class Categories {

	public static ArrayList<String> getCategoryNames() {
		ArrayList<String> names = new ArrayList<String>();
		
		names.add("History");
		names.add("Geography");
		names.add("News");
		names.add("Math");
		names.add("Science");
		names.add("Music");
		names.add("Pop Culture");
		names.add("Other");
		
		return names;
	}
	
}
