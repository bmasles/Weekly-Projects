/**
 * 
 */
package com.SmoothStack.WeeklyProjects.ProjectOne;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Week 1 Project
 * Basic Library System using files
 * 
 * @author Burke Masles
 *
 */
public class LibraryManagementSystem {
	
	List<String> books = null, publishers = null, authors = null;
	Scanner consoleInput = new Scanner(System.in);
	
	LibraryManagementSystem(String folderName) {
		try {
			books = Files.readAllLines(Paths.get(folderName + "/books"));
			publishers = Files.readAllLines(Paths.get(folderName + "/publishers"));
			authors = Files.readAllLines(Paths.get(folderName + "/authors"));
		} catch (IOException e) {
			System.out.println("Failed to read the file: " + e.getMessage());
			System.exit(0);
		}
	}
	void mainMenu() {
		int decision = 0;
		do {
			System.out.println("Please select what you would like to modify:");
			System.out.println("1. Books");
			System.out.println("2. Authors");
			System.out.println("3. Publishers");
			System.out.println("4. Save and Exit");
			System.out.println("5. Exit without Saving");
			try {
				decision = consoleInput.nextInt();
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
			}
		} while (decision != 4 && decision != 5);
		if (decision == 5) {
			// TODO write back out to all the files
		}
		consoleInput.close();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LibraryManagementSystem lms = new LibraryManagementSystem("resources");
		lms.mainMenu();
	}

}


//System.out.println("Failed to read the file");
//System.exit(0);