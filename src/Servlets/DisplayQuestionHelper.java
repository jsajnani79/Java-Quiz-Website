package Servlets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import QuizProject.QuestionPackage.FillInTheBlankQuestion;
import QuizProject.QuestionPackage.MatchingQuestion;
import QuizProject.QuestionPackage.MultiMultipleChoiceQuestion;
import QuizProject.QuestionPackage.MultipleChoiceQuestion;
import QuizProject.QuestionPackage.OrderedMultiTextQuestion;
import QuizProject.QuestionPackage.PictureResponseQuestion;
import QuizProject.QuestionPackage.ResponseQuestion;
import QuizProject.QuestionPackage.UnorderedMultiTextQuestion;


public class DisplayQuestionHelper {
	
	private static final int NUMBER_ANSWER_OPTIONS = 4;
	
	public static ArrayList<String> parseAnswersString(String answersString){
		ArrayList<String> actualAnswers = new ArrayList<String>();
	      for (String retval: answersString.split("[\\r\\n]+")){
	    	  actualAnswers.add(retval.toLowerCase());
	       }
	      return actualAnswers;    
	}
	
	public static String textforQuestionResponse(ResponseQuestion question,int questionIndex, int answerIndex, int numAnswers){
		String background = question.getBackgroundText();
		String questionText = question.getQuestionText();
		int displayIndex = answerIndex+1;
		return "<div class=\"panel panel-default\"> "+
				  "<div class=\"panel-heading\"> "+
				  	"<h3 class=\"panel-title\">" + questionIndex +". Standard Question-Response</h3> "+
				  "</div> "+
				  "<div class=\"panel-body\"> "+
				    "<p>"+background+"</p> "+
				  "</div> "+
				"<div id=\"questionTable\"> "+
				  "<div class=\"well\">"+questionText+"</div> "+
					"<div class=\"row\"> "+
				    "<div class=\"col-lg-6\"> "+
				        "<div class=\"input-group\"> "+
				            "<input type=\"text\" name=\"answer" + displayIndex +"\" class=\"form-control\" placeholder=\"Answer\" /> "+
				        "</div> "+
				    "</div> "+
				    "<br>"+
				"</div>"+
				"</div>"+
				"</div>";
	}
	
	public static String textforMultipleChoice(MultipleChoiceQuestion question,int questionIndex, int answerIndex, int numAnswers){
		String background = question.getBackgroundText();
		String questionText = question.getQuestionText();
		ArrayList<String> possibleAnswers = question.getPossibleAnswers();
		StringBuilder options = new StringBuilder();
		int displayIndex = answerIndex+1;
		for(int index=0;index<NUMBER_ANSWER_OPTIONS;index++){
	        options.append("<div class=\"input-group\">" +
		            "<span class=\"input-group-addon\">" +
		                "<input type=\"radio\" name=\"answer"+displayIndex+"\" value=\""+index+"\" />" +
		            "</span>" +
		            "<input type=text name=\"option[]\" class=\"form-control\" value = \""+possibleAnswers.get(index)+"\" disabled/>" +
		        "</div>");
		}
		return "<div class=\"panel panel-default\">" +
		  "<div class=\"panel-heading\"> " +
		  	"<h3 class=\"panel-title\">" + questionIndex +". Multiple Choice</h3>" +
		  "</div>" +
		  "<div class=\"panel-body\">" +
		    "<p>"+background+"</p> "+
		 " </div>" +
		"<div id=\"questionTable\">" +
			"<div class=\"well\">"+questionText+"</div> "+
		"<div class=\"row\">" +
		    "<div class=\"col-lg-6\">" +
		    		options +
		    "</div>" +
		"</div>" +
		"</div>" +
		    "<br>" +
		"</div>";
	}
	
	public static String textforFillInTheBlank(FillInTheBlankQuestion question,int questionIndex, int answerIndex, int numAnswers){
		String background = question.getBackgroundText();
		String questionText = question.getQuestionText();
		int displayIndex = answerIndex+1;
		return "<div class=\"panel panel-default\"> "+
		  "<div class=\"panel-heading\"> "+
		  	"<h3 class=\"panel-title\">" + questionIndex +". Fill in the Blank</h3> "+
		  "</div> "+
		  "<div class=\"panel-body\"> "+
		    "<p>"+background+"</p> "+
		  "</div> "+
		"<div id=\"questionTable\"> "+
		  "<div class=\"well\">"+questionText+"</div> "+
			"<div class=\"row\"> "+
		    "<div class=\"col-lg-6\"> "+
		        "<div class=\"input-group\"> "+
		            "<input type=\"text\" name=\"answer" + displayIndex +"\" class=\"form-control\" placeholder=\"Answer\" /> "+
		        "</div> "+
		    "</div> "+
		    "<br>"+
		"</div>"+
		"</div>"+
		"</div>";
	}
	
	public static String textforMatching(MatchingQuestion question,int questionIndex, int answerIndex, int numAnswers){
		String background = question.getBackgroundText();
		String questionText = question.getQuestionText();
		ArrayList<String> questionsList = question.getQuestionsArrayList();
		ArrayList<String> answersList = question.getAnswersArrayList();
		long seed = System.nanoTime();
		Collections.shuffle(answersList, new Random(seed));
		StringBuilder selects = new StringBuilder();
		for(int index = 0; index<numAnswers;index++){
			selects.append("<option>"+answersList.get(index)+"</option>");
		}
		
		StringBuilder options = new StringBuilder();
		for(int optionIndex=0;optionIndex<numAnswers;optionIndex++){
			int next = optionIndex+1;
			int displayIndex = answerIndex+1+optionIndex;
			options.append(next + ". " + questionsList.get(optionIndex) + "&#65515; ");
			options.append("<select class=\"form-control\" name=\"answer"+displayIndex+"\">");
			options.append(selects + "</select>"); 
		}
		
		return "<div class=\"panel panel-default\">"+
			  "<div class=\"panel-heading\"> "+
			  	"<h3 class=\"panel-title\">" + questionIndex +". Matching </h3>"+
			  "</div>"+
			  "<div class=\"panel-body\">"+
			  	"<p>"+background+"</p> "+
			  "</div>"+
			"<div id=\"questionTable\">"+
			"<div class=\"well\">"+questionText+"</div> "+
			options+  
			"</div>	"+
			"</div>";
	}
	public static String textforMultiMultipleChoice(MultiMultipleChoiceQuestion question,int questionIndex, int answerIndex, int numAnswers){
		String background = question.getBackgroundText();
		String questionText = question.getQuestionText();
		ArrayList<String> possibleAnswers = question.getPossibleAnswers(); 
		StringBuilder options = new StringBuilder();		
		for(int index=0;index<numAnswers;index++){
			int displayIndex = answerIndex+index+1;
	        options.append("<div class=\"input-group\">" +
	                "<span class=\"input-group-addon\">" +
	                "<input type=\"checkbox\" name=\"answer"+displayIndex+"\" value=\""+index+"\" />" +
	            "</span>" +
	           " <input type=\"text\" name=\"multipleOption"+ displayIndex+"\" class=\"form-control\" value = \""+possibleAnswers.get(index)+"\" disabled/>" +
	        "</div>");
		}
		return "<div class=\"panel panel-default\">" +
		  "<div class=\"panel-heading\"> " +
		  "<h3 class=\"panel-title\">" + questionIndex +". Multi-Multiple Choice</h3>" +
		  "</div>" +
		  "<div class=\"panel-body\">" +
		  	"<p>"+background+"</p> "+
		 " </div>" +
		"<div id=\"questionTable\">" +
			"<div class=\"well\">"+questionText+"</div> "+
		"<div class=\"row\">" +
		    "<div class=\"col-lg-6\">" +
		    		options +
		    "</div>" +
		"</div>" +
		    "<br>" +
		"</div>" +
		"</div>";
	}
	public static String textforOrderedMultiText(OrderedMultiTextQuestion question,int questionIndex, int answerIndex, int numAnswers){
		String background = question.getBackgroundText();
		String questionText = question.getQuestionText();
		StringBuilder answerInputs = new StringBuilder();
		for(int index =0;index<numAnswers;index++){
			int displayIndex = answerIndex+index+1;
			answerInputs.append("<input type=\"text\" name=\"answer" + displayIndex +"\" class=\"form-control\" placeholder=\"Answer\" />");
		}
		return "<div class=\"panel panel-default\">" +
			  "<div class=\"panel-heading\"> " +
			  	"<h3 class=\"panel-title\">" + questionIndex +". Ordered Multi-Text </h3>" +
			 " </div>" +
			  "<div class=\"panel-body\">" +
			  	"<p>"+background+"</p> "+
			  "</div>" +
			"<div id=\"questionTable\">" +
				"<div class=\"well\">"+questionText+"</div> "+
				"<div class=\"row\">" +
			    "<div class=\"col-lg-6\">" +
			        "<div class=\"input-group\">" +
			            answerInputs +
			        "</div>" +
			    "</div>" +
				"<br>" +
			"</div>" +
			"</div>" +
			"</div>";
	}
	public static String textforPictureResponse(PictureResponseQuestion question,int questionIndex, int answerIndex, int numAnswers){
		String background = question.getBackgroundText();
		String questionText = question.getQuestionText();
		String pictureURL = question.getPictureURL();
		int displayIndex = answerIndex+1;
		return "<div class=\"panel panel-default\">"+
			  "<div class=\"panel-heading\"> "+
			  	"<h3 class=\"panel-title\">" + questionIndex +". Picture Response </h3>"+
			 " </div>"+
			  "<div class=\"panel-body\">"+
			  	"<p>"+background+"</p> "+
			  "</div>"+
			"<div id=\"questionTable\">"+
				"<div class=\"well\">"+questionText+"</div> "+
				"<img src=\""+pictureURL+"\" class=\"img-responsive\" alt=\"question image\">"+
				"<br>"+
				"<div class=\"row\">"+
			    "<div class=\"col-lg-6\">"+
			        "<div class=\"input-group\">"+ 	
			            "<input type=\"text\" name=\"answer" + displayIndex +"\" class=\"form-control\" placeholder=\"Answer\" />"+
			       " </div>"+
			    "</div>"+
				"<br>"+
			"</div>"+
			"</div>"+
			"</div>";
	}
	public static String textforUnorderedMultiText(UnorderedMultiTextQuestion question,int questionIndex, int answerIndex, int numAnswers){
		String background = question.getBackgroundText();
		String questionText = question.getQuestionText();
		StringBuilder answerInputs = new StringBuilder();
		for(int index =0;index<numAnswers;index++){
			int displayIndex = answerIndex+index+1;
			answerInputs.append("<input type=\"text\" name=\"answer" + displayIndex +"\" class=\"form-control\" placeholder=\"Answer\" />");
		}
		return "<div class=\"panel panel-default\">" +
				  "<div class=\"panel-heading\"> " +
				  	"<h3 class=\"panel-title\">" + questionIndex +". Unordered Multi-Text</h3>" +
				 " </div>" +
				  "<div class=\"panel-body\">" +
				  	"<p>"+background+"</p> "+
				  "</div>" +
				"<div id=\"questionTable\">" +
					"<div class=\"well\">"+questionText+"</div> "+
					"<div class=\"row\">" +
				    "<div class=\"col-lg-6\">" +
				        "<div class=\"input-group\">" +
				            answerInputs +
				        "</div>" +
				    "</div>" +
					"<br>" +
				"</div>" +
				"</div>" +
				"</div>";
	}
	
}
