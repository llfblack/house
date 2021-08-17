package com.lc.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.lc.entity.Data;

public class ClientService  {
	public Socket s;
	public ObjectOutputStream oos;
	public ObjectInputStream ois;
	public ClientService() {
		try {
			s=new Socket("127.0.0.1",6666);
			oos=new ObjectOutputStream(s.getOutputStream());
			ois=new ObjectInputStream(s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writer(Data data) throws IOException {
		oos.writeObject(data);
		oos.flush();
	}
	
	public Data read() {
		Data d = null;
		try {
			d=(Data)ois.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	
	

}
