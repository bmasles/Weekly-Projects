package com.ss.training.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ss.training.lms.dao.AuthorDAO;
import com.ss.training.lms.dao.BookDAO;
import com.ss.training.lms.dao.BorrowerDAO;
import com.ss.training.lms.dao.BranchDAO;
import com.ss.training.lms.dao.CopiesDAO;
import com.ss.training.lms.dao.GenreDAO;
import com.ss.training.lms.dao.LoansDAO;
import com.ss.training.lms.dao.PublisherDAO;
import com.ss.training.lms.entity.Author;
import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.Borrower;
import com.ss.training.lms.entity.Branch;
import com.ss.training.lms.entity.Copies;
import com.ss.training.lms.entity.Genre;
import com.ss.training.lms.entity.Loans;
import com.ss.training.lms.entity.Publisher;

public class AdministratorService {

	private ConnectionUtil connUtil = new ConnectionUtil();
	
	
	public void saveAuthor(Author auth) {
		try (Connection conn = connUtil.getConnection()) {
			AuthorDAO adao = new AuthorDAO(conn);
			auth.setAuthorId(adao.addAuthor(auth));
			for (Book bk: auth.getBooks())
				adao.insertBookAuthors(auth, bk);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not save author");
		}
	}
	
	public void updateAuthor(Author auth) {
		try (Connection conn = connUtil.getConnection()) {
			AuthorDAO adao = new AuthorDAO(conn);
			adao.updateAuthor(auth);
			adao.deleteBookAuthors(auth);
			for (Book bk: auth.getBooks())
				adao.insertBookAuthors(auth, bk);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update author");
		}
	}
	
	public void deleteAuthor(Author auth) {
		try (Connection conn = connUtil.getConnection()) {
			AuthorDAO adao = new AuthorDAO(conn);
			BookDAO bdao = new BookDAO(conn);
			adao.deleteAuthor(auth);
			adao.deleteBookAuthors(auth);
			for (Book book: auth.getBooks())
				bdao.deleteBook(book);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete author");
		}
	}
	
	public List<Author> readAuthor() {
		try (Connection conn = connUtil.getConnection()) {
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readAuthors();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read author");
		}
		return null;
	}
	
	
	public void saveBook(Book book) {
		try (Connection conn = connUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			book.setBookId(bdao.addBook(book));
			for (Author aut: book.getAuthors()) 
				bdao.insertBookAuthors(aut, book);
			for (Genre gen: book.getGenres()) 
				bdao.insertBookGenres(gen, book);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Could not save book");
		}
	}
	
	public void updateBook(Book book) {
		try (Connection conn = connUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			bdao.updateBook(book);
			bdao.deleteBookAuthors(book);
			bdao.deleteBookGenres(book);
			for (Author auth: book.getAuthors())
				bdao.insertBookAuthors(auth, book);
			for (Genre gen: book.getGenres())
				bdao.insertBookGenres(gen, book);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update book");
		}
	}
	
	public void deleteBook(Book book) {
		try (Connection conn = connUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			CopiesDAO cdao = new CopiesDAO(conn);
			bdao.deleteBook(book);
			bdao.deleteBookAuthors(book);
			bdao.deleteBookGenres(book);
			cdao.deleteCopiesByBookId(book.getBookId());
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete book");
		}
	}
	
	public List<Book> readBook() {
		try (Connection conn = connUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			return bdao.readBooks();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read book");
		}
		return null;
	}
	
	public Book readBookById(Integer bookId) {
		try (Connection conn = connUtil.getConnection()) {
			BookDAO bdao = new BookDAO(conn);
			return bdao.readBookById(bookId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read book");
		}
		return null;
	}
	
	
	public void saveGenre(Genre gen) {
		try (Connection conn = connUtil.getConnection()) {
			GenreDAO gdao = new GenreDAO(conn);
			gen.setGenreId(gdao.addGenre(gen));
			for (Book bk: gen.getBooks())
				gdao.insertBookGenres(gen, bk);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not create genre");
		}
	}
	
	public void updateGenre(Genre gen) {
		try (Connection conn = connUtil.getConnection()) {
			GenreDAO gdao = new GenreDAO(conn);
			gdao.updateGenre(gen);
			gdao.deleteBookGenres(gen);
			for (Book book: gen.getBooks())
				gdao.insertBookGenres(gen, book);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update genre");
		}
	}
	
	public void deleteGenre(Genre gen) {
		try (Connection conn = connUtil.getConnection()) {
			GenreDAO gdao = new GenreDAO(conn);
			gdao.deleteGenre(gen);
			gdao.deleteBookGenres(gen);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete genre");
		}
	}
	
	public List<Genre> readGenre() {
		try (Connection conn = connUtil.getConnection()) {
			GenreDAO gdao = new GenreDAO(conn);
			return gdao.readGenre();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read genre");
		}
		return null;
	}
	
	
	public void saveBorrower(Borrower bor) {
		try (Connection conn = connUtil.getConnection()) {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			bdao.addBorrower(bor);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not save borrower");
		}
	}
	
	public void updateBorrower(Borrower bor) {
		try (Connection conn = connUtil.getConnection()) {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			bdao.updateBorrower(bor);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update borrower");
		}
	}
	
	public void deleteBorrower(Borrower bor) {
		try (Connection conn = connUtil.getConnection()) {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			bdao.deleteBorrower(bor);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete borrower");
		}
	}
	
	public List<Borrower> readBorrower() {
		try (Connection conn = connUtil.getConnection()) {
			BorrowerDAO bdao = new BorrowerDAO(conn);
			return bdao.readBorrower();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read borrower");
		}
		return null;
	}
	
	
	public void saveBranch(Branch brch) {
		try (Connection conn = connUtil.getConnection()) {
			BranchDAO bdao = new BranchDAO(conn);
			bdao.addBranch(brch);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not create branch");
		}
	}
	
	public void updateBranch(Branch brch) {
		try (Connection conn = connUtil.getConnection()) {
			BranchDAO bdao = new BranchDAO(conn);
			bdao.updateBranch(brch);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update branch");
		}
	}
	
	public void deleteBranch(Branch brch) {
		try (Connection conn = connUtil.getConnection()) {
			BranchDAO bdao = new BranchDAO(conn);
			bdao.deleteBranch(brch);
			bdao.deleteBranchCopies(brch);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete branch");
		}
	}
	
	public List<Branch> readBranch() {
		try (Connection conn = connUtil.getConnection()) {
			BranchDAO bdao = new BranchDAO(conn);
			return bdao.readBranch();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read branch");
		}
		return null;
	}
	
	
	public void saveCopies(Copies copy) {
		try (Connection conn = connUtil.getConnection()) {
			CopiesDAO cdao = new CopiesDAO(conn);
			cdao.addCopies(copy);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not create copies");
		}
	}
	
	public void updateCopies(Copies copy) {
		try (Connection conn = connUtil.getConnection()) {
			CopiesDAO cdao = new CopiesDAO(conn);
			cdao.updateCopies(copy);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update copies");
		}
	}
	
	public void deleteCopies(Copies copy) {
		try (Connection conn = connUtil.getConnection()) {
			CopiesDAO cdao = new CopiesDAO(conn);
			cdao.deleteCopies(copy);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete copies");
		}
	}
	
	public List<Copies> readCopies() {
		try (Connection conn = connUtil.getConnection()) {
			CopiesDAO cdao = new CopiesDAO(conn);
			return cdao.readCopies();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read copies");
		}
		return null;
	}
	
	public List<Copies> readCopiesById(Integer branchId) {
		try (Connection conn = connUtil.getConnection()) {
			CopiesDAO cdao = new CopiesDAO(conn);
			return cdao.readCopyById(branchId);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read copies");
		}
		return null;
	}
	
	public Copies readCopyById(Integer branchId, Integer bookId) {
		try (Connection conn = connUtil.getConnection()) {
			CopiesDAO cdao = new CopiesDAO(conn);
			return cdao.readCopyById(branchId, bookId).get(0);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read copies");
		}
		return null;
	}
	
	
	public void saveLoans(Loans ln) {
		try (Connection conn = connUtil.getConnection()) {
			LoansDAO ldao = new LoansDAO(conn);
			ldao.addLoans(ln);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not creat loans");
		}
	}
	
	public void updateLoans(Loans ln) {
		try (Connection conn = connUtil.getConnection()) {
			LoansDAO ldao = new LoansDAO(conn);
			ldao.updateLoans(ln);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update loans");
		}
	}
	
	public void deleteLoans(Loans ln) {
		try (Connection conn = connUtil.getConnection()) {
			LoansDAO ldao = new LoansDAO(conn);
			ldao.deleteLoans(ln);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not delete loan");
		}
	}
	
	public List<Loans> readLoans() {
		try (Connection conn = connUtil.getConnection()) {
			LoansDAO ldao = new LoansDAO(conn);
			return ldao.readLoans();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read loans");
		}
		return null;
	}
	
	public List<Loans> readLoans(Integer branchId, Integer cardNo) {
		try (Connection conn = connUtil.getConnection()) {
			LoansDAO ldao = new LoansDAO(conn);
			return ldao.readCurrentLoans(branchId, cardNo);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read loans");
		}
		return null;
	}
	
	public Loans readLoans(Integer branchId, Integer cardNo, Integer bookId) {
		try (Connection conn = connUtil.getConnection()) {
			LoansDAO ldao = new LoansDAO(conn);
			return ldao.readCurrentLoans(branchId, cardNo, bookId).get(0);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read loans");
		}
		return null;
	}
	
	
	public void savePublisher(Publisher pub) {
		try (Connection conn = connUtil.getConnection()) {
			PublisherDAO pdao = new PublisherDAO(conn);
			pub.setPublisherId(pdao.addPublisher(pub));
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not creat publisher");
		}
	}
	
	public void updatePublisher(Publisher pub) {
		try (Connection conn = connUtil.getConnection()) {
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.updatePublisher(pub);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not update publisher");
		}
	}
	
	public void deletePublisher(Publisher pub) {
		try (Connection conn = connUtil.getConnection()) {
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.deletePublisher(pub);
			pdao.deletePublisherBooks(pub);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Could not delete publisher");
		}
	}
	
	public List<Publisher> readPublisher() {
		try (Connection conn = connUtil.getConnection()) {
			PublisherDAO pdao = new PublisherDAO(conn);
			return pdao.readPublisher();
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Could not read publisher");
		}
		return null;
	}
}