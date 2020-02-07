package com.ss.training.lms.entity;

import java.io.Serializable;
import java.util.List;

public class Author implements Serializable {
	private static final long serialVersionUID = 9199207081829112239L;
	private Integer authorId;
	private String authorName;
	private List<Book> books;
	
	public Integer getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Integer authorId) {
		this.authorId = authorId;
	}
	public String getAuthorName() {
		return authorName;
	}
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
