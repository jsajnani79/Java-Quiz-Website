<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="Servlets.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add Question</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" href="QuizletStylesheet.css"/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.js"></script>
<style></style>
</head>
<body>

<!-- Print Partial Navigation Bar --> 
<%String partialNavBar = HTMLHelper.generatePartialNavBar(); %>
<%= partialNavBar %>

<div id = "main">

<!-- MATCHING -->
<form action="AddQuestionToQuiz" method="post">
<%int id = Integer.parseInt(request.getParameter("id")); %>
<input name="id" type="hidden" value="<%=id%>"/>
<input name="questionType" type="hidden" value="MatchingQuestion"/> 
<div class="panel panel-default">
  <div class="panel-heading"> 
  	<h3 class="panel-title">Add a  Matching Question</h3>
  </div>
  <div class="panel-body">
    <textarea class="form-control" maxlength="1000" rows="2" name="questionBackground" placeholder="Add any background info on the question here"></textarea>
  </div>
<div id="questionTable">
<textarea class="form-control" maxlength="1000" rows="3" name="questionText" placeholder="Type question text here"></textarea>
<br>
Please type the option and its corresponding answer per line
	<table>
	<tr>
	    <td><input type="text" maxlength="200" name="option0" class="form-control" placeholder = "Option 1" /></td>
	    <td><input type="text" maxlength="200" name="answer0" class="form-control" placeholder = "Answer 1" /></td>
	</tr>
	<tr>
	    <td><input type="text" maxlength="200" name="option1" class="form-control" placeholder = "Option 2" /></td>
	    <td><input type="text" maxlength="200" name="answer1" class="form-control" placeholder = "Answer 2" /></td>
	</tr>
	<tr>
	    <td><input type="text" maxlength="200" name="option2" class="form-control" placeholder = "Option 3" /></td>
	    <td><input type="text" maxlength="200" name="answer2" class="form-control" placeholder = "Answer 3" /></td>
	</tr>
	<tr>
	    <td><input type="text" maxlength="200" name="option3" class="form-control" placeholder = "Option 4" /></td>
	    <td><input type="text" maxlength="200" name="answer3" class="form-control" placeholder = "Answer 4" /></td>
	</tr>
	</table>
<br>
</div>
</div>
	<button type="submit" class="btn btn-info pull-right" value = "Submit">Submit</button>
</form>
<form action="AddQuestionToQuiz" method="get">
	<input name="id" type="hidden" value="<%=id%>"/> 
	<button type="submit" class="btn btn-warning pull-right" value = "Submit">Cancel</button>
	</form>
</div> <!-- end of main div -->
</body>
</html>