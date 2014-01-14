<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="QuizProject.DBConnection" %>
    <%@ page import="Servlets.*" %>
    <%@ page import="QuizProject.UserPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Profile</title>
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
<%	 	User user = (User) session.getAttribute(SessionListener.AUTHENTICATED_USER_KEY); 
		int id = user.getID();%>
<!-- Print Complete Navigation Bar --> 
<%String fullNavBar = HTMLHelper.generateFullNavBar(user); %>
<%= fullNavBar %>

<div id = "main">
<h2>Edit Profile</h2>
<form class="form-inline" role="form" action="user-profile.jsp" method="post">
	<input name="id" type="hidden" value="<%=id%>"/>
  <button type="submit" class="btn btn-info btn-sm pull-right">Return to Profile</button>
</form>
<br> 
<br>
<div class="panel panel-default">
  <div class="panel-heading">
    <h2 class="panel-title"> Edit Username </h2>
  </div>
  <div class="panel-body">

<form class="form-inline" role="form" action="EditProfileServlet" method="post">
<input name="type" type="hidden" value="editUsername"/>
  <div class="form-group">
    <label for="inputNewUsername" class="col-sm-5 control-label">New Username</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="newUsername" placeholder="New username">
    </div>
  </div>
  <button type="submit" class="btn btn-info btn-sm pull-right">Update Username</button>
</form>
<br>
  </div>
</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h2 class="panel-title"> Edit Password </h2>
  </div>
  <div class="panel-body">

<form class="form-inline" role="form" action="EditProfileServlet" method="post">
<input name="type" type="hidden" value="editPassword"/>
  <div class="form-group">
    <label for="currentPassword" class="col-sm-9 control-label">Current password</label>
    <div class="col-sm-10">
      <input type="password" class="form-control" name="currentPassword" placeholder="Current password">
    </div>
  </div>
<br>
  <div class="form-group">
    <label for="newPassword" class="col-sm-9 control-label">New password</label>
    <div class="col-sm-10">
      <input type="password" class="form-control" name="newPassword" placeholder="New password">
    </div>
  </div>
  <button type="submit" class="btn btn-info btn-sm pull-right">Update Password</button>
</form>
  </div>
</div>

</div> <!-- End of Main Div -->
</body>
</html>