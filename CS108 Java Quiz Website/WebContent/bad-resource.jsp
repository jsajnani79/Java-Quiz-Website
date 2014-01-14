<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
	<%@ page import="Servlets.*" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="QuizProject.UserPackage.MessagePackage.*" %>
    <%@ page import="QuizProject.DBConnection" %>
    <%@ page import="QuizProject.UserPackage.*" %>
    <%@ page import="QuizProject.QuizPackage.*" %>
    <%@ page import="QuizProject.QuestionPackage.*" %>
    <%@ page import="java.util.Set" %>
    <%@ page import="QuizProject.UserPackage.AchievementPackage.*" %>
    <%@ page import="java.util.Date" %>
    <%@ page import="java.text.SimpleDateFormat" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bad Input</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" href="QuizletStylesheet.css"/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.js"></script>
<style></style>
</head>
<body>

<!-- Print Complete Navigation Bar --> 
<%String partialNavBar = HTMLHelper.generatePartialNavBar(); %>
<%= partialNavBar %>
<div id = "main">
<h3> Bad Resource</h3>
The resource you have tried to visit is no longer available. Please use your browser's back button to return to the previous page. 
</div> <!-- end of main div -->
</body>
</html>