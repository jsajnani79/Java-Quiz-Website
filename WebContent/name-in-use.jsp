<%@ page import="Servlets.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Please Try Again</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" href="QuizletStylesheet.css"/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.js"></script>
<style></style>
</head>
<body>

<%String indexNavBar = HTMLHelper.generateIndexNavBar(); %>
<%= indexNavBar %>
 
<%ServletContext context = request.getServletContext(); %>
<%String name = request.getParameter("name"); %>
		
<div id = "login">
<h2 align="center">The Name <%= name %>  is Already In Use</h2>
<p align="center">Please enter another name and password.</p>
<br>
<form class="form-horizontal" role="form" action = "CreateAccountServlet" method = "post"> 
  <div class="form-group">
    <label for="inputEmail3" class="col-sm-2 control-label">Username</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" id="inputUsername" name = "name" placeholder="Username">
    </div>
  </div>
  <div class="form-group">
    <label for="inputPassword3" class="col-sm-2 control-label">Password</label>
    <div class="col-sm-10">
      <input type="password" class="form-control" id="inputPassword" name = "password" placeholder="Password">
    </div>
  </div>
 <div class="form-group">
    <div class="col-sm-offset-2 col-sm-10">
      <button type="submit" class="btn btn-info pull-right" value = "Login">Create</button>
    </div>
  </div>
  <a class="btn btn-default pull-right" role="button" href="index.html" >Sign in with an Existing Account</a>
</form>
</div> <!-- end of main div -->

</body>
</html>