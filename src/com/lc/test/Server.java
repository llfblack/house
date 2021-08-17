package com.lc.test;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class Server {

	public static void main(String[] args) throws Exception {
		ServerSocket socket=new ServerSocket(6666);
		List<Socket> list=new ArrayList<Socket>();
		int count=0;
		while(true) {
			count++;
			Socket accept = socket.accept();
			list.add(accept);
			System.out.println(count+"ÒÑÁ¬½Ó");
			new Thread(new ServerService(accept, list)).start();
		}

	}

}
