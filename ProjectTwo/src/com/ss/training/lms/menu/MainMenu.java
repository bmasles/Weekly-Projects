package com.ss.training.lms.menu;

import java.util.Scanner;

import com.ss.training.lms.service.AdministratorService;

public class MainMenu {

	private Scanner consoleInput;
	private BorrowerMenu bMenu;
	private LibrarianMenu lMenu;
	private AdminMenu aMenu;
	private AdministratorService admin;

	MainMenu() {
		consoleInput = new Scanner(System.in);
		admin = new AdministratorService();
		bMenu = new BorrowerMenu(consoleInput, admin);
		lMenu = new LibrarianMenu(consoleInput, admin);
		aMenu = new AdminMenu(consoleInput, admin);
	}

	public void start() {
		int decision = 0;
		do {
			System.out.println("Welcome to the Library Management System, please select your identity:");
			System.out.println("1. Borrower");
			System.out.println("2. Librarian");
			System.out.println("3. Admin");
			System.out.println("4. Exit");
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
				bMenu.start();
				break;
			case 2:
				lMenu.start();
				break;
			case 3:
				aMenu.start();
				break;
			case 4:
				break;
			default:
				System.out.println("Please enter a valid number");
				continue;
			}
		} while (decision != 4);
		System.out.println("Goodbye!");
		consoleInput.close();
	}

	public static void main(String[] args) {
		MainMenu menu = new MainMenu();
		menu.start();
	}

}
