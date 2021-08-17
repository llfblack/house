package com.lc.entity;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable{

	private static final long serialVersionUID = -1531223852409730724L;
	
	private String flag;//状态
	private User user;
	private Book book;
	private long readPage;//第几页
	private String downloadPath;//下载地址
	private String uploadPath;//上传地址
	private List<Book> bookList;//小说列表
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public long getReadPage() {
		return readPage;
	}
	public void setReadPage(long readPage) {
		this.readPage = readPage;
	}
	public String getDownloadPath() {
		return downloadPath;
	}
	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}
	public String getUploadPath() {
		return uploadPath;
	}
	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}
	public List<Book> getBookList() {
		return bookList;
	}
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Data(String flag, User user, Book book, long readPage, String downloadPath, String uploadPath,
			List<Book> bookList) {
		super();
		this.flag = flag;
		this.user = user;
		this.book = book;
		this.readPage = readPage;
		this.downloadPath = downloadPath;
		this.uploadPath = uploadPath;
		this.bookList = bookList;
	}
	public Data() {
		super();
	}
	@Override
	public String toString() {
		return "Data [flag=" + flag + ", user=" + user + ", book=" + book + ", readPage=" + readPage + ", downloadPath="
				+ downloadPath + ", uploadPath=" + uploadPath + ", bookList=" + bookList + "]";
	}
	
	
}
