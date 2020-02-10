package com.ss.training.lms.menu;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.ss.training.lms.entity.Borrower;
import com.ss.training.lms.entity.Branch;
import com.ss.training.lms.entity.Copies;
import com.ss.training.lms.entity.Loans;
import com.ss.training.lms.service.AdministratorService;

public class BorrowerMenu {
	private Scanner consoleInput;
	private AdministratorService admin;

	public BorrowerMenu(Scanner consoleInput, AdministratorService admin) {
		this.admin = admin;
		this.consoleInput = consoleInput;
	}

	private void returnBook(Borrower borrower, Branch currentBranch) {
		int decision = 0;
		int index = 0;
		List<Loans> loans = null;
		do {
			System.out.println("Which loan are you returning a book for?");
			if (loans == null)
				loans = admin.readLoans(currentBranch.getBranchId(), borrower.getCardNo());
			for (Loans loan : loans) {
				System.out.println(
						++index + ". " + admin.readBookById(loan.getBookId()).getTitle() + "   " + loan.getDateOut());
			}
			System.out.println(++index + ". Return to Branch menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
				if (decision > index || decision < 0) {
					System.out.println("Please input a valid number");
					continue;
				}
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
				consoleInput.nextLine();
				continue;
			}
			if (decision == index)
				return;
			break;
		} while (true);
		Loans loan = admin.readLoans(currentBranch.getBranchId(), borrower.getCardNo(),
				loans.get(decision - 1).getBookId());
		loan.setDateIn(new java.sql.Date(new java.util.Date().getTime()));
		admin.updateLoans(loan);
		Copies copies = admin.readCopyById(currentBranch.getBranchId(), loans.get(decision - 1).getBookId());
		copies.setNoOfCopies(copies.getNoOfCopies() + 1);
		admin.updateCopies(copies);
		System.out.println("Thank you for returning your book!");
	}

	private void checkoutBook(Borrower borrower, Branch currentBranch) {
		int decision = 0;
		int index = 0;
		List<Copies> copies = admin.readCopiesById(currentBranch.getBranchId());
		do {
			for (Copies copy : copies) {
				System.out.println(++index + ". " + admin.readBookById(copy.getBookId()).getTitle() + "   "
						+ copy.getNoOfCopies());
			}
			System.out.println(++index + ". Return to Branch menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
				if (decision > index || decision < 0) {
					System.out.println("Please input a valid number");
					continue;
				}
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
				consoleInput.nextLine();
				continue;
			}
			if (decision == index)
				return;
			break;
		} while (true);
		Copies copy = copies.get(decision - 1);
		copy.setNoOfCopies(copy.getNoOfCopies() + 1);
		admin.updateCopies(copy);
		Loans loan = new Loans();
		loan.setBookId(copy.getBookId());
		loan.setBranchId(copy.getBranchId());
		loan.setCardNo(borrower.getCardNo());
		loan.setDateOut(new java.sql.Date(new java.util.Date().getTime()));
		loan.setDueDate(new java.sql.Date(new java.util.Date().getTime() + 604800000L));
		admin.saveLoans(loan);
		System.out.println("Remember to return your book by: " + loan.getDueDate());
	}

	public void start() {
		int decision = 0;
		HashMap<Integer, Borrower> borrowers = (HashMap<Integer, Borrower>) admin.readBorrower().stream()
				.collect(Collectors.toMap((Borrower b) -> b.getCardNo(), (Borrower b) -> b));
		do {
			System.out.println("Enter your card number, or 0 to return to main menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
				if (decision < 0) {
					System.out.println("Please input a valid number");
					continue;
				}
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
				consoleInput.nextLine();
				continue;
			}
			if (borrowers.containsKey(decision))
				break;
			else if (decision == 0)
				return;
			else
				System.out.println("Incorrect card number, please try again");
		} while (true);
		Borrower borrower = borrowers.get(decision);
		List<Branch> branches = admin.readBranch();
		int index = 0;
		do {
			System.out.println("What branch are you currently located?");
			for (Branch brch : branches) {
				System.out.println(++index + ". " + brch.getBranchName());
			}
			System.out.println(++index + ". Return to main menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
				if (decision > index || decision < 0) {
					System.out.println("Please input a valid number");
					continue;
				}
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
				consoleInput.nextLine();
				continue;
			}
			if (decision == index) {
				return;
			}
			break;
		} while (true);
		Branch currentBranch = branches.get(decision - 1);
		do {
			System.out.println("What would you like to do?");
			System.out.println("1. Check out a book");
			System.out.println("2. Return a book");
			System.out.println("3. Return to main menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
				if (decision < 1 || decision > 3) {
					System.out.println("Please input a valid number");
					continue;
				}
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
				consoleInput.nextLine();
				continue;
			}
			break;
		} while (true);
		if (decision == 1)
			checkoutBook(borrower, currentBranch);
		else if (decision == 2)
			returnBook(borrower, currentBranch);
		else
			return;
	}

}
