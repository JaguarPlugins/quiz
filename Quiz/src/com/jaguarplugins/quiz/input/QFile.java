package com.jaguarplugins.quiz.input;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.jaguarplugins.quiz.Question;

public class QFile {

	public static ArrayList<String> getFiles() {
		
		String path = "quizes";
		
		File file = new File(path);
		
		ArrayList<String> output = new ArrayList<String>();
		for(String s : file.list()) {
			output.add(s.split("\\.")[0]);
		}
		
		return output;
		
	}
	
	public static ArrayList<Question> loadFile(String path) {
		
		ArrayList<Question> contents = new ArrayList<Question> ();
		
		try {
		
			File file = new File(path);
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
//			InputStreamReader reader = new InputStreamReader(new Input, StandardCharsets.UTF_8);
			
			String line = reader.readLine();
			while(line != null) {
				String[] data = line.split(":");
				contents.add(new Question(data[0], data[1]));
				line = reader.readLine();
			}
			
			reader.close();
			
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File could not be found");	
			return null;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "I/O Exception");
			return null;
		}
		
		return contents;
	}
	
//	TODO GET ALL FILES METHOD
	
}
