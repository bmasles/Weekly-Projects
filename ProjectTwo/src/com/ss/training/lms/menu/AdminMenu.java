package com.ss.training.lms.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.ss.training.lms.entity.Author;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.Borrower;
import com.ss.training.lms.entity.Branch;
import com.ss.training.lms.entity.Genre;
import com.ss.training.lms.entity.Publisher;
import com.ss.training.lms.service.AdministratorService;

public class AdminMenu {
	private Scanner consoleInput;
	private AdministratorService admin;

	public AdminMenu(Scanner consoleInput, AdministratorService admin) {
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
				createBook();
				break;
			case 2:
				updateBook();
				break;
			case 3:
				deleteBook();
				break;
			case 4:
				for (Book book : admin.readBook()) {
					System.out.println(book.getTitle() + " " + book.getAuthors() + " "
							+ book.getPublisher().getPublisherName() + " " + book.getGenres());
				}
				continue;
			case 5:
				return;
			default:
				System.out.println("Please enter a valid number");
				continue;
			}
		} while (true);
	}

	private void deleteBook() {
		int decision = 0;
		int index = 0;
		List<Book> books = null;
		do {
			System.out.println("Which book would you like to delete?");
			System.out.println("NOTE: All copies of this book will be deleted from their branches");
			if (books == null)
				books = admin.readBook();
			for (Book book : books) {
				System.out.println(++index + ". " + book.getTitle());
			}
			System.out.println(++index + ". Return to book menu");
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
		admin.deleteBook(books.get(decision - 1));
		System.out.println(books.get(decision - 1) + " has been deleted");
	}

	private void updateBook() {
		int decision = 0;
		int index = 0;
		StringBuilder str = new StringBuilder();
		List<Author> authors = null;
		List<Publisher> publishers = null;
		List<Genre> genres = null;
		List<Book> books = null;
		Book book = new Book();
		do {
			System.out.println("Which book would you like to update?");
			if (books == null)
				books = admin.readBook();
			for (Book bk : books) {
				System.out.println(++index + ". " + bk.getTitle());
			}
			System.out.println(++index + ". Return to book menu");
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
			book.setBookId(books.get(decision - 1).getBookId());

			System.out.println("What is the name of the book?");
			book.setTitle(consoleInput.nextLine());
			if (book.getTitle().length() > 45) {
				System.out
						.println("Please make a name that is smaller than 45 characters long, " + "returning to menu");
				continue;
			} else if (book.getTitle().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			outerAuthor: // All the looping for the inclusion of authors to this book and the addition of
							// new ones
			do {
				System.out.println("Who is/are the author/s of this book?");
				if (authors == null)
					authors = admin.readAuthor();
				for (Author auth : authors) {
					System.out.println(++index + ". " + auth.getAuthorName());
				}
				System.out.println(++index + ". Add new author");
				System.out.println(++index + ". Return to book menu");
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
				book.setAuthors(new ArrayList<Author>());
				if (decision == index)
					return;
				if (decision == index - 1) {
					Author a = createAuthor();
					if (a == null)
						return;
					book.getAuthors().add(a);
				} else {
					book.getAuthors().add(authors.get(decision - 1));
					authors.remove(decision - 1);
				}
				do {
					System.out.println("Is there another author to add to this book? (y/n)");
					str.append(consoleInput.next());
					if (str.toString() == "y" || str.toString() == "Y")
						continue outerAuthor;
					else if (str.toString() == "n" || str.toString() == "N")
						break;
					else {
						System.out.println("Please enter either y or n as a response");
						continue;
					}
				} while (true);
				break;
			} while (true);
			outerGenre: // All the looping for the inclusion of genres to the book except cannot add
						// new ones
			do {
				System.out.println("What is/are the genre/s of this book?");
				if (genres == null)
					genres = admin.readGenre();
				for (Genre gen : genres) {
					System.out.println(++index + ". " + gen.getGenreName());
				}
				System.out.println(++index + ". Add new genre");
				System.out.println(++index + ". Return to book menu");
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
				book.setGenres(new ArrayList<Genre>());
				if (decision == index)
					return;
				if (decision == index - 1) {
					Genre g = createGenre();
					if (g == null)
						return;
					book.getGenres().add(g);
				} else {
					book.getGenres().add(genres.get(decision - 1));
					genres.remove(decision - 1);
				}
				do {
					System.out.println("Is there another genre to add to the book? (y/n)");
					str.append(consoleInput.next());
					if (str.toString() == "y" || str.toString() == "Y")
						continue outerGenre;
					else if (str.toString() == "n" || str.toString() == "N")
						break;
					else {
						System.out.println("Please enter either y or n as a response");
						continue;
					}
				} while (true);
				break;
			} while (true);
			outerPublisher: do {
				System.out.println("Who published this book?");
				if (publishers == null)
					publishers = admin.readPublisher();
				for (Publisher pub : publishers) {
					System.out.println(++index + ". " + pub.getPublisherName());
				}
				System.out.println(++index + ". Add new publisher");
				System.out.println(++index + ". Return to book menu");
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
				if (decision == index - 1) {
					Publisher p = createPublisher();
					if (p == null)
						return;
					book.setPublisher(p);
				} else {
					book.setPublisher(publishers.get(index - 1));
				}
				break;
			} while (true);
			break;
		} while (true);
		admin.updateBook(book);
		System.out.println(book.getTitle() + " has been updated!");
	}

	private void createBook() {
		Book book = new Book();
		int decision = 0;
		int index = 0;
		StringBuilder str = new StringBuilder();
		List<Author> authors = null;
		List<Publisher> publishers = null;
		List<Genre> genres = null;
		do {
			System.out.println("What is the name of the book?");
			book.setTitle(consoleInput.nextLine());
			if (book.getTitle().length() > 45) {
				System.out
						.println("Please make a name that is smaller than 45 characters long, " + "returning to menu");
				continue;
			} else if (book.getTitle().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			book.setAuthors(new ArrayList<Author>());
			outerAuthor: // All the looping for the inclusion of authors to this book and the addition of
							// new ones
			do {
				index = 0;
				str.setLength(0);
				System.out.println("Who is/are the author/s of this book?");
				if (authors == null)
					authors = admin.readAuthor();
				for (Author auth : authors) {
					System.out.println(++index + ". " + auth.getAuthorName());
				}
				System.out.println(++index + ". Add new author");
				System.out.println(++index + ". Return to book menu");
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
				if (decision == index - 1) {
					Author a = createAuthor();
					if (a == null)
						return;
					book.getAuthors().add(a);
				} else {
					book.getAuthors().add(authors.get(decision - 1));
					authors.remove(decision - 1);
				}
				do {
					System.out.println("Is there another author to add to this book? (y/n)");
					str.append(consoleInput.next());
					if (str.toString().equals("y") || str.toString().equals("Y"))
						continue outerAuthor;
					else if (str.toString().equals("n") || str.toString().equals("N"))
						break;
					else {
						System.out.println("Please enter either y or n as a response");
						continue;
					}
				} while (true);
				break;
			} while (true);
			book.setGenres(new ArrayList<Genre>());
			outerGenre: // All the looping for the inclusion of genres to the book except cannot add
						// new ones
			do {
				index = 0;
				str.setLength(0);
				System.out.println("What is/are the genre/s of this book?");
				if (genres == null)
					genres = admin.readGenre();
				for (Genre gen : genres) {
					System.out.println(++index + ". " + gen.getGenreName());
				}
				System.out.println(++index + ". Add new genre");
				System.out.println(++index + ". Return to book menu");
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
				if (decision == index - 1) {
					Genre g = createGenre();
					if (g == null)
						return;
					book.getGenres().add(g);
				} else {
					book.getGenres().add(genres.get(decision - 1));
					genres.remove(decision - 1);
				}
				do {
					System.out.println("Is there another genre to add to the book? (y/n)");
					str.append(consoleInput.next());
					if (str.toString().equals("y") || str.toString().equals("Y"))
						continue outerGenre;
					else if (str.toString().equals("n") || str.toString().equals("N"))
						break;
					else {
						System.out.println("Please enter either y or n as a response");
						continue;
					}
				} while (true);
				break;
			} while (true);
			outerPublisher: do {
				index = 0;
				str.setLength(0);
				System.out.println("Who published this book?");
				if (publishers == null)
					publishers = admin.readPublisher();
				for (Publisher pub : publishers) {
					System.out.println(++index + ". " + pub.getPublisherName());
				}
				System.out.println(++index + ". Add new publisher");
				System.out.println(++index + ". Return to book menu");
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
				if (decision == index - 1) {
					Publisher p = createPublisher();
					if (p == null)
						return;
					book.setPublisher(p);
				} else {
					book.setPublisher(publishers.get(decision - 1));
				}
				break;
			} while (true);
			admin.saveBook(book);
			System.out.println(book.getTitle() + " has been added!");
			break;
		} while (true);
	}

	private void authorSubMenu() {
		int decision = 0;
		do {
			System.out.println("What would you like to do?");
			System.out.println("1. Create an author");
			System.out.println("2. Update an author");
			System.out.println("3. Delete an author");
			System.out.println("4. Display all authors");
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
				createAuthor();
				break;
			case 2:
				updateAuthor();
				break;
			case 3:
				deleteAuthor();
				break;
			case 4:
				for (Author auth : admin.readAuthor())
					System.out.println(auth.getAuthorName() + auth.getBooks());
				continue;
			case 5:
				return;
			default:
				System.out.println("Please enter a valid number");
				continue;
			}
		} while (true);
	}

	private void deleteAuthor() {
		int decision = 0;
		int index = 0;
		List<Author> authors = null;
		do {
			System.out.println("Which author would you like to delete?");
			System.out.println("NOTE: All books that have been writen by this author will also be removed");
			if (authors == null)
				authors = admin.readAuthor();
			for (Author author : authors) {
				System.out.println(++index + ". " + author.getAuthorName());
			}
			System.out.println(++index + ". Return to Author menu");
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
		Author auth = authors.get(decision - 1);
		admin.deleteAuthor(auth);
		System.out.println(auth.getAuthorName() + " has been deleted");
	}

	private void updateAuthor() {
		int decision = 0;
		int index = 0;
		List<Author> authors = null;
		Author author = new Author();
		do {
			System.out.println("Which author would you like to update?");
			System.out.println("NOTE: All books that have been writen by this author will also be removed");
			if (authors == null)
				authors = admin.readAuthor();
			for (Author auth : authors) {
				System.out.println(++index + ". " + auth.getAuthorName());
			}
			System.out.println(++index + ". Return to Author menu");
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
			author.setAuthorId(authors.get(decision - 1).getAuthorId());

			System.out.println("What is the name of the author?");
			author.setAuthorName(consoleInput.nextLine());
			if (author.getAuthorName().length() > 45) {
				System.out
						.println("Please make a name that is smaller than 45 characters long, " + "returning to menu");
				continue;
			} else if (author.getAuthorName().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		admin.updateAuthor(author);
		System.out.println(author.getAuthorName() + " has been updated!");
	}

	private Author createAuthor() {
		Author author = new Author();
		author.setBooks(new ArrayList<Book>());
		do {
			System.out.println("What is the name of the author?");
			author.setAuthorName(consoleInput.nextLine());
			if (author.getAuthorName().length() > 45) {
				System.out
						.println("Please make a name that is smaller than 45 characters long, " + "returning to menu");
				continue;
			} else if (author.getAuthorName().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		admin.saveAuthor(author);
		System.out.println(author.getAuthorName() + " has been added!");
		return author;
	}

	private void publisherSubMenu() {
		int decision = 0;
		do {
			System.out.println("What would you like to do?");
			System.out.println("1. Create a publisher");
			System.out.println("2. Update a publisher");
			System.out.println("3. Delete a publisher");
			System.out.println("4. Display all publishers");
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
				createPublisher();
				break;
			case 2:
				updatePublisher();
				break;
			case 3:
				deletePublisher();
				break;
			case 4:
				for (Publisher pub : admin.readPublisher())
					System.out.println(pub.getPublisherName() + "  " + pub.getPublisherAddress() + "   "
							+ pub.getPublisherPhone());
				continue;
			case 5:
				return;
			default:
				System.out.println("Please enter a valid number");
				continue;
			}
		} while (true);
	}

	private void deletePublisher() {
		int decision = 0;
		int index = 0;
		List<Publisher> publishers = null;
		do {
			System.out.println("Which publisher would you like to delete?");
			System.out.println("NOTE: All books that have been writen by this publisher will also be removed");
			if (publishers == null)
				publishers = admin.readPublisher();
			for (Publisher publisher : publishers) {
				System.out.println(++index + ". " + publisher.getPublisherName());
			}
			System.out.println(++index + ". Return to Publisher menu");
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
		Publisher pub = publishers.get(decision - 1);
		admin.deletePublisher(pub);
		System.out.println(pub.getPublisherName() + " has been deleted");
	}

	private void updatePublisher() {
		int decision = 0;
		int index = 0;
		List<Publisher> publishers = null;
		Publisher publisher = new Publisher();
		do {
			System.out.println("Which publisher would you like to update?");
			if (publishers == null)
				publishers = admin.readPublisher();
			for (Publisher pub : publishers) {
				System.out.println(++index + ". " + pub.getPublisherName());
			}
			System.out.println(++index + ". Return to Publisher menu");
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
			publisher.setPublisherId(publishers.get(decision - 1).getPublisherId());

			do {
				System.out.println("What is the name of the publisher?");
				publisher.setPublisherName(consoleInput.nextLine());
				if (publisher.getPublisherName().length() > 45) {
					System.out.println("Please make a name that is smaller than 45 characters long");
					continue;
				} else if (publisher.getPublisherName().length() < 1) {
					System.out.println("Please make sure to input a name before hitting enter");
					continue;
				}
				break;
			} while (true);
			do {
				System.out.println("What is the address of the publisher?");
				publisher.setPublisherAddress(consoleInput.nextLine());
				if (publisher.getPublisherAddress().length() > 45) {
					System.out.println("Please make a name that is smaller than 45 characters long");
					continue;
				} else if (publisher.getPublisherAddress().length() < 1) {
					System.out.println("Please make sure to input a name before hitting enter");
					continue;
				}
				break;
			} while (true);
			do {
				System.out.println("What is the phone number of the publisher?");
				publisher.setPublisherPhone(consoleInput.nextLine());
				if (publisher.getPublisherPhone().length() > 45) {
					System.out.println("Please make a name that is smaller than 45 characters long");
					continue;
				} else if (publisher.getPublisherPhone().length() < 1) {
					System.out.println("Please make sure to input a name before hitting enter");
					continue;
				}
				break;
			} while (true);
			break;
		} while (true);
		admin.updatePublisher(publisher);
		System.out.println(publisher.getPublisherName() + " has been updated!");
	}

	private Publisher createPublisher() {
		Publisher pub = new Publisher();
		do {
			System.out.println("What is the name of the publisher?");
			pub.setPublisherName(consoleInput.nextLine());
			if (pub.getPublisherName().length() > 45) {
				System.out.println("Please make a name that is smaller than 45 characters long");
				continue;
			} else if (pub.getPublisherName().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		do {
			System.out.println("What is the address of the publisher?");
			pub.setPublisherAddress(consoleInput.nextLine());
			if (pub.getPublisherAddress().length() > 45) {
				System.out.println("Please make a name that is smaller than 45 characters long");
				continue;
			} else if (pub.getPublisherAddress().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		do {
			System.out.println("What is the phone number of the publisher?");
			pub.setPublisherPhone(consoleInput.nextLine());
			if (pub.getPublisherPhone().length() > 45) {
				System.out.println("Please make a name that is smaller than 45 characters long");
				continue;
			} else if (pub.getPublisherPhone().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		admin.savePublisher(pub);
		System.out.println(pub.getPublisherName() + " has been added!");
		return pub;
	}

	private void genreSubMenu() {
		int decision = 0;
		do {
			System.out.println("What would you like to do?");
			System.out.println("1. Create a genre");
			System.out.println("2. Update a genre");
			System.out.println("3. Delete a genre");
			System.out.println("4. Display all genres");
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
				createGenre();
				break;
			case 2:
				updateGenre();
				break;
			case 3:
				deleteGenre();
				break;
			case 4:
				for (Genre gen : admin.readGenre())
					System.out.println(gen.getGenreName() + gen.getBooks());
				continue;
			case 5:
				return;
			default:
				System.out.println("Please enter a valid number");
				continue;
			}
		} while (true);
	}

	private void deleteGenre() {
		int decision = 0;
		int index = 0;
		List<Genre> genres = null;
		do {
			System.out.println("Which genre would you like to delete?");
			if (genres == null)
				genres = admin.readGenre();
			for (Genre genre : genres) {
				System.out.println(++index + ". " + genre.getGenreName());
			}
			System.out.println(++index + ". Return to Genre menu");
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
		Genre gen = genres.get(decision - 1);
		admin.deleteGenre(gen);
		System.out.println(gen.getGenreName() + " has been deleted");
	}

	private void updateGenre() {
		int decision = 0;
		int index = 0;
		List<Genre> genres = null;
		Genre genre = new Genre();
		do {
			System.out.println("Which genre would you like to update?");
			if (genres == null)
				genres = admin.readGenre();
			for (Genre gen : genres) {
				System.out.println(++index + ". " + gen.getGenreName());
			}
			System.out.println(++index + ". Return to Genre menu");
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
			genre.setGenreId(genres.get(decision - 1).getGenreId());

			do {
				System.out.println("What is the name of the genre?");
				genre.setGenreName(consoleInput.nextLine());
				if (genre.getGenreName().length() > 45) {
					System.out.println(
							"Please make a name that is smaller than 45 characters long, " + "returning to menu");
					continue;
				} else if (genre.getGenreName().length() < 1) {
					System.out.println("Please make sure to input a name before hitting enter");
					continue;
				}
				break;
			} while (true);
			break;
		} while (true);
		admin.updateGenre(genre);
		System.out.println(genre.getGenreName() + " has been updated!");
	}

	private Genre createGenre() {
		Genre gen = new Genre();
		gen.setBooks(new ArrayList<Book>());
		do {
			System.out.println("What is the name of the genre?");
			gen.setGenreName(consoleInput.nextLine());
			if (gen.getGenreName().length() > 45) {
				System.out
						.println("Please make a name that is smaller than 45 characters long, " + "returning to menu");
				continue;
			} else if (gen.getGenreName().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		admin.saveGenre(gen);
		System.out.println(gen.getGenreName() + " has been added!");
		return gen;
	}

	private void branchSubMenu() {
		int decision = 0;
		do {
			System.out.println("What would you like to do?");
			System.out.println("1. Create a branch");
			System.out.println("2. Update a branch");
			System.out.println("3. Delete a branch");
			System.out.println("4. Display all branches");
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
				createBranch();
				break;
			case 2:
				updateBranch();
				break;
			case 3:
				deleteBranch();
				break;
			case 4:
				for (Branch brch : admin.readBranch())
					System.out.println(brch.getBranchName() + "   " + brch.getBranchAddress());
				continue;
			case 5:
				return;
			default:
				System.out.println("Please enter a valid number");
				continue;
			}
		} while (true);
	}

	private void deleteBranch() {
		int decision = 0;
		int index = 0;
		List<Branch> branches = null;
		do {
			System.out.println("Which Branch would you like to delete?");
			System.out.println("NOTE: All copies will be deleted from that branch");
			if (branches == null)
				branches = admin.readBranch();
			for (Branch brch : branches) {
				System.out.println(++index + ". " + brch.getBranchName());
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
		Branch brch = branches.get(decision - 1);
		admin.deleteBranch(brch);
		System.out.println(brch.getBranchName() + " has been deleted");
	}

	private void updateBranch() {
		int decision = 0;
		int index = 0;
		List<Branch> branches = null;
		Branch branch = new Branch();
		do {
			System.out.println("Which Branch would you like to update?");
			if (branches == null)
				branches = admin.readBranch();
			for (Branch brch : branches) {
				System.out.println(++index + ". " + brch.getBranchName());
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
			branch.setBranchId(branches.get(decision - 1).getBranchId());

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
			break;
		} while (true);
		admin.updateBranch(branch);
		System.out.println(branch.getBranchName() + " has been updated");
	}

	private Branch createBranch() {
		Branch brch = new Branch();
		do {
			System.out.println("What is the name of the branch?");
			brch.setBranchName(consoleInput.nextLine());
			if (brch.getBranchName().length() > 45) {
				System.out.println("Please make a name that is smaller than 45 characters long");
				continue;
			} else if (brch.getBranchName().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		do {
			System.out.println("What is the address of the branch?");
			brch.setBranchAddress(consoleInput.nextLine());
			if (brch.getBranchAddress().length() > 45) {
				System.out.println("Please make a name that is smaller than 45 characters long");
				continue;
			} else if (brch.getBranchAddress().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		admin.saveBranch(brch);
		System.out.println(brch.getBranchName() + " has been added!");
		return brch;
	}

	private void borrowerSubMenu() {
		int decision = 0;
		do {
			System.out.println("What would you like to do?");
			System.out.println("1. Create a borrower");
			System.out.println("2. Update a borrower");
			System.out.println("3. Delete a borrower");
			System.out.println("4. Display all boorrowers");
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
				createBorrower();
				break;
			case 2:
				updateBorrower();
				break;
			case 3:
				deleteBorrower();
				break;
			case 4:
				for (Borrower bor : admin.readBorrower())
					System.out.println(bor.getName() + "   " + bor.getAddress() + "   " + bor.getPhone());
				continue;
			case 5:
				return;
			default:
				System.out.println("Please enter a valid number");
				continue;
			}
		} while (true);
	}

	private void deleteBorrower() {
		int decision = 0;
		int index = 0;
		List<Borrower> borrowers = null;
		do {
			System.out.println("Which borrower would you like to delete?");
			if (borrowers == null)
				borrowers = admin.readBorrower();
			for (Borrower borrower : borrowers) {
				System.out.println(++index + ". " + borrower.getName());
			}
			System.out.println(++index + ". Return to Borrower menu");
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
		Borrower bor = borrowers.get(decision - 1);
		admin.deleteBorrower(bor);
		System.out.println(bor.getName() + " has been deleted");
	}

	private void updateBorrower() {
		int decision = 0;
		int index = 0;
		List<Borrower> borrowers = null;
		Borrower borrower = new Borrower();
		do {
			System.out.println("Which borrower would you like to delete?");
			if (borrowers == null)
				borrowers = admin.readBorrower();
			for (Borrower bor : borrowers) {
				System.out.println(++index + ". " + bor.getName());
			}
			System.out.println(++index + ". Return to Borrower menu");
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
			borrower.setCardNo(borrowers.get(decision - 1).getCardNo());

			do {
				System.out.println("What is the name of the borrower?");
				borrower.setName(consoleInput.nextLine());
				if (borrower.getName().length() > 45) {
					System.out.println("Please make a name that is smaller than 45 characters long");
					continue;
				} else if (borrower.getName().length() < 1) {
					System.out.println("Please make sure to input a name before hitting enter");
					continue;
				}
				break;
			} while (true);
			do {
				System.out.println("What is the address of the borrower?");
				borrower.setAddress(consoleInput.nextLine());
				if (borrower.getAddress().length() > 45) {
					System.out.println("Please make a name that is smaller than 45 characters long");
					continue;
				} else if (borrower.getAddress().length() < 1) {
					System.out.println("Please make sure to input a name before hitting enter");
					continue;
				}
				break;
			} while (true);
			do {
				System.out.println("What is the phone number of the borrower?");
				borrower.setPhone(consoleInput.nextLine());
				if (borrower.getPhone().length() > 45) {
					System.out.println("Please make a name that is smaller than 45 characters long");
					continue;
				} else if (borrower.getPhone().length() < 1) {
					System.out.println("Please make sure to input a name before hitting enter");
					continue;
				}
				break;
			} while (true);
			break;
		} while (true);
		admin.updateBorrower(borrower);
		System.out.println(borrower.getName() + " has been updated!");
	}

	private Borrower createBorrower() {
		Borrower bor = new Borrower();
		do {
			System.out.println("What is the name of the borrower?");
			bor.setName(consoleInput.nextLine());
			if (bor.getName().length() > 45) {
				System.out.println("Please make a name that is smaller than 45 characters long");
				continue;
			} else if (bor.getName().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		do {
			System.out.println("What is the address of the borrower?");
			bor.setAddress(consoleInput.nextLine());
			if (bor.getAddress().length() > 45) {
				System.out.println("Please make a name that is smaller than 45 characters long");
				continue;
			} else if (bor.getAddress().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		do {
			System.out.println("What is the phone number of the borrower?");
			bor.setPhone(consoleInput.nextLine());
			if (bor.getPhone().length() > 45) {
				System.out.println("Please make a name that is smaller than 45 characters long");
				continue;
			} else if (bor.getPhone().length() < 1) {
				System.out.println("Please make sure to input a name before hitting enter");
				continue;
			}
			break;
		} while (true);
		admin.saveBorrower(bor);
		System.out.println(bor.getName() + " has been added!");
		return bor;
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
