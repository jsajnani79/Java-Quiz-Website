<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="QuizProject.DBConnection" %>
    <%@ page import="Servlets.*" %>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="QuizProject.UserPackage.*" %>
    <%@ page import="QuizProject.QuizPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" href="QuizletStylesheet.css"/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.js"></script>
 <style></style>
</head>
<body>
<!-- Initialize User -->
<%	 	User user = (User) session.getAttribute(SessionListener.AUTHENTICATED_USER_KEY); %>
<!-- Print Complete Navigation Bar --> 
<%String fullNavBar = HTMLHelper.generateFullNavBar(user); %>
<%= fullNavBar %>

<div id="main">
<h2>Create A New Quiz</h2>
<form action="CreateQuizServlet" method="post">
<div class="row"><!-- Quiz Title Div -->
  <!-- <div class="col-xs-10"> -->
    <input class="form-control input-lg" type="text" maxlength="64" name="quizTitle" placeholder="Enter Quiz Title">
  <!-- </div> -->
</div> <!-- End Quiz Title Div -->
<br>
<textarea class="form-control" rows="3" maxlength="1000" placeholder="Enter quiz description here" name = "quizDescription" id="quizDescription"></textarea>
<br>

<select class="form-control" name="category">
  <%ArrayList<String> categoryNames = Categories.getCategoryNames();
  for(String categoryName:categoryNames){ %>
  <option><%=categoryName %></option>
  <%} %>
</select>
<br>
<textarea class="form-control" rows="2" maxlength="1000" placeholder="Enter quiz tags here, add each on its own line (Press ENTER after each)" name = "quizTags" id="quizTags"></textarea>
<br>
<div class="row">
    <div class="col-lg-6">
    <div class="input-group">
      <span class="input-group-addon">
        <input type="checkbox" name="canTakePracticeMode" value="1">
      </span>
      <input type="text" class="form-control" value="Enable Practice Mode" disabled>
    </div><!-- /input-group -->
  </div><!-- /.col-lg-6 -->
  <div class="col-lg-6">
    <div class="input-group">
      <span class="input-group-addon">
        <input type="checkbox" name="isRandom" value="1">
      </span>
      <input type="text" class="form-control" value="Randomize Questions on Display" disabled>
    </div><!-- /input-group -->
  </div><!-- /.col-lg-6 -->
  </div><!-- /.row -->
  <div class="row">
  <div class="col-lg-6">
    <div class="input-group">
      <span class="input-group-addon">
        <input type="checkbox" name="isMultiPage" value="1">
      </span>
      <input type="text" class="form-control" value="Show questions on individual pages" disabled>
    </div><!-- /input-group -->
  </div><!-- /.col-lg-6 -->
    <div class="col-lg-6">
    <div class="input-group">
      <span class="input-group-addon">
        <input type="checkbox" name="hasImmediateCorrection" value="1">
      </span>
      <input type="text" class="form-control" value="Show result after each question (for individual page quizzes)" disabled>
    </div><!-- /input-group -->
  </div><!-- /.col-lg-6 -->
</div><!-- /.row -->
<br>

<button type="submit" class="btn btn-info pull-right" value = "Submit">Start Creating!</button>
</form>
	<form action="user-home.jsp">
	<button type="submit" class="btn btn-default pull-right" value = "Submit">Cancel</button>
</form>
</div> <!-- end of main div -->
</body>
</html>