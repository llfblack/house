package com.lc.entity;

import java.io.Serializable;

@SuppressWarnings("all")
public class Book implements Serializable {
	private String type;//种类
	private String name;//名称
	private String author;//作者
	private String description;//简介
	private long total;//总页数
	private String content;//内容
	private String path;//路径
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Book(String type, String name, String author, String description, long total, String content, String path) {
		super();
		this.type = type;
		this.name = name;
		this.author = author;
		this.description = description;
		this.total = total;
		this.content = content;
		this.path = path;
	}
	public Book() {
		super();
	}
	@Override
	public String toString() {
		return "Book [type=" + type + ", name=" + name + ", author=" + author + ", description=" + description
				+ ", total=" + total + ", content=" + content + ", path=" + path + "]";
	}
	
}
