package com.ss.training.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Genre implements Serializable{
	private static final long serialVersionUID = -623987253220433559L;
	private Integer genreId;
	private String genreName;
	private List<Book> books;
	public Integer getGenreId() {
		return genreId;
	}
	public void setGenreId(Integer genreId) {
		this.genreId = genreId;
	}
	public String getGenreName() {
		return genreName;
	}
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
