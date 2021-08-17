package com.lc.dao;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.lc.entity.User;

@SuppressWarnings("all")
public class UserDao {
	
	public Document getDoc() {
		SAXReader sax=new SAXReader();
		Document d=null;
		try {
			d=sax.read("F:\\Workspace\\StorySys\\src\\com\\lc\\xml\\User.xml");
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	
	public boolean FindName(String name,String pwd) {
		boolean pd=false;
		Document doc = this.getDoc();
		Element e = doc.getRootElement();
		Iterator<Element> it = e.elementIterator();
		while(it.hasNext()) {
			Element e1 = it.next();
			if(name.equals(e1.elementText("name"))&&pwd.equals(e1.elementText("pwd"))) {
				pd=true;
			}
		}
		return pd;
	}
	
	public boolean addUser(User user) {
		boolean pd=false;
		Document doc = this.getDoc();
		Element e = doc.getRootElement();
		Element e1 = e.addElement("user");
		Element e2 = e1.addElement("name");
		e2.addText(user.getName());
		Element e3 = e1.addElement("pwd");
		e3.addText(user.getPwd());
		
		OutputFormat o=OutputFormat.createPrettyPrint();
		o.setEncoding("UTF-8");
		
		try {
			XMLWriter xw=new XMLWriter(new FileOutputStream("F:\\Workspace\\StorySys\\src\\com\\lc\\xml\\User.xml"), o);
			xw.write(doc);
			xw.flush();
			xw.close();
			pd=true;
		} catch (UnsupportedEncodingException | FileNotFoundException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		} catch (IOException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		return pd;
	}
	
}
