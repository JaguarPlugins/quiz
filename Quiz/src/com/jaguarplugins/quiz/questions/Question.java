package com.jaguarplugins.quiz.questions;

import java.util.ArrayList;

public class Question {

	private String[] question;
	private String[] answer;

	public Question(String question, String answer) {

		this.question = question.split(",");
		this.answer = answer.split(",");
	}

	public String[] getQuestion(boolean english) {

		if (english) {
			return question;
		} else {
			return answer;
		}

	}

	public boolean anyCorrect(boolean english, String guess) {

		if (english) {
			for (String x : question) {
				if (guess.equalsIgnoreCase(x)) {
					return true;
				}
			}
		} else {
			for (String x : answer) {
				if (guess.equalsIgnoreCase(x)) {
					return true;
				}
			}
		}

		return false;

	}
	
	public static boolean contains(ArrayList<Question> questions, Question question) {
		
		for (Question q : questions) {
			
			if (q.question[0].equalsIgnoreCase(question.question[0])) {
				
				return true;
				
			}
			
		}
		
		return false;
			
	}

}