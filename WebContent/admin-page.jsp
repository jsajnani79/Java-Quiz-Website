<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="QuizProject.DBConnection" %>
    <%@ page import="Servlets.*" %>
    <%@ page import="QuizProject.UserPackage.*" %>
    <%@ page import="QuizProject.QuizPackage.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Administration</title>
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

<div id = "main">
<h2>Administration</h2>

<div class="panel panel-default">
  <div class="panel-heading">
    <h2 class="panel-title"> Website Stats </h2>
  </div>
  <div class="panel-body">
  		<ul class="list-group">
			<li class="list-group-item">Number of Quizzes Created: <%=Quiz.getAllQuizzes(user.getConnection()).size()%> </li>
			<li class="list-group-item">Number of Quizzes Taken: <%=Score.getAllScores(user.getConnection()).size()%></li>
			<li class="list-group-item">Number of Users: <%=User.getAllUsers(user.getConnection()).size()%></li>
		</ul>
  </div> <!-- panel body -->
  </div> <!-- panel -->
  
<form action="AdminServlet" method="post">
<input name="type" type="hidden" value="announcement"/>
<div class="panel panel-default">
  <div class="panel-heading">
    <h2 class="panel-title"> Post an Announcement </h2>
  </div>
  <div class="panel-body">
    <textarea class="form-control" maxlength="1000" name="announcementText" rows="2"></textarea>
    <br>
    <button type="submit" class="btn btn-info btn-sm pull-right"> Post! </button>
  </div> <!-- panel body -->
  </form>
</div> <!-- panel -->
  
<div class="panel panel-default">
  <div class="panel-heading">
    <h2 class="panel-title"> Manage Users </h2>
  </div>
  <div class="panel-body">

<form class="form-inline" role="form" action="AdminServlet" method="post">
<input name="type" type="hidden" value="removeUser"/>
  <div class="form-group">
    <label for="inputUserToRemove" class="col-sm-5 control-label">Remove A User</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="userToRemove" placeholder="User to Remove">
    </div>
  </div>
  <button type="submit" class="btn btn-info btn-sm pull-right">Remove User</button>
</form>
<br>
<form class="form-inline" role="form" action="AdminServlet" method="post">
<input name="type" type="hidden" value="promoteUser"/>
  <div class="form-group">
    <label for="inputUserToRemove" class="col-sm-9 control-label">Promote User to Admin</label>
    <div class="col-sm-10">
      <input type="text" class="form-control" name="userToPromote" placeholder="User to Promote">
    </div>
  </div>
  <button type="submit" class="btn btn-info btn-sm pull-right">Promote User</button>
</form>
  </div>
</div>

<div class="panel panel-default">
  <div class="panel-heading">
    <h2 class="panel-title"> Manage Quizzes </h2>
  </div>
  <div class="panel-body">
  
  <div class="panel panel-default">
  <div class="panel-heading">
    <h2 class="panel-title"> Reported Quizzes </h2>
  </div>
  <div class="panel-body">
   <table class="table">
           <% 
       	ArrayList<Quiz> reportedQuizzes = Quiz.getReportedQuizzes(user.getConnection());
        if(reportedQuizzes.size()==0){ %>
        	<tbody>
        	<li class="list-group-item">There are no reported quizzes to display</li>
       <% }	else{	%>
    	     <thead>
             <tr>
               <th>#</th>
               <th>Quiz Title</th>            
               <th>Creator</th>
               <th></th>
               <th></th>
             </tr>
           </thead>
           <tbody>  
        <% int quizIndex = 1;
        for (Quiz quiz: reportedQuizzes) { %>
          <tr>
            <td><%=quizIndex %></td> <!-- quiz number -->
            <td><%=HTMLHelper.linkforQuizSummary(quiz)%></td> <!-- quiz title -->           
            <td><%=HTMLHelper.linkforUser(quiz.getCreator()) %></td> <!-- quiz creator -->
            <%-- <td><%=quiz.getReport() %></td> <!-- Reason for report --> --%>
            <!-- Initiate Quiz.  -->
			<td><form class="form-inline" role="form" action="AdminServlet" method="post">
				<input name="type" type="hidden" value="removeQuiz"/>
				<input name="id" type="hidden" value="<%=quiz.getID()%>"/>
				<button type="submit" class="btn btn-default">Remove Quiz</button>
				</form>
			</td>
          </tr>
        <% quizIndex++;}
        }%> 
        </tbody>
  </table>
  
    </div>
</div>
  
  <div class="panel panel-default">
  <div class="panel-heading">
    <h2 class="panel-title"> All Quizzes </h2>
  </div>
  <div class="panel-body">
 <table class="table">
         <% 
       	ArrayList<Quiz> allQuizzes = Quiz.getAllQuizzes(user.getConnection());
        if(allQuizzes.size()==0){ %>
        	<tbody>
        	<li class="list-group-item">There are no quizzes to display</li>
       <% }	else{	%>
    	     <thead>
             <tr>
               <th>#</th>
               <th>Quiz Title</th>            
               <th>Creator</th>
               <th></th>
               <th></th>
             </tr>
           </thead>
           <tbody>  
        <% int quizIndex = 1;
        for (Quiz quiz: allQuizzes) { %>
          <tr>
            <td><%=quizIndex %></td> <!-- quiz number -->
            <td><%=HTMLHelper.linkforQuizSummary(quiz)%></td> <!-- quiz title -->           
            <td><%=HTMLHelper.linkforUser(quiz.getCreator()) %></td> <!-- quiz creator -->
            <!-- Initiate Quiz.  -->
			<td><form class="form-inline" role="form" action="AdminServlet" method="post">
				<input name="type" type="hidden" value="removeQuiz"/>
				<input name="id" type="hidden" value="<%=quiz.getID()%>"/>
				<button type="submit" class="btn btn-default">Remove Quiz</button>
				</form>
			</td>
			<td><form class="form-inline" role="form" action="AdminServlet" method="post">
				<input name="type" type="hidden" value="clearHistory"/>
				<input name="id" type="hidden" value="<%=quiz.getID()%>"/>
				<button type="submit" class="btn btn-default pull-right">Clear History</button>
				</form>
			</td>
          </tr>
        <% quizIndex++;}
        }%> 
        </tbody>
  </table>
  </div>
</div>

  </div>
</div>

</div> <!-- End of Main Div -->
</body>
</html>


<%-- Alternative Stats Header
<div class="jumbotron">
  <h2 align="center">Website Stats</h2>
  <br>
<table class="table table-hover">
        <tbody>
          <tr>
          <th>Number of Quizzes Created</th>
            <td></td>
            <td></td>
            <td></td>
            <td><%=Quiz.getAllQuizzes(user.getConnection()).size()%></td>
          </tr>
          <tr>
          <th>Number of Quizzes Taken</th>
            <td></td>
            <td></td>
            <td></td>
            <td><%=Score.getAllScores(user.getConnection()).size()%></td>
          </tr>
          <tr>
            <th>Number of Users</th>
            <td></td>
            <td></td>
            <td></td>
            <td><%=User.getAllUsers(user.getConnection()).size()%></td>
          </tr>
        </tbody>
</table>
</div>  --%>
