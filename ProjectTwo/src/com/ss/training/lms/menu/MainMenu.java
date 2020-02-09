package com.ss.training.lms.menu;

import java.util.Scanner;

import com.ss.training.lms.service.AdministratorService;

public class MainMenu {

	private Scanner consoleInput;
	private borrowerMenu bMenu;
	private librarianMenu lMenu;
	private adminMenu aMenu;
	private AdministratorService admin;

	MainMenu() {
		consoleInput = new Scanner(System.in);
		admin = new AdministratorService();
		bMenu = new borrowerMenu(consoleInput, admin);
		lMenu = new librarianMenu(consoleInput, admin);
		aMenu = new adminMenu(consoleInput, admin);
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
			default:
				System.out.println("Please enter a valid number");
				continue;
			}
			break;
		} while (decision != 4);
		consoleInput.close();
	}

	public static void main(String[] args) {
		MainMenu menu = new MainMenu();
		menu.start();
	}

}
