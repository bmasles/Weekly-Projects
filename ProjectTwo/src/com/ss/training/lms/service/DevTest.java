package com.ss.training.lms.service;

import java.sql.SQLException;

import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.entity.Author;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.Genre;

public class DevTest {

	public DevTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ConnectionUtil conn = new ConnectionUtil();
		try {
			BookDAO books = new BookDAO(conn.getConnection());
			for (Book book: books.readBooks()) {
				System.out.print(book.getBookId() + " " + book.getTitle() + " " + book.getPublisher().getPublisherId());
				System.out.print(" (");
				for(Author auth: book.getAuthors()) {
					System.out.print(" " + auth.getAuthorName() + " ");
				}
				System.out.print(") ");
				System.out.print(" (");
				for(Genre gn: book.getGenres()) {
					System.out.print(" " + gn.getGenreName() + " ");
				}
				System.out.print(") ");
				System.out.println();
			}
//			AuthorDAO authors = new AuthorDAO(conn.getConnection());
//			for (Author auth: authors.readAuthors()) {
//				System.out.println(auth.getAuthorName() + " " + auth.getAuthorId());
//			}
//			LoansDAO loans = new LoansDAO(conn.getConnection());
//			for (Loans loan : loans.readLoans()) {
//				System.out.println(loan.getBookId() + " " + loan.getBranchId()  + " " + loan.getCardNo()
//				 + " " + loan.getDateIn()  + " " + loan.getDueDate()  + " " + loan.getDateOut());
//			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

}
