<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.ArrayList" %>
    <%@ page import="QuizProject.UserPackage.MessagePackage.*" %>
    <%@ page import="QuizProject.DBConnection" %>
    <%@ page import="Servlets.*" %>
    <%@ page import="QuizProject.UserPackage.*" %>
    <%@ page import="QuizProject.QuizPackage.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Browse Quizzes</title>
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
		String type = "category";
		String value = "category";
		if(request.getParameter("type")!=null&&!request.getParameter("type").equals("")){
			type = request.getParameter("type");
		}
		if(request.getParameter("value")!=null&&!request.getParameter("value").equals("")){
			value = request.getParameter("value");
		}%>

<!-- Print Complete Navigation Bar --> 
<%String fullNavBar = HTMLHelper.generateFullNavBar(user); %>
<%= fullNavBar %>

<div id="main">
<h2>Browse Quizzes</h2>

	<table align="center">
		<tr>
			<form action="BrowseQuizzesServlet" method="post">
				<input type="hidden" name="type" value="searchByName">
				<td><input type="text" class="form-control" name="value" placeholder="Enter Quiz Name"></td>
				<td><button type="submit" class="btn btn-info pull-right" value = "Submit">Search by Name</button></td>
			</form>
			<form action="BrowseQuizzesServlet" method="post">
				<input type="hidden" name="type" value="searchByTag">
				<td><input type="text" class="form-control" name="value" placeholder="Enter Quiz Tag"></td>
				<td><button type="submit" class="btn btn-info pull-right" value = "Submit">Search by Tag</button></td>
			</form>
			<td></td>
			<form action="BrowseQuizzesServlet" method="post">
			<%if(type.equals("category")){ %>
				<input type="hidden" name="type" value="searchAll">
				<td><button type="submit" class="btn btn-primary pull-right" value = "Submit">Show All Quizzes</button></td>
			<%}else{%>
				<input type="hidden" name="type" value="category">
				<td><button type="submit" class="btn btn-primary pull-right" value = "Submit">Show Categorized Quizzes</button></td>
			<%} %>
			</form>
		</tr>
	</table>
<br>

<%if(type.equals("category")){
	ArrayList<String> categories = Categories.getCategoryNames();
	for(String category:categories){
		ArrayList<Quiz> quizzes = Quiz.getQuizzesForCategory(user.getConnection(), category);%>
		 <div class="panel panel-default">
		  <div class="panel-heading"><%=category %> Quizzes</div>
		  <!-- Table -->
		  <table class="table">
		         <% 
		        if(quizzes.size()==0){ %>
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
		        for (Quiz quiz: quizzes) { %>
		          <tr>
		            <td><%=quizIndex %></td> <!-- quiz number -->
		            <td><%=HTMLHelper.linkforQuizSummary(quiz)%></td> <!-- quiz title -->           
		            <td><%=HTMLHelper.linkforUser(quiz.getCreator()) %></td> <!-- quiz creator -->
		            <!-- Initiate Quiz.  -->
					<td><form action=<%=HTMLHelper.hrefForQuizSummary(quiz)%>>
						<input type="hidden" name="id" value="<%=quiz.getID()%>"/>
						<button type="submit" class="btn btn-default">Start Quiz</button>
						</form>
					</td>
					<!-- Initiate Quiz in Practice Mode -->
					<!-- <td><button type="button" class="btn btn-default pull-right">Start Practice Mode</button></td> -->
		          </tr>
		        <% quizIndex++;}
		        }%> 
		        </tbody>
		  </table>
		</div>
<%	}
}else {
	ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
	if(type.equals("searchByName")){
		quizzes = Quiz.getQuizzesForName(user.getConnection(), value);
	}else if(type.equals("searchByTag")){
		quizzes = Quiz.getQuizzesForTag(user.getConnection(), value);
	} else{
		quizzes = Quiz.getAllQuizzes(user.getConnection());
	}%>
	
  <div class="panel panel-default">
  <div class="panel-heading">Quizzes</div>
  <!-- Table -->
  <table class="table">
         <% 
        if(quizzes.size()==0){ %>
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
        for (Quiz quiz: quizzes) { %>
          <tr>
            <td><%=quizIndex %></td> <!-- quiz number -->
            <td><%=HTMLHelper.linkforQuizSummary(quiz)%></td> <!-- quiz title -->           
            <td><%=HTMLHelper.linkforUser(quiz.getCreator()) %></td> <!-- quiz creator -->
            <!-- Initiate Quiz.  -->
			<td><form action=<%=HTMLHelper.hrefForQuizSummary(quiz)%>>
				<input type="hidden" name="id" value="<%=quiz.getID()%>"/>
				<button type="submit" class="btn btn-default">Start Quiz</button>
				</form>
			</td>
			<!-- Initiate Quiz in Practice Mode -->
			<!-- <td><button type="button" class="btn btn-default pull-right">Start Practice Mode</button></td> -->
          </tr>
        <% quizIndex++;}
        }%> 
        </tbody>
  </table>
</div>
	
<%}%>
</div>

</body>
</html>