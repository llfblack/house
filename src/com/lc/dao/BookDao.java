package com.lc.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.lc.entity.Book;
import com.lc.entity.Data;

@SuppressWarnings("all")
public class BookDao {

	public Document getDoc() {
		SAXReader sax = new SAXReader();
		Document d = null;
		try {
			d = sax.read("F:\\Workspace\\StorySys\\src\\com\\lc\\xml\\Story.xml");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}

	public Data showType() {
		Document doc = this.getDoc();
		Element e = doc.getRootElement();
		Iterator<Element> it = e.elementIterator();
		Data d = new Data();
		List<Book> list = new ArrayList<Book>();
		while (it.hasNext()) {
			Element e1 = it.next();
			Book b = new Book();
			String type = e1.attributeValue("type");
			b.setType(type);
			list.add(b);
		}
		d.setBookList(list);
		return d;
	}

	public Data showList(Data dd) {
		Document doc = this.getDoc();
		Element e = doc.getRootElement();
		Iterator<Element> it = e.elementIterator();
		Data d = new Data();
		List<Book> list = new ArrayList<Book>();
		while (it.hasNext()) {
			Element e1 = it.next();
			if (e1.attributeValue("type").equals(dd.getBook().getType())) {
				Iterator<Element> it1 = e1.elementIterator();
				while (it1.hasNext()) {
					Element e2 = it1.next();
					Book b = new Book();
					b.setType(e1.attributeValue("type"));
					b.setName(e2.elementText("name"));
					b.setAuthor(e2.elementText("author"));
					b.setDescription(e2.elementText("description"));
					b.setPath(e2.elementText("path"));
					list.add(b);
				}
				Book kl=new Book();
				kl.setType(e1.attributeValue("type"));
				d.setBook(kl);
				d.setUploadPath(e1.attributeValue("path"));
			}
		}
		d.setBookList(list);
		d.setReadPage(0);
		return d;
	}

	public Data showXq(Data data) {
		Document doc = this.getDoc();
		Element e = doc.getRootElement();
		Iterator<Element> it = e.elementIterator();
		Data d = new Data();
		while (it.hasNext()) {
			Element e1 = it.next();
			if (e1.attributeValue("type").equals(data.getBook().getType())) {
				Iterator<Element> it1 = e1.elementIterator();
				while (it1.hasNext()) {
					Element e2 = it1.next();
					if (e2.elementText("name").equals(data.getBook().getName())
							 &&e2.elementText("author").equals(data.getBook().getAuthor())) {
						Book b = new Book();
						b.setPath(e2.elementText("path"));
						b.setType(data.getBook().getType());
						b.setName(e2.elementText("name"));
						b.setAuthor(e2.elementText("author"));
						b.setDescription(e2.elementText("description"));
						String storyContent = storyContent(data.getBook().getPath(),data.getReadPage());
						b.setContent(storyContent);
						d.setBook(b);
					}
				}
			}
		}
		return d;
	}
	
	public Data xyy(Data d) {
		Book b = d.getBook();
		String s = storyContent(b.getPath(),d.getReadPage());
		b.setContent(s);
		d.setBook(b);
		return d;
	}
	
	public String storyContent(String path,long page) {
		String content="";
		try {
			File f = new File(path);
			InputStream i = new FileInputStream(f);
			byte[] b = new byte[2048];
			int len = -1;
			StringBuffer sb = new StringBuffer();
			i.skip(page*2048);
			len = i.read(b);
			sb.append(new String(b, 0, len,"UTF-8"));
			i.close();
			content=sb.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return content;
	}
	
	public Data xz(Data d) {
		try {
			File f = new File(d.getBook().getPath());
			InputStream i = new FileInputStream(f);
			byte[] b = new byte[4096];
			int len = -1;
			StringBuffer sb = new StringBuffer();
			while((len = i.read(b))!=-1) {
				sb.append(new String(b, 0, len));
			}
			i.close();
			d.getBook().setContent(sb.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		d.setDownloadPath("D:\\");
		return d;
	}
	
	public Data sc(Data d) {
		boolean pd=false;
		try {
			OutputStream out=new FileOutputStream(d.getUploadPath()+d.getBook().getName()+"txt");
			out.write(d.getBook().getContent().getBytes());
			out.flush();
			Document doc = this.getDoc();
			Element e = doc.getRootElement();
			Iterator<Element> it = e.elementIterator();
			while (it.hasNext()) {
				Element e1 = it.next();
				System.out.println("1111111111111");
				if (e1.attributeValue("type").equals(d.getBook().getType())) {
					System.out.println("22222222");
					Element e2 = e1.addElement("story");
					Element e3 = e2.addElement("name");
					e3.addText(d.getBook().getName());
					Element e4 = e2.addElement("author");
					e4.addText(d.getBook().getAuthor());
					Element e5 = e2.addElement("description");
					e5.addText(d.getBook().getDescription());
					Element e6 = e2.addElement("path");
					e6.addText(d.getUploadPath()+d.getBook().getName()+"txt");
				}
			}
			OutputFormat o=OutputFormat.createPrettyPrint();
			o.setEncoding("UTF-8");
			
			try {
				XMLWriter xw=new XMLWriter(new FileOutputStream("F:\\Workspace\\StorySys\\src\\com\\lc\\xml\\Story.xml"), o);
				xw.write(doc);
				xw.flush();
				pd=true;
			} catch (UnsupportedEncodingException | FileNotFoundException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			} catch (IOException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(pd) {
			d.setFlag("³É¹¦");
		}else {
			d.setFlag("Ê§°Ü");
		}
		return d;
	}
	

//	public static void main(String[] args) throws Exception {
//		
//	}

}
