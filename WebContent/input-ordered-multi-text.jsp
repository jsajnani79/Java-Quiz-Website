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
<%int id = Integer.parseInt(request.getParameter("id")); %>
<form role="form" action = "orderedQuestionServlet" method = "post">
<input name="id" type="hidden" value="<%=id%>"/>
<!-- ORDERED MULTI-TEXT  --><!-- UNORDERED MULTI-TEXT -->
<div class="panel panel-default">
  <div class="panel-heading"> 
  	<h3 class="panel-title">Add a Ordered Multi-Text Question</h3>
  </div>
  <div class="panel-body">
  </div>
<div id="questionTable">
	<div class="row">
    <div class="col-lg-6">
        <div class="input-group">
        How many answers will there be to this question?
            <input type="text" maxlength="10" id="numAnswers" name="numAnswers" placeholder="Number of answers" />
        </div>
    </div>
	<br>
</div>
</div>
<br>

	<button type="submit" class="btn btn-info pull-right" value = "Submit">Submit</button>

<form action="AddQuestionToQuiz" method="get">

	<input name="id" type="hidden" value="<%=id%>"/>
	<button type="submit" class="btn btn-warning pull-right" value = "Submit">Cancel</button>
	</form>
</form>
</div>

</body>
</html>