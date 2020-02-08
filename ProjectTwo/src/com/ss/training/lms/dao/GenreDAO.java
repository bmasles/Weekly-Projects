package com.ss.training.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.training.lms.entity.Book;
import com.ss.training.lms.entity.Genre;

public class GenreDAO extends BaseDAO<Genre> {

	public GenreDAO(Connection conn) {
		super(conn);
	}
	
	public void addGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("insert into tbl_genre (genre_name) values (?)", 
				new Object[] {genre.getGenreName()});
	}

	public void updateGenre(Genre genre) throws SQLException, ClassNotFoundException {
		save("update tbl_genre set genre_name where genre_id = ?", 
				new Object[]{genre.getGenreName(),genre.getGenreId()} );
	}

	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("delete from tbl_genre where genre_id = ?", new Object[] {genre.getGenreId()});
	}
	
	public List<Genre> readGenre() throws ClassNotFoundException, SQLException {
		return read("select * from tbl_genre", null);
	}
	
	public void insertBookGenres(Genre genre, Book book) throws ClassNotFoundException, SQLException{
		save("insert into tbl_book_genres values(?, ?)", new Object[] {genre.getGenreId(), book.getBookId()});
	}
	
	public void deleteBookGenres(Genre genre) throws ClassNotFoundException, SQLException{
		save("delete form tbl_book_genres where genre_id = ?", new Object[] {genre.getGenreId()});
	}

	@Override
	List<Genre> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Genre> genres = new ArrayList<Genre>();
		BookDAO bdao = new BookDAO(conn);
		while (rs.next()) {
			Genre gn = new Genre();
			gn.setGenreId(rs.getInt("genre_id"));
			gn.setGenreName(rs.getString("genre_name"));
			gn.setBooks(bdao.readFirstLevel("select * from tbl_book where bookId in"
					+ "(select bookId from tbl_book_genre where genre_id = ?)", new Object[] {gn.getGenreId()}));
			genres.add(gn);
		}
		return genres;
	}

	@Override
	List<Genre> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Genre> genres = new ArrayList<Genre>();
		while (rs.next()) {
			Genre gn = new Genre();
			gn.setGenreId(rs.getInt("genre_id"));
			gn.setGenreName(rs.getString("genre_name"));
			genres.add(gn);
		}
		return genres;
	}

}
