<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Question Types</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.css"/>
<link rel="stylesheet" href="QuizletStylesheet.css"/>
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://code.jquery.com/jquery.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="bootstrap/js/bootstrap.js"></script>
<style></style>
</head>
<body>

<!-- QUESTION-RESPONSE -->
<div class="panel panel-default">
  <div class="panel-heading"> 
  	<h3 class="panel-title">1. Standard Question-Response</h3>
  </div>
  <div class="panel-body">
    <p>Background on Question Here</p>
  </div>
<div id="questionTable">
  <div class="well">Question Goes Here</div>
	<div class="row">
    <div class="col-lg-6">
        <div class="input-group">
            <input type="text" name="response1" class="form-control" placeholder="Answer" />
        </div>
    </div>
    <br>
</div>
</div>
</div>

<form action="TestQuestionServlet" method="post">
<!-- MULTIPLE CHOICE -->
<div class="panel panel-default">
  <div class="panel-heading"> 
  	<h3 class="panel-title">2. Multiple Choice</h3>
  </div>
  <div class="panel-body">
    <p>Background on Question Here</p>
  </div>
<div id="questionTable">
  <div class="well">Question Goes Here</div>
<div class="row">
    <div class="col-lg-6">
        <div class="input-group">
            <span class="input-group-addon">
                <input type="radio" name="multChoice[]" value="choice 1" />
            </span>
            <input type=text name="option[]" class="form-control" value = "Option 1"/>
        </div>
        <div class="input-group">
            <span class="input-group-addon">
                <input type="radio" name="multChoice[]" value="choice 2" />
            </span>
            <input type="text" name="option[]" class="form-control" value = "Option 2"/>
        </div>
        <div class="input-group">
            <span class="input-group-addon">
                <input type="radio" name="multChoice[]" value="choice 3" />
            </span>
            <input type="text" name="option[]" class="form-control" value = "Option 3"/>
        </div>
        <div class="input-group">
            <span class="input-group-addon">
                <input type="radio" name="multChoice[]" value="choice 4" />
            </span>
            <input type="text" name="option[]" class="form-control" value = "Option 4" />
        </div>
    </div>
</div>
</div>
    <br>
</div>
</div>

<!-- MULTI-MULTIPLE CHOICE -->
<div class="panel panel-default">
  <div class="panel-heading"> 
  	<h3 class="panel-title">3. Multi-Multiple Choice</h3>
  </div>
  <div class="panel-body">
    <p>Background on Question Here</p>
  </div>
<div id="questionTable">
  <div class="well">Question Goes Here</div>
<div class="row">
    <div class="col-lg-6">
        <div class="input-group">
            <span class="input-group-addon">
                <input type="checkbox" name="checkChoices1" value="choice 1" />
            </span>
            <input type="text" name="multipleOption1" class="form-control" value = "Option 1" disabled/>
        </div>
        <div class="input-group">
            <span class="input-group-addon">
                <input type="checkbox" name="checkChoices2" value="choice 2" />
            </span>
            <input type="text" name="multipleOption2" class="form-control" value = "Option 2"/>
        </div>
        <div class="input-group">
            <span class="input-group-addon">
                <input type="checkbox" name="checkChoices3" value="choice 3" />
            </span>
            <input type="text" name="multipleOption3" class="form-control" value = "Option 3" disabled/>
        </div>
        <div class="input-group">
            <span class="input-group-addon">
                <input type="checkbox" name="checkChoices4" value="choice 4" />
            </span>
            <input type="text" name="multipleOption4" class="form-control" value = "Option 4" disabled/>
        </div>
    </div>
</div>
    <br>
</div>
</div>


<!-- FILL IN THE BLANK -->
<div class="panel panel-default">
  <div class="panel-heading"> 
  	<h3 class="panel-title">4. Fill in the Blank</h3>
  </div>
  <div class="panel-body">
    <p>Background on Question Here</p>
  </div>
<div id="questionTable">
  <div class="well">
    <div class="col-lg-6">
        <div class="input-group">
           First Part of Question 
           <input type="text" name="response2"  placeholder="Answer" /> 
           Second Part of Question
        </div>
    </div>
</div>
</div>
</div>

<!-- ORDERED MULTI-TEXT  --><!-- UNORDERED MULTI-TEXT -->
<div class="panel panel-default">
  <div class="panel-heading"> 
  	<h3 class="panel-title">5. Multi-Text (Ordered and Unordered)</h3>
  </div>
  <div class="panel-body">
    <p>Background on Question Here</p>
  </div>
<div id="questionTable">
  <div class="well">Question Goes Here</div>
	<div class="row">
    <div class="col-lg-6">
        <div class="input-group">
            <input type="text" name="response3" placeholder="Answer" />
    		<button type="submit" class="btn btn-default">Add Answer</button>
        </div>
    </div>
	<br>
</div>
</div>
</div>

<!-- PICTURE RESPONSE -->
<div class="panel panel-default">
  <div class="panel-heading"> 
  	<h3 class="panel-title">6. Picture Response </h3>
  </div>
  <div class="panel-body">
    <p>Background on Question Here</p>
  </div>
<div id="questionTable">
<div class="well">Question Goes Here</div>
	<img src="http://events.stanford.edu/events/252/25201/Memchu_small.jpg" class="img-responsive" alt="Responsive image">
	<br>
	<div class="row">
    <div class="col-lg-6">
        <div class="input-group">
            <input type="text" name="response4" class="form-control" placeholder="Answer" />
        </div>
    </div>
	<br>
</div>
</div>
</div>

<!-- MATCHING -->
<div class="panel panel-default">
  <div class="panel-heading"> 
  	<h3 class="panel-title">7. Matching </h3>
  </div>
  <div class="panel-body">
    <p>Background on Question Here</p>
  </div>
<div id="questionTable">
  1. Option 1 &#65515;
  <select class="form-control" name="matching1">
  <option >one</option>
  <option >testing two</option>
  <option >3</option>
  <option >4</option>
</select>
</div>	
</div>
</div>
<button type="submit" class="btn btn-info pull-right" value = "Submit">Submit</button>
</form>

</body>
</html>