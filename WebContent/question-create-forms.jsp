<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Input Question Types</title>
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
    <textarea class="form-control" rows="2" name="questionBackground" placeholder="Add any background info on the question here"></textarea>
  </div>
<div id="questionTable">
  <textarea class="form-control" rows="3" name="questionText" placeholder="Type question text here"></textarea>
  <br>
  <textarea class="form-control" rows="2" name="questionAnswers" placeholder="Add all possible answers, separated by two semi-colons (;;) here"></textarea>
    <br>
</div>

</div>

<!-- MULTIPLE CHOICE -->
<div class="panel panel-default">
  <div class="panel-heading"> 
  	<h3 class="panel-title">2. Multiple Choice</h3>
  </div>
  <div class="panel-body">
    <textarea class="form-control" rows="2" name="questionBackground" placeholder="Add any background info on the question here"></textarea>
  </div>
<div id="questionTable">
  <textarea class="form-control" rows="3" name="questionText" placeholder="Type question text here"></textarea>
  <p>Enter the answer options below. Select the correct answer.</p>
<div class="row">
    <div class="col-lg-6">
        <div class="input-group">
            <span class="input-group-addon">
                <input type="radio" name="multChoice[]" value="choice 1" />
            </span>
            <input type="text" name="option[]" class="form-control" placeholder = "Option 1" />
        </div>
        <div class="input-group">
            <span class="input-group-addon">
                <input type="radio" name="multChoice[]" value="choice 2" />
            </span>
            <input type="text" name="option[]" class="form-control" placeholder = "Option 2" />
        </div>
        <div class="input-group">
            <span class="input-group-addon">
                <input type="radio" name="multChoice[]" value="choice 3" />
            </span>
            <input type="text" name="option[]" class="form-control" placeholder = "Option 3" />
        </div>
        <div class="input-group">
            <span class="input-group-addon">
                <input type="radio" name="multChoice[]" value="choice 4" />
            </span>
            <input type="text" name="option[]" class="form-control" placeholder = "Option 4" />
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
    <textarea class="form-control" rows="2" name="questionBackground" placeholder="Add any background info on the question here"></textarea>
  </div>
<div id="questionTable">
  <textarea class="form-control" rows="3" name="questionText" placeholder="Type question text here"></textarea>
    <p>Enter the answer options below. Select the correct answer(s).</p>
<div class="row">
    <div class="col-lg-6">
        <div class="input-group">
            <span class="input-group-addon">
                <input type="checkbox" name="checkChoices[]" value="choice 1" />
            </span>
            <input type="text" name="multipleOption[]" class="form-control" placeholder = "Option 1" />
        </div>
        <div class="input-group">
            <span class="input-group-addon">
                <input type="checkbox" name="checkChoices[]" value="choice 2" />
            </span>
            <input type="text" name="multipleOption[]" class="form-control" placeholder = "Option 2" />
        </div>
        <div class="input-group">
            <span class="input-group-addon">
                <input type="checkbox" name="checkChoices[]" value="choice 3" />
            </span>
            <input type="text" name="multipleOption[]" class="form-control" placeholder = "Option 3" />
        </div>
        <div class="input-group">
            <span class="input-group-addon">
                <input type="checkbox" name="checkChoices[]" value="choice 4" />
            </span>
            <input type="text" name="multipleOption[]" class="form-control" placeholder = "Option 4" />
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
   <textarea class="form-control" rows="2" name="questionBackground" placeholder="Add any background info on the question here"></textarea>
  </div>
<div id="questionTable">
  <textarea class="form-control" rows="2" name="firstSentence" placeholder="Enter the text preceeding the blank here"></textarea>
  <textarea class="form-control" rows="2" name="secondSentence" placeholder="Enter the text following the blank here"></textarea>
  <br>
  <textarea class="form-control" rows="2" name="questionAnswers" placeholder="Add all possible answers, separated by two semi-colons (;;) here"></textarea>
</div>
</div>
</div>

<!-- ORDERED MULTI-TEXT  --><!-- UNORDERED MULTI-TEXT -->
<div class="panel panel-default">
  <div class="panel-heading"> 
  	<h3 class="panel-title">5. Multi-Text (Ordered and Unordered)</h3>
  </div>
  <div class="panel-body">
    <textarea class="form-control" rows="2" name="questionBackground" placeholder="Add any background info on the question here"></textarea>
  </div>
<div id="questionTable">
 <textarea class="form-control" rows="3" name="questionText" placeholder="Type question text here"></textarea>
	<div class="row">
    <div class="col-lg-6">
        <div class="input-group">
            <input type="text" name="response" placeholder="Answer" />
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
   <textarea class="form-control" rows="2" name="questionBackground" placeholder="Add any background info on the question here"></textarea>
  </div>
<div id="questionTable">
<textarea class="form-control" rows="3" name="questionText" placeholder="Type question text here"></textarea>
	<textarea class="form-control" rows="1" name="pictureURL" placeholder="Paste link to picture here"></textarea>
	<br>
<textarea class="form-control" rows="2" name="questionAnswers" placeholder="Add all possible answers, separated by two semi-colons (;;) here"></textarea>
</div>
</div>

<!-- MATCHING -->
<div class="panel panel-default">
  <div class="panel-heading"> 
  	<h3 class="panel-title">7. Matching </h3>
  </div>
  <div class="panel-body">
    <textarea class="form-control" rows="2" name="questionBackground" placeholder="Add any background info on the question here"></textarea>
  </div>
<div id="questionTable">
Please type the one question and its corresponding answer per line
<form role="form">
<table>
<tr>
    <td><input type="text" name="option0" class="form-control" placeholder = "Option 1" /></td>

    <td><input type="text" name="answer0" class="form-control" placeholder = "Answer 1" /></td>
</tr>
<tr>
    <td><input type="text" name="option1" class="form-control" placeholder = "Option 2" /></td>

    <td><input type="text" name="answer1" class="form-control" placeholder = "Answer 2" /></td>
</tr>
<tr>
    <td><input type="text" name="option2" class="form-control" placeholder = "Option 3" /></td>

    <td><input type="text" name="answer2" class="form-control" placeholder = "Answer 3" /></td>
</tr>
<tr>
    <td><input type="text" name="option3" class="form-control" placeholder = "Option 4" /></td>

    <td><input type="text" name="answer3" class="form-control" placeholder = "Answer 4" /></td>
</tr>
</table>
</form>
</div>
</div>

</body>
</html>