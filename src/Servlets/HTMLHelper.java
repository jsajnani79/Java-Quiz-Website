package Servlets;

import QuizProject.QuizPackage.Quiz;
import QuizProject.UserPackage.User;

public class HTMLHelper {

	public static String linkforUser(User user) {
		if (user.exists()) {
			return "<a href=\"user-profile.jsp?id=" + user.getID() + "\">" + user.getUsername() + "</a>";
		}
		return "<a href=\"bad-resource.jsp\"> User Was Deleted</a>";
	}
	
	public static String hrefForQuiz(Quiz quiz) {
		if (quiz.exists()) {
			if (quiz.isMultiPage()) {
				int questionIndex = 0;
				String ref = "display-multipage-quiz.jsp?id=" + quiz.getID()+"&questionIndex="+questionIndex;
				return ref;
			} else {
				return "display-quiz.jsp?id=" + quiz.getID();
			}
		}
		return "";
	}
	
	public static String linkforQuizSummary(Quiz quiz) {
		if (quiz.exists()) {
			return "<a href=\"quiz-summary.jsp?id=" + quiz.getID() + "\">" + quiz.getName() + "</a>";
		}
		return "<a href=\"bad-resource.jsp\"> Quiz Was Deleted</a>";
	}
	
	public static String hrefForQuizSummary(Quiz quiz) {
		if (quiz.exists()) {
			return "quiz-summary.jsp?id=" + quiz.getID();
		}
		return "bad-resource.jsp";
	}
	
	public static String printErrorMessage(String errorMessage, String returnLink){
		String partialNavBar = HTMLHelper.generatePartialNavBar();
		return "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"+
		"<html>"+
		"<head>"+
		"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">"+
		"<title>Bad Input</title>"+
		"<link rel=\"stylesheet\" href=\"bootstrap/css/bootstrap.css\"/>"+
		    "<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->"+
		    "<script src=\"https://code.jquery.com/jquery.js\"></script>"+
		    "<!-- Include all compiled plugins (below), or include individual files as needed -->"+
		    "<script src=\"bootstrap/js/bootstrap.js\"></script>"+
		"<style>"+
		"body {"+
			"padding-top: 0px;"+
			"overflow: auto;"+
			"font-family: sans-serif;"+
		"}"+
		
		"#main { "+
		" margin-left:auto;"+
		 "margin-right:auto;"+
		 "width: 600px;	"+
		 "font-family: sans-serif; "+
		"} "+
		
		"</style>"+
		"</head>"+
		"<body>"+
		
		"<!-- Print Partial Navigation Bar --> "+
		partialNavBar +
		
		"<div id = \"main\">"+
		"<h3> Bad Input</h3>"+
		errorMessage+
		"<br>"+
		"<a class=\"btn btn-info \" role=\"button\" href=\""+returnLink+"\">Return</a>"+ 
		"</div> <!-- end of main div -->"+
		"</body>"+
		"</html>";
	}
	
	public static String generateFullNavBar(User user){
		String admin = "";
		if(user.isAdmin()) admin = "<li><a href=\"admin-page.jsp\">Administration</a></li>";
		String navBar = "<nav class=\"navbar navbar-default\" role=\"navigation\"> " +
		  "<!-- Brand and toggle get grouped for better mobile display -->"+
		  "<div class=\"navbar-header\">"+
		    "<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#bs-example-navbar-collapse-1\">"+
		      "<span class=\"sr-only\">Toggle navigation</span>"+
		      "<span class=\"icon-bar\"></span>"+
		      "<span class=\"icon-bar\"></span>"+
		      "<span class=\"icon-bar\"></span>"+
		    "</button>"+
		    "<a class=\"navbar-brand\" href=\"user-home.jsp\">Quizlet++</a>"+
		  "</div>"+
		  		  
		  "<!-- Collect the nav links, forms, and other content for toggling -->"+
		  "<div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\">"+
		    "<ul class=\"nav navbar-nav\">"+
		      "<li class=\"divider\"></li>"+
		      "<li class=\"\"><a href=\"create-quiz.jsp\">Create</a></li>"+
		      "<li class=\"divider\"></li>"+
		      "<li class=\"\"><a href=\"browse-quizzes.jsp?type=category&value=category\">Browse Quizzes</a></li>"+
		      "<li class=\"divider\"></li>"+
		      "<li class=\"\"><a href=\"browse-users.jsp?type=searchAll&value=searchAll\">Browse Users</a></li>"+
		    "</ul>"+

		    "<ul class=\"nav navbar-nav navbar-right\">"+
		   "<!--    <li><a href=\"#\">My Account</a></li> -->"+
		       "<li class=\"dropdown\">"+
		        "<a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">My Account <b class=\"caret\"></b></a>"+
		        "<ul class=\"dropdown-menu\">"+
		          "<li><a href=\"inbox.jsp\">Inbox</a></li>"+
		          admin +
		          "<li><a href=\"user-profile.jsp?id="+user.getID()+"\">My Profile</a></li>"+
		          "<li><a href=\"friends.jsp\">Edit Friends</a></li>"+
		          "<li><a href=\"SignOutServlet\">Sign Out</a></li>"+
		        "</ul>"+
		      "</li>"+
		    "</ul>"+
//		    "<form class=\"navbar-form navbar-right\" role=\"search\">"+
//		      "<div class=\"form-group\">"+
//		        "<input type=\"text\" class=\"form-control\" placeholder=\"Search for a Quiz\">"+
//		      "</div>"+
//		      "<button type=\"submit\" class=\"btn btn-default\">Submit</button>"+
//		    "</form>"+
		  "</div><!-- /.navbar-collapse -->"+
		"</nav>";
		
		return navBar;
	}
	
	public static String generatePartialNavBar(){
		String navBar = "<nav class=\"navbar navbar-default\" role=\"navigation\">"+
			  "<!-- Brand and toggle get grouped for better mobile display -->"+
			  "<div class=\"navbar-header\">"+
			    "<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#bs-example-navbar-collapse-1\">"+
			      "<span class=\"sr-only\">Toggle navigation</span>"+
			      "<span class=\"icon-bar\"></span>"+
			      "<span class=\"icon-bar\"></span>"+
			      "<span class=\"icon-bar\"></span>"+
			    "</button>"+
			    "<p class=\"navbar-dummy\">Quizlet++</p>"+
			  "</div>"+
			 "</nav>";
		
		return navBar;
	}
	
	public static String generateIndexNavBar(){
		String navBar = "<nav class=\"navbar navbar-default\" role=\"navigation\">"+
			  "<!-- Brand and toggle get grouped for better mobile display -->"+
			  "<div class=\"navbar-header\">"+
			    "<button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\"#bs-example-navbar-collapse-1\">"+
			      "<span class=\"sr-only\">Toggle navigation</span>"+
			      "<span class=\"icon-bar\"></span>"+
			      "<span class=\"icon-bar\"></span>"+
			      "<span class=\"icon-bar\"></span>"+
			    "</button>"+
			    "<a class=\"navbar-brand\" href=\"index.html\">Quizlet++</a>"+
			  "</div>"+
			 "</nav>";
		
		return navBar;
	}
}
