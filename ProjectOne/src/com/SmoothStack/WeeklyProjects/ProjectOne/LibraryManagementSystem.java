/**
 * 
 */
package com.SmoothStack.WeeklyProjects.ProjectOne;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Week 1 Project Basic Library System using files
 * 
 * @author Burke Masles
 *
 */
public class LibraryManagementSystem {

	private List<String> bookLines = null, publisherLines = null, authorLines = null;
	private ArrayList<Book> books = new ArrayList<Book>();
	private ArrayList<Publisher> publishers = new ArrayList<Publisher>();
	private ArrayList<Author> authors = new ArrayList<Author>();
	private Scanner consoleInput;
	int bookId, publisherId, authorId;
	private String folderName;

	LibraryManagementSystem(String folderName) {
		consoleInput = new Scanner(System.in);
		this.folderName = folderName;
		try {
			bookLines = Files.readAllLines(Paths.get(folderName + "/books"));
			publisherLines = Files.readAllLines(Paths.get(folderName + "/publishers"));
			authorLines = Files.readAllLines(Paths.get(folderName + "/authors"));
		} catch (IOException e) {
			System.out.println("Failed to read the file: " + e.getMessage());
			System.exit(0);
		}
		bookId = Character.getNumericValue(bookLines.get(bookLines.size() - 1).charAt(0));
		publisherId = Character.getNumericValue(publisherLines.get(publisherLines.size() - 1).charAt(0));
		authorId = Character.getNumericValue(authorLines.get(authorLines.size() - 1).charAt(0));
		String delim = "[\t]";
		for (String lines: bookLines) {
			Book bk = new Book();
			String[] words = lines.split(delim);
			bk.setId(Integer.parseInt(words[0]));
			bk.setName(words[1]);
			bk.setPublisher(words[2]);
			bk.setAuthor(words[3]);
			books.add(bk);
		}
		for (String lines: authorLines) {
			Author aut = new Author();
			String[] words = lines.split(delim);
			aut.setId(Integer.parseInt(words[0]));
			aut.setName(words[1]);
			aut.setAddress(words[2]);
			authors.add(aut);
		}
		for (String lines: publisherLines) {
			Publisher pub = new Publisher();
			String[] words = lines.split(delim);
			pub.setId(Integer.parseInt(words[0]));
			pub.setName(words[1]);
			pub.setAddress(words[2]);
			publishers.add(pub);
		}
	}

//	public <T extends Entity> void create(T entity) {
//		switch (entity.getClass().getName()) {
//		case "Book":
//			createBook();
//		}
//	}
	
	public <T extends Entity> void subMenu(T entity) {
		int decision = -1;
		System.out.println("1. Create");
		System.out.println("2. Read");
		System.out.println("3. Update");
		System.out.println("4. Delete");
		try {
			decision = consoleInput.nextInt();
			consoleInput.nextLine();
		} catch (Exception e) {
			System.out.println("Please input a number for the selection");
		}
		// TODO switch through which option and the type??
		
	}

	private void createBook() {
		Book bk = new Book();
		StringBuilder str = new StringBuilder();
		int decision = -1;
		boolean finished = false;
		do {
			System.out.println("What is the name of the book?");
			bk.setName(consoleInput.nextLine());
			System.out.println("Who is the author of this book?");
			str.replace(0, str.length(), consoleInput.nextLine());
			bk.setAuthor(str.toString());
			if (!authors.contains(new Author(0, str.toString(), ""))) {
				System.out.println("This author is not in the datebase");
				System.out.println("1. Create new author");
				System.out.println("2. Redo book creation");
				try {
					decision = consoleInput.nextInt();
					consoleInput.nextLine();
				} catch (Exception e) {
					System.out.println("Please input a number for the selection");
				}
				switch (decision) {
				case 1:
					createAuthor(str.toString());
					break;
				case 2:
					continue;
				default:
					System.out.println("Invalid number, returning to main menu without saving");
				}
				System.out.println("Author creation finished, returning to book creation");
			}
			System.out.println("Who published this book?");
			str.replace(0, str.length(), consoleInput.nextLine());
			bk.setPublisher(str.toString());
			if (!publishers.contains(new Publisher(0, str.toString(), ""))) {
				System.out.println("This publisher is not in the datebase");
				System.out.println("1. Create new publisher");
				System.out.println("2. Redo book creation");
				try {
					decision = consoleInput.nextInt();
					consoleInput.nextLine();
				} catch (Exception e) {
					System.out.println("Please input a number for the selection");
				}
				switch (decision) {
				case 1:
					createPublisher(str.toString());
					break;
				case 2:
					continue;
				default:
					System.out.println("Invalid number, returning to main menu without saving");
				}
				System.out.println("Publisher creation finished, returning to book creation");
			}
			finished = true;
		} while (!finished);
		bk.setId(++bookId);
		books.add(bk);
		System.out.println("Book '" + bk.getName() + "' has been added, rememeber to save!");
	}

	private void createAuthor() {
		Author aut = new Author();
		StringBuilder str = new StringBuilder();
		System.out.println("What is the name of the author?");
		str.replace(0, str.length(), consoleInput.nextLine());
		aut.setName(str.toString());
		System.out.println("What is the address of the author?");
		str.replace(0, str.length(), consoleInput.nextLine());
		aut.setAddress(str.toString());
		aut.setId(++authorId);
		authors.add(aut);
		System.out.println("Author '" + aut.getName() + "' has been added, remember to save!");
	}

	private void createAuthor(String name) {
		Author aut = new Author();
		StringBuilder str = new StringBuilder();
		aut.setName(name);
		System.out.println("What is the address of the author?");
		str.replace(0, str.length(), consoleInput.nextLine());
		aut.setAddress(str.toString());
		aut.setId(++authorId);
		authors.add(aut);
		System.out.println("Author '" + aut.getName() + "' has been added, remember to save!");
	}

	private void createPublisher() {
		Publisher pub = new Publisher();
		StringBuilder str = new StringBuilder();
		System.out.println("What is the name of the publisher?");
		str.replace(0, str.length(), consoleInput.nextLine());
		pub.setName(str.toString());
		System.out.println("What is the address of the publisher?");
		str.replace(0, str.length(), consoleInput.nextLine());
		pub.setAddress(str.toString());
		pub.setId(++publisherId);
		publishers.add(pub);
		System.out.println("Publisher '" + pub.getName() + "' has been added, remember to save!");
	}
	
	private void createPublisher(String name) {
		Publisher pub = new Publisher();
		StringBuilder str = new StringBuilder();
		pub.setName(name);
		System.out.println("What is the address of the publisher?");
		str.replace(0, str.length(), consoleInput.nextLine());
		pub.setAddress(str.toString());
		pub.setId(++publisherId);
		publishers.add(pub);
		System.out.println("Publisher '" + pub.getName() + "' has been added, remember to save!");
	}
	
	private void save() {
		StringBuilder line = new StringBuilder();
		bookLines.clear();
		authorLines.clear();
		publisherLines.clear();
		for (Book bk: books) {
			line.append(bk.getId() + "\t");
			line.append(bk.getName() + "\t");
			line.append(bk.getPublisher() + "\t");
			line.append(bk.getAuthor());
			bookLines.add(line.toString());
			line.replace(0, line.length(), "");
		}
		for (Author aut: authors) {
			line.append(aut.getId() + "\t");
			line.append(aut.getName() + "\t");
			line.append(aut.getAddress());
			authorLines.add(line.toString());
			line.replace(0, line.length(), "");
		}
		for (Publisher pub: publishers) {
			line.append(pub.getId() + "\t");
			line.append(pub.getName() + "\t");
			line.append(pub.getAddress());
			publisherLines.add(line.toString());
			line.replace(0, line.length(), "");
		}
		try {
			Files.write(Paths.get(folderName + "/books"), bookLines);
			Files.write(Paths.get(folderName + "/publishers"), publisherLines);
			Files.write(Paths.get(folderName + "/authors"), authorLines);
		} catch (IOException e) {
			System.out.println("Failed to write the file: " + e.getMessage());
			System.exit(0);
		} catch (SecurityException e) {
			System.out.println("Failed to write, bad permissions");
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
				consoleInput.nextLine();
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
			}
			switch (decision) {
			case 1:
				// TODO Remember that the whole CRUD menu needs to go here, not create
				createBook();
				break;
			case 2:
				createAuthor();
				break;
			case 3:
				createPublisher();
				break;
			case 4:
				save();
				break;
			case 5:
				break;
			default:
				System.out.println("Please enter a valid number");
				continue;
			}
		} while (decision != 4 && decision != 5);
		System.out.println("Goodbye!");
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