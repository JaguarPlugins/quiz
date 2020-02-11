package com.jaguarplugins.quiz.input;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jaguarplugins.quiz.App;
import com.jaguarplugins.quiz.questions.Mistake;
import com.jaguarplugins.quiz.questions.Question;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Handler {
	
	private ArrayList<Question> questions;
	private int q;
	private int score, totalScore;
	private StringBuilder hint = new StringBuilder();
	
	private ButtonHandler buttonHandler;
	private KeyHandler keyHandler;
	
	public Handler() {
		
		buttonHandler = new ButtonHandler();
		keyHandler = new KeyHandler();
		
	}
	
//	Button Handler
	private class ButtonHandler implements EventHandler<ActionEvent> {
		
		@Override
		public void handle(ActionEvent e) {
			
			if(e.getSource().equals(App.getOkBtn())) {
				
				nextQuestion();
			
			}
			
			if(e.getSource().equals(App.getLoadBtn())) {
				
				if(App.getSelector().getValue() == null) {
					return;
				}
				
				try {
					App.getMistakes().getItems().clear();
					App.getMistakes().getItems().add(Mistake.BLANK);
					hint.delete(0, hint.length());
					questions = QFile.loadFile("quizes/" + App.getSelector().getValue() + ".txt");
					score = 0;
					totalScore = questions.size();
					App.updateScore(score, totalScore);
					q = (int) (Math.random() * questions.size());
					App.setText(questions.get(q).getQuestion(App.isEnglish())[0]);
				} catch (IndexOutOfBoundsException error) {
					JOptionPane.showMessageDialog(null, App.getSelector().getValue() + ".txt " + "is empty");
				}
				
			}
			
			if(e.getSource().equals(App.getHelpBtn())) {
				
				try {
					App.sendHeld(questions.get(q).getQuestion(!App.isEnglish())[0]);
					score -= 1;
					App.updateScore(score, totalScore);
					
				} catch (Exception ex) {
					App.sendHeld("I can't help you");
				}
				
			}
			
			if(e.getSource().equals(App.getHintBtn())) {
				
				try {
					hint.append(questions.get(q).getQuestion(!App.isEnglish())[0].charAt(hint.length()));
					App.sendHint(hint.toString());
				} catch (IndexOutOfBoundsException | NullPointerException e1) {
//					Deliberately Nothing
				}
				
			}
			
			if(e.getSource().equals(App.getSkipButton())) {
				
				hint.delete(0, hint.length());
				App.sendHeld(" ");
				App.sendHint(" ");
				
				
				int newQ = (int) (Math.random() * questions.size());
				while(newQ == q) {
					newQ = (int) (Math.random() * questions.size());
				}
				
				q = newQ;
				App.setText(questions.get(q).getQuestion(App.isEnglish())[0]);
				
			}
			
		}
		
	}
	
//	KeyHandler
	private class KeyHandler implements EventHandler<KeyEvent> {

		@Override
		public void handle(KeyEvent e) {
			
			if(e.getCode().equals(KeyCode.ENTER)) {		
				nextQuestion();
			}
			
		}
	
	}
	
	private void nextQuestion() {
		
		if(questions == null) {
			JOptionPane.showMessageDialog(null, "Please load a quiz");
			return;
		}
			
		try {
			
			String userInput = App.getInput().getText();
			
			if(questions.get(q).anyCorrect(!App.isEnglish(), userInput)) {
        //CORRECT

			String[] answers = questions.get(q).getQuestion(!App.isEnglish());
			if(userInput.equalsIgnoreCase(answers[0]) || userInput.equalsIgnoreCase(answers[1]) || userInput.equal) {

				
				hint.delete(0, hint.length());
				score++;
				App.updateScore(score, totalScore);
				App.sendHeld(" ");
				App.sendHint(" ");
				questions.remove(q);
				
				q = (int) (Math.random() * questions.size());
				App.setText(questions.get(q).getQuestion(App.isEnglish())[0]);
			
			} else {
//				WRONG
				
				if(App.getMistakes().getItems().get(0).equals(Mistake.BLANK)) {
					App.getMistakes().getItems().clear();
				}
				App.addMistake(userInput, questions.get(q).getQuestion(!App.isEnglish())[0]);
				
			}
			
			App.getInput().clear();
		
		} catch(IndexOutOfBoundsException e) {
			
			App.setText("Quiz over");
		
		}
	
	}
	
//	private boolean getAllPossible(boolean isEnglish) {
//		
////		String[] answers = questions.get(q).getQuestion(!App.isEnglish()).split(",");
//		
//		return isEnglish;
//		
//	}

//	CLASS GETTERS
	public ButtonHandler getButtonHandler() {
		return buttonHandler;
	}

	public KeyHandler getKeyHandler() {
		return keyHandler;
	}
	
}
