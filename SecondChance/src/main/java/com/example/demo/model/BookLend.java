package com.example.demo.model;

public class BookLend {
	private String bookId;
	private String studentId;
	public BookLend() {
		
	}
	public BookLend(String bookId, String studentId) {
		super();
		this.bookId = bookId;
		this.studentId = studentId;
	}
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	
	
	
}
