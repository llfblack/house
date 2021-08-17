package com.lc.test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.lc.dao.BookDao;
import com.lc.dao.UserDao;
import com.lc.entity.Data;
import com.lc.entity.User;


@SuppressWarnings("all")
public class ServerService implements Runnable { 
     private List<Socket> list=new ArrayList<Socket>();
     private Socket s;
     private static Data data;
     private ObjectInputStream ois;
 	 private ObjectOutputStream oos;
     
     public ServerService(Socket s,List<Socket> list) throws IOException {
    	 this.s=s;
    	 this.list=list;
    	 ois=new ObjectInputStream(s.getInputStream());
		 oos=new ObjectOutputStream(s.getOutputStream());
     }
     
     
	
	@Override
	public void run() {
		try {
			data=(Data)ois.readObject();
			if(Objects.equals(data.getFlag(),"��¼")) {
				if(locad(data.getUser())) {
					data.setFlag("�ɹ�");
				}else {
					data.setFlag("ʧ��");
				}
			}else if(Objects.equals(data.getFlag(),"ע��")) {
				if(zc(data.getUser())) {
					data.setFlag("�ɹ�");
				}else {
					data.setFlag("ʧ��");
				}
			}else if(Objects.equals(data.getFlag(),"С˵�˵�")) {
				data = xscd();
			}else if(Objects.equals(data.getFlag(),"С˵�б�")) {
				data=xslb(data);
			}else if(Objects.equals(data.getFlag(),"С˵����")) {
				data=xsxq(data);
			}else if(Objects.equals(data.getFlag(),"��һҳ")) {
				data=xyy(data);
			}else if(Objects.equals(data.getFlag(),"����")) {
				data=xz(data);
			}else if(Objects.equals(data.getFlag(),"�ϴ�")) {
				data=sc(data);
			}
			oos.writeObject(data);
			oos.flush();
			s.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	private Data sc(Data data2) {
		BookDao b=new BookDao();
		Data sc = b.sc(data2);
		return sc;
	}



	private Data xz(Data data2) {
		BookDao b=new BookDao();
		Data xz = b.xz(data2);
		return xz;
	}



	private Data xyy(Data data2) {
		BookDao b=new BookDao();
		Data x = b.xyy(data2);
		return x;
	}



	private Data xsxq(Data data2) {
		BookDao b=new BookDao();
		Data showXq = b.showXq(data2);
		return showXq;
	}



	private Data xslb(Data dd) {
		BookDao b=new BookDao();
		Data d = b.showList(dd);
		return d;
	}



	private Data xscd() {
		BookDao b=new BookDao();
		Data d = b.showType();
		return d;
	}



	private boolean zc(User user) {
		UserDao u=new UserDao();
		boolean pd = u.addUser(user);
		return pd;
	}



	private boolean locad(User user) {
		UserDao u=new UserDao();
		boolean pd = u.FindName(user.getName(),user.getPwd());
		return pd;
	}

}
