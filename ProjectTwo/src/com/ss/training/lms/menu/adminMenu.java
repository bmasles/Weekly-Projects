package com.ss.training.lms.menu;

import java.util.Scanner;

import com.ss.training.lms.entity.Book;
import com.ss.training.lms.service.AdministratorService;

public class adminMenu {
	private Scanner consoleInput;
	private AdministratorService admin;

	public adminMenu(Scanner consoleInput, AdministratorService admin) {
		this.admin = admin;
		this.consoleInput = consoleInput;
	}

	private void bookSubMenu() {
		int decision = 0;
		do {
			System.out.println("What would you like to do?");
			System.out.println("1. Create a book");
			System.out.println("2. Update a book");
			System.out.println("3. Delete a book");
			System.out.println("4. Display all books");
			System.out.println("5. Return to admin menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
				consoleInput.nextLine();
				continue;
			}
			switch (decision) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				for (Book book: admin.readBook()) {
					System.out.println(book.getTitle() + " " + book.getAuthors()  + " " + book.getPublisher().getPublisherName()
							 + " " + book.getGenres());
				}
				break;
			case 5:
				break;
			default:
				System.out.println("Please enter a valid number");
				continue;
				}
			break;
		} while (true);
	}

	private void authorSubMenu() {

	}

	private void publisherSubMenu() {

	}

	private void genreSubMenu() {

	}

	private void branchSubMenu() {

	}

	private void borrowerSubMenu() {

	}

	public void start() {
		int decision = 0;
		do {
			System.out.println("Please select what you would like to modify:");
			System.out.println("1. Books");
			System.out.println("2. Authors");
			System.out.println("3. Publishers");
			System.out.println("4. Genres");
			System.out.println("5. Library Branch");
			System.out.println("6. Borrowers");
			System.out.println("7. Return to main menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
				consoleInput.nextLine();
				continue;
			}
			switch (decision) {
			case 1:
				bookSubMenu();
				break;
			case 2:
				authorSubMenu();
				break;
			case 3:
				publisherSubMenu();
				break;
			case 4:
				genreSubMenu();
				break;
			case 5:
				branchSubMenu();
				break;
			case 6:
				borrowerSubMenu();
				break;
			case 7:
				break;
			default:
				System.out.println("Please enter a valid number");
				continue;
			}
			break;
		} while (decision != 7);
	}

}
