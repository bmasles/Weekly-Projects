package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.Publisher;

public class BookDAO extends BaseDAO<Book> {

	public BookDAO(Connection conn) {
		super(conn);
	}
	
	public void addBook(Book book) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book (title,pubId) values (?,?)", 
				new Object[] { book.getTitle(),book.getPublisher().getPublisherId() });
	}

	public void updateBook(Book book) throws SQLException, ClassNotFoundException {
		save("update tbl_book set title = ?, pubId = ? where bookId = ?", 
				new Object[]{book.getTitle(),book.getPublisher().getPublisherId(),book.getBookId()} );
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		save("delete from tbl_book where bookId = ?", new Object[] {book.getBookId()});
	}
	
	public List<Book> readBooks() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_book", null);
	}

	@Override
	List<Book> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<>();
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			b.setPublisher(new Publisher());
			b.getPublisher().setPublisherId(rs.getInt("pubId"));
			books.add(b);
		}
		return books;
	}

}
