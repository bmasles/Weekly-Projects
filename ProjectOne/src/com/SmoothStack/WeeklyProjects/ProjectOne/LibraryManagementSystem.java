/**
 * 
 */
package com.SmoothStack.WeeklyProjects.ProjectOne;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Week 1 Project Basic Library System using files
 * 
 * @author Burke Masles
 *
 */

/*
 * X TODO Convert the Book file and class to accommodate IDs instead of names X
 * TODO Convert everything to Hashmap cause that is what they said and it will
 * be easier to search for IDs TODO implement delete (requires the ability to
 * search by ID) X TODO delete old entries when updating TODO get some lambda
 * functions in that bad boy
 */

public class LibraryManagementSystem {

	private ArrayList<String> bookLines = null, publisherLines = null, authorLines = null;
	private HashMap<Integer, Book> books = new HashMap<Integer, Book>();
	private HashMap<Integer, Publisher> publishers = new HashMap<Integer, Publisher>();
	private HashMap<Integer, Author> authors = new HashMap<Integer, Author>();
	private Scanner consoleInput;
	int bookId, publisherId, authorId;
	private String folderName, delim;

	LibraryManagementSystem(String folderName) {
		consoleInput = new Scanner(System.in);
		this.folderName = folderName;
		try {
			bookLines = (ArrayList<String>) Files.readAllLines(Paths.get(folderName + "/books"));
			publisherLines = (ArrayList<String>) Files.readAllLines(Paths.get(folderName + "/publishers"));
			authorLines = (ArrayList<String>) Files.readAllLines(Paths.get(folderName + "/authors"));
		} catch (IOException e) {
			System.out.println("Failed to read the file: " + e.getMessage());
			System.exit(0);
		}
		bookId = Character.getNumericValue(bookLines.get(bookLines.size() - 1).charAt(0));
		publisherId = Character.getNumericValue(publisherLines.get(publisherLines.size() - 1).charAt(0));
		authorId = Character.getNumericValue(authorLines.get(authorLines.size() - 1).charAt(0));
		delim = "[\t]";
		// books = (Hashmap<Integer,Book>)bookLines.stream()
//		.collect(Collectors.toMap(x -> x.charAt(0), x -> lambdaBook(x), null, () -> new HashMap<>()));
		for (String lines : bookLines) {
			Book bk = new Book();
			String[] words = lines.split(delim);
			bk.setId(Integer.parseInt(words[0]));
			bk.setName(words[1]);
			bk.setPublisherId(Integer.parseInt(words[2]));
			bk.setAuthorId(Integer.parseInt(words[3]));
			books.put(bk.getId(), bk);
		}
		for (String lines : authorLines) {
			Author aut = new Author();
			String[] words = lines.split(delim);
			aut.setId(Integer.parseInt(words[0]));
			aut.setName(words[1]);
			aut.setAddress(words[2]);
			authors.put(aut.getId(), aut);
		}
		for (String lines : publisherLines) {
			Publisher pub = new Publisher();
			String[] words = lines.split(delim);
			pub.setId(Integer.parseInt(words[0]));
			pub.setName(words[1]);
			pub.setAddress(words[2]);
			publishers.put(pub.getId(), pub);
		}
	}

	public Book lambdaBook(String line) {
		Book bk = new Book();
		String[] words = line.split(delim);
		bk.setId(Integer.parseInt(words[0]));
		bk.setName(words[1]);
		bk.setPublisherId(Integer.parseInt(words[2]));
		bk.setAuthorId(Integer.parseInt(words[3]));
		return bk;
	}

	public void subMenu(String type) {
		int decision = -1;
		do {
			System.out.println("1. Create");
			System.out.println("2. Read");
			System.out.println("3. Update");
			System.out.println("4. Delete");
			System.out.println("5. Return to Main Menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
			}
			if (decision == 5)
				return;
			switch (type) {
			case "book":
				switch (decision) {
				case 1:
					createBook(-1);
					break;
				case 2:
					readBook();
					break;
				case 3:
					updateBook();
					break;
				case 4:
					deleteBook();
					break;
				default:
					System.out.println("Please enter a valid number");
					continue;
				}
				break;
			case "author":
				switch (decision) {
				case 1:
					createAuthor(-1);
					break;
				case 2:
					readAuthor();
					break;
				case 3:
					updateAuthor();
					break;
				case 4:
					deleteAuthor();
					break;
				default:
					System.out.println("Please enter a valid number");
					continue;
				}
				break;
			case "publisher":
				switch (decision) {
				case 1:
					createPublisher(-1);
					break;
				case 2:
					readPublisher();
					break;
				case 3:
					updatePublisher();
					break;
				case 4:
					deletePublisher();
					break;
				default:
					System.out.println("Please enter a valid number");
					continue;
				}
				break;
			}
		} while (true);
	}

	public <T extends Entity> boolean containsEntity(ArrayList<T> entityList, int id) {
		for (T entity : entityList) {
			if (entity.getId() == id)
				return true;
		}
		return false;
	}

	public <T extends Entity> boolean containsEntity(ArrayList<T> entityList, String name) {
		for (T entity : entityList) {
			if (entity.getName().equals(name))
				return true;
		}
		return false;
	}

	private void updateBook() {
		int decision = -1;
		int returnNum = books.get(books.size() - 1).getId() + 1;
		System.out.println("Select a book you would like to update");
		readBook();
		System.out.println(returnNum + ". Return to Main Menu");
		try {
			decision = consoleInput.nextInt();
			consoleInput.nextLine();
		} catch (Exception e) {
			System.out.println("Please input a number for the selection");
		}
		if (decision > returnNum) {
			System.out.println("Please enter a valid number");
			return;
		}
		createBook(decision);
	}

	private void updateAuthor() {
		int decision = -1;
		int returnNum = authors.get(authors.size() - 1).getId() + 1;
		System.out.println("Select an author you would like to update");
		readAuthor();
		System.out.println(returnNum + ". Return to Main Menu");
		try {
			decision = consoleInput.nextInt();
			consoleInput.nextLine();
		} catch (Exception e) {
			System.out.println("Please input a number for the selection");
		}
		if (decision > returnNum) {
			System.out.println("Please enter a valid number");
			return;
		}
		createAuthor(decision);
	}

	private void updatePublisher() {
		int decision = -1;
		int returnNum = publishers.get(publishers.size() - 1).getId() + 1;
		System.out.println("Select a publisher you would like to update");
		readPublisher();
		System.out.println(returnNum + ". Return to Main Menu");
		try {
			decision = consoleInput.nextInt();
			consoleInput.nextLine();
		} catch (Exception e) {
			System.out.println("Please input a number for the selection");
		}
		if (decision > returnNum) {
			System.out.println("Please enter a valid number");
			return;
		}
		createPublisher(decision);
	}

	private void readBook() {
		for (Entry<Integer, Book> bk : books.entrySet()) {
			System.out.format("%-3d%-25s%-20s%-10s%n", bk.getValue().getId(), bk.getValue().getName(),
					bk.getValue().getAuthorId(), bk.getValue().getPublisherId());
		}
		System.out.println();
	}

	private void readAuthor() {
		for (Entry<Integer, Author> aut : authors.entrySet()) {
			System.out.format("%-3d%-20s%-20s%n", aut.getValue().getId(), aut.getValue().getName(),
					aut.getValue().getAddress());
		}
		System.out.println();
	}

	private void readPublisher() {
		for (Entry<Integer, Publisher> pub : publishers.entrySet()) {
			System.out.format("%-3d%-10s%-20s%n", pub.getValue().getId(), pub.getValue().getName(),
					pub.getValue().getAddress());
		}
		System.out.println();
	}

	private void createBook(int id) {
		Book bk = new Book();
//		StringBuilder str = new StringBuilder();
		int decision = -1;
		int returnNumAuthor = authors.get(authors.size()).getId() + 2;
		int returnNumPublisher = publishers.get(publishers.size()).getId() + 2;
		boolean finished = false;
		do {
			System.out.println("What is the name of the book?");
			bk.setName(consoleInput.nextLine());
			if (bk.getName().length() > 25) {
				System.out
						.println("Please make a name that is smaller than 25 characters long, " + "returning to menu");
				continue;
			}
			System.out.println("Who is the author of this book?");
			readAuthor();
			System.out.println((returnNumAuthor - 1) + ". New Author");
			System.out.println(returnNumAuthor + ". Return to Main Menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
			}
			if (decision > returnNumAuthor) {
				System.out.println("Please enter a valid number");
				return;
			}
			if (decision == returnNumAuthor - 1)
				decision = createAuthor(-1);
			else if (decision == returnNumAuthor) {
				System.out.println("Cancelling creation");
				return;
			}
			bk.setAuthorId(decision);
			System.out.println("Who published this book?");
			readPublisher();
			System.out.println((returnNumPublisher - 1) + ". New Publisher");
			System.out.println(returnNumPublisher + ". Return to Main Menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
			}
			if (decision > returnNumAuthor) {
				System.out.println("Please enter a valid number");
				return;
			}
			if (decision == returnNumPublisher - 1)
				decision = createPublisher(-1);
			else if (decision == returnNumPublisher) {
				System.out.println("Cancelling creation");
				continue;
			}
			bk.setPublisherId(decision);
			finished = true;
		} while (!finished);
		if (id == -1) {
			bk.setId(++bookId);
			books.put(bk.getId(), bk);
			System.out.println("Book '" + bk.getName() + "' has been added, rememeber to save!");
		} else {
			bk.setId(id);
			books.replace(id, bk);
			System.out.println("Book '" + bk.getName() + "' has been updated, rememeber to save!");
		}
	}

	private int createAuthor(int id) {
		Author aut = new Author();
		StringBuilder str = new StringBuilder();
		do {
			System.out.println("What is the name of the author?");
			str.replace(0, str.length(), consoleInput.nextLine());
			if (str.length() > 20) {
				System.out
						.println("Please input a name that is smaller than 20 characters long, " + "returning to menu");
				continue;
			}
			aut.setName(str.toString());
			System.out.println("What is the address of the author?");
			str.replace(0, str.length(), consoleInput.nextLine());
			aut.setAddress(str.toString());
			if (id == -1) {
				aut.setId(++authorId);
				authors.put(aut.getId(), aut);
				System.out.println("Author '" + aut.getName() + "' has been added, remember to save!");
			} else {
				aut.setId(id);
				authors.put(aut.getId(), aut);
				System.out.println("Author '" + aut.getName() + "' has been updated, remember to save!");
			}
		} while (false);
		return aut.getId();
	}

	private void deleteBook(int id) {
		books.remove(id);
	}

	private void deleteBook() {
		int returnNum = books.get(books.size()).getId() + 1, decision = -1;
		do {
			System.out.println("Which book would you like to delete?");
			readBook();
			System.out.println(returnNum + ". Return to Main Menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
			}
			if (decision > returnNum) {
				System.out.println("Please enter a valid number");
				return;
			}
			if (decision == returnNum - 1)
				deleteBook(decision);
			else if (decision == returnNum) {
				System.out.println("Cancelling creation");
				return;
			}
			break;
		} while (true);
	}

	private void deleteAuthor(int id) {
		books.forEach((bookId, bk) -> {
			if (bk.getAuthorId() == id)
				deleteBook(bookId);
		});
		authors.remove(id);
	}

	private void deleteAuthor() {
		int returnNum = authors.get(authors.size()).getId() + 1, decision = -1;
		do {
			System.out.println("Which author would you like to delete?");
			readAuthor();
			System.out.println(returnNum + ". Return to Main Menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
			}
			if (decision > returnNum) {
				System.out.println("Please enter a valid number");
				return;
			}
			if (decision == returnNum - 1)
				deleteAuthor(decision);
			else if (decision == returnNum) {
				System.out.println("Cancelling creation");
				return;
			}
			break;
		} while (true);
	}

	private void deletePublisher(int id) {
		books.forEach((bookId, bk) -> {
			if (bk.getPublisherId() == id)
				deleteBook(bookId);
		});
		publishers.remove(id);
	}

	private void deletePublisher() {
		int returnNum = publishers.get(publishers.size()).getId() + 1, decision = -1;
		do {
			System.out.println("Which publisher would you like to delete?");
			readPublisher();
			System.out.println(returnNum + ". Return to Main Menu");
			try {
				decision = consoleInput.nextInt();
				consoleInput.nextLine();
			} catch (Exception e) {
				System.out.println("Please input a number for the selection");
			}
			if (decision > returnNum) {
				System.out.println("Please enter a valid number");
				return;
			}
			if (decision == returnNum - 1)
				deletePublisher(decision);
			else if (decision == returnNum) {
				System.out.println("Cancelling creation");
				return;
			}
			break;
		} while (true);
	}

	private int createPublisher(int id) {
		Publisher pub = new Publisher();
		StringBuilder str = new StringBuilder();
		do {
			System.out.println("What is the name of the publisher?");
			str.replace(0, str.length(), consoleInput.nextLine());
			if (str.length() > 10) {
				System.out
						.println("Please input a name that is smaller than 10 characters long, " + "returning to menu");
				continue;
			}
			pub.setName(str.toString());
			System.out.println("What is the address of the publisher?");
			str.replace(0, str.length(), consoleInput.nextLine());
			pub.setAddress(str.toString());
			pub.setId(++publisherId);
			publishers.put(pub.getId(), pub);
			System.out.println("Publisher '" + pub.getName() + "' has been added, remember to save!");
			if (id == -1) {
				pub.setId(++publisherId);
				publishers.put(pub.getId(), pub);
				System.out.println("Publisher '" + pub.getName() + "' has been added, remember to save!");
			} else {
				pub.setId(id);
				publishers.put(pub.getId(), pub);
				System.out.println("Publisher '" + pub.getName() + "' has been updated, remember to save!");
			}
		} while (false);
		return pub.getId();
	}

	private void save() {
		StringBuilder line = new StringBuilder();
		bookLines.clear();
		authorLines.clear();
		publisherLines.clear();
		// TODO mapFlat to lines
		// bookLines = (ArrayList<String>) books.entrySet().stream().flatMap(x ->
		// x.getValue()).collect(Collectors.toList());
		for (Entry<Integer, Book> bk : books.entrySet()) {
			line.append(bk.getValue().getId() + "\t");
			line.append(bk.getValue().getName() + "\t");
			line.append(bk.getValue().getPublisherId() + "\t");
			line.append(bk.getValue().getAuthorId());
			bookLines.add(line.toString());
			line.replace(0, line.length(), "");
		}
		for (Entry<Integer, Author> aut : authors.entrySet()) {
			line.append(aut.getValue().getId() + "\t");
			line.append(aut.getValue().getName() + "\t");
			line.append(aut.getValue().getAddress());
			authorLines.add(line.toString());
			line.replace(0, line.length(), "");
		}
		for (Entry<Integer, Publisher> pub : publishers.entrySet()) {
			line.append(pub.getValue().getId() + "\t");
			line.append(pub.getValue().getName() + "\t");
			line.append(pub.getValue().getAddress());
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
				subMenu("book");
				break;
			case 2:
				subMenu("author");
				break;
			case 3:
				subMenu("publisher");
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

	public static void main(String[] args) {
		LibraryManagementSystem lms = new LibraryManagementSystem("resources");
		lms.mainMenu();
	}

}
