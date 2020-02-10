package com.ss.training.lms.menu;

import java.util.List;
import java.util.Scanner;

import com.ss.training.lms.entity.Branch;
import com.ss.training.lms.service.AdministratorService;

public class LibrarianMenu {
	Scanner consoleInput;
	AdministratorService admin;

	public LibrarianMenu(Scanner consoleInput, AdministratorService admin) {
		this.admin = admin;
		this.consoleInput = consoleInput;
	}

	private void updateBranch(Branch branch) {
		do {
			System.out.println("What is the name of the branch?");
			branch.setBranchName(consoleInput.nextLine());
			if (branch.getBranchName().length() > 45) {
				System.out.println("Please make a name that is smaller than 45 characters long");
				continue;
			} else if (branch.getBranchName().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		do {
			System.out.println("What is the address of the branch?");
			branch.setBranchAddress(consoleInput.nextLine());
			if (branch.getBranchAddress().length() > 45) {
				System.out.println("Please make a name that is smaller than 45 characters long");
				continue;
			} else if (branch.getBranchAddress().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		admin.updateBranch(branch);
		System.out.println(branch.getBranchName() + " has been updated");
	}

	public void start() {
		int decision = 0;
		int index = 0;
		List<Branch> branches = null;
		do {
			System.out.println("Please select the branch you would like to update");
			if (branches == null)
				branches = admin.readBranch();
			for (Branch branch : branches) {
				System.out.println(++index + ". " + branch.getBranchName());
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
			if (decision == index)
				return;
			break;
		} while (true);

		Branch branch = branches.get(decision - 1);
		updateBranch(branch);
	}

}
