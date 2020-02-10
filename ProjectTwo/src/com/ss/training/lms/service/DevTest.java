package com.ss.training.lms.service;

import java.sql.SQLException;
import java.util.List;

import com.ss.training.lms.entity.Copies;

public class DevTest {

	public DevTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		System.out.println(new java.sql.Date(new java.util.Date().getTime() + 604800000L));
//		ConnectionUtil conn = new ConnectionUtil();
//		AdministratorService admin = new AdministratorService();
//		List<Copies> copies = admin.readCopies();
//		Copies copy = copies.get(0);
//		copy.setNoOfCopies(100);
//		admin.updateCopies(copy);
//			BookDAO books = new BookDAO(conn.getConnection());
//			for (Book book: books.readBooks()) {
//				System.out.print(book.getBookId() + " " + book.getTitle() + " " + book.getPublisher().getPublisherId());
//				System.out.print(" (");
//				for(Author auth: book.getAuthors()) {
//					System.out.print(" " + auth.getAuthorName() + " ");
//				}
//				System.out.print(") ");
//				System.out.print(" (");
//				for(Genre gn: book.getGenres()) {
//					System.out.print(" " + gn.getGenreName() + " ");
//				}
//				System.out.print(") ");
//				System.out.println();
//			}
//			AuthorDAO authors = new AuthorDAO(conn.getConnection());
//			for (Author auth: authors.readAuthors()) {
//				System.out.println(auth.getAuthorName() + " " + auth.getAuthorId());
//			}
//			LoansDAO loans = new LoansDAO(conn.getConnection());
//			for (Loans loan : loans.readLoans()) {
//				System.out.println(loan.getBookId() + " " + loan.getBranchId()  + " " + loan.getCardNo()
//				 + " " + loan.getDateIn()  + " " + loan.getDueDate()  + " " + loan.getDateOut());
//			}
	}

}
