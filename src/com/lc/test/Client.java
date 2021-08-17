package com.lc.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.lc.entity.Book;
import com.lc.entity.Data;
import com.lc.entity.User;

public class Client {
	private static Data data = new Data();
	private static Scanner input = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		main();
		System.out.println("���������");
	}

	//�˵�
	private static void main() throws Exception {
		System.out.println("��ӭʹ����������TXTС˵������");
		System.out.println("---------------------------------------");
		System.out.println("1:��¼");
		System.out.println("2:ע��");
		System.out.println("3:�˳�");
		System.out.println("---------------------------------------");
		System.out.print("��ѡ��:");
		int xz = input.nextInt();
		switch (xz) {
		case 1:
			locad();
			break;
		case 2:
			zc();
			break;
		case 3:
			System.out.println("****************************************");
			System.out.println("��ӭ�´�ʹ��!");
			System.out.println("****************************************\n");
			break;
		default:
			System.out.println("�������!");
			main();
			break;
		}
	}

	//ע��
	private static void zc() throws Exception {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("��ǰ����:�û�ע��");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("�������¼��:");
		String name = input.next();
		System.out.print("����������:");
		String pwd = input.next();
		System.out.print("���ٴ���������:");
		String pwd1 = input.next();
		if (Objects.equals(pwd, pwd1)) {
			User u = new User(name, pwd);
			data.setUser(u);
			data.setFlag("��¼");
			ClientService cs = new ClientService();
			cs.writer(data);
			Data d = cs.read();
			if (Objects.equals(d.getFlag(), "�ɹ�")) {
				System.out.println("****************************************");
				System.out.println("�û��Ѵ���!");
				System.out.println("****************************************\n");
				zc();
			} else {
				data.setFlag("ע��");
				ClientService cs1 = new ClientService();
				cs1.writer(data);
				Data d1 = cs1.read();
				if (Objects.equals(d1.getFlag(), "�ɹ�")) {
					System.out.println("****************************************");
					System.out.println("�û�ע��ɹ����ȵ�¼!");
					System.out.println("****************************************\n");
					locad();
				} else {
					System.out.println("****************************************");
					System.out.println("�û�ע��ʧ��,������ע��!");
					System.out.println("****************************************\n");
					zc();
				}
			}
		} else {
			System.out.println("****************************************");
			System.out.println("�����������벻һ�£�������ע��");
			System.out.println("****************************************\n");
			zc();
		}
	}

	//��¼
	private static void locad() throws Exception {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("��ǰ����:�û���¼");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("�������¼��:");
		String name = input.next();
		System.out.print("����������:");
		String pwd = input.next();
		User u = new User(name, pwd);
		data.setUser(u);
		data.setFlag("��¼");
		ClientService cs = new ClientService();
		cs.writer(data);
		Data d = cs.read();
		if (Objects.equals(d.getFlag(), "�ɹ�")) {
			System.out.println("****************************************");
			System.out.println("��¼�ɹ�");
			System.out.println("****************************************\n");
			Story();
		} else {
			System.out.println("****************************************");
			System.out.println("��¼ʧ��");
			System.out.println("****************************************\n");
			locad();
		}

	}

	//С˵�˵�
	private static void Story() throws Exception {
		System.out.println("---------------------------------------");
		System.out.println("0.�����ϼ��˵�:");
		data.setFlag("С˵�˵�");
		ClientService cs = new ClientService();
		cs.writer(data);
		Data d = cs.read();
		List<Book> list = d.getBookList();
		for (int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + "." + list.get(i).getType());
		}
		System.out.println("---------------------------------------");
		System.out.print("��ѡ��");
		int xz = input.nextInt();
		if (xz == 0) {
			locad();
		}
		data.setBook(list.get(xz - 1));
		data.setFlag("С˵�б�");
		xslb(data);
	}

	//С˵�Ķ���С˵
	private static void xslb(Data data2) throws Exception {
		ClientService cs1 = new ClientService();
		cs1.writer(data2);
		Data d1 = cs1.read();
		List<Book> list1 = d1.getBookList();
		System.out.println("----------------------TXTС˵�б�-------------------");
		System.out.println("���\t����\t����\t\t���");
		for (int i = 0; i < list1.size(); i++) {
			Book b = list1.get(i);
			System.out.println((i + 1) + "\t" + b.getName() + "\t" + b.getAuthor() + "\t\t" + b.getDescription());

		}
		System.out.println("----------------------С˵�б����-------------------");
		System.out.print("�Ķ���������ѡ���ļ���ţ��ϴ�TXT������-1������������0:");
		int xz = input.nextInt();
		switch (xz) {
		case -1:
			sc(d1);
			break;
		case 0:
			Story();
			break;
		default:
			data.setBook(list1.get(xz - 1));
			data.setFlag("С˵����");
			xsxq(data);
			break;
		}
	}

	//�ϴ�
	private static void sc(Data d1) throws Exception {
		System.out.print("������С˵��:");
		String name=input.next();
		System.out.print("����������:");
		String author=input.next();
		System.out.print("��������:");
		String jj=input.next();
		System.out.print("�������ϴ���txt:");
		String path=input.next();
		File f = new File(path);
		InputStream i = new FileInputStream(f);
		byte[] b = new byte[4096];
		int len = -1;
		StringBuffer sb = new StringBuffer();
		while((len = i.read(b))!=-1) {
			sb.append(new String(b, 0, len));
		}
		i.close();
		Book bb=new Book();
		bb.setType(d1.getBook().getType());
		bb.setName(name);
		bb.setAuthor(author);
		bb.setDescription(jj);
		bb.setContent(sb.toString());
		d1.setFlag("�ϴ�");
		d1.setBook(bb);
		ClientService cs1 = new ClientService();
		cs1.writer(d1);
		Data d2 = cs1.read();
		if(d2.getFlag().equals("�ɹ�")) {
			System.out.println("****************************************");
			System.out.println("С˵�ϴ��ɹ�");
			System.out.println("****************************************\n");
		}else {
			System.out.println("****************************************");
			System.out.println("С˵�ϴ�ʧ��");
			System.out.println("****************************************\n");
		}
		System.out.print("�����ϴ���1��������:");
		int dfg=input.nextInt();
		if(dfg==1) {
			sc(d1);
		}else {
			xslb(d1);
		}
	}

	//�Ķ�
	private static void xsxq(Data data2) throws Exception {
		System.out.println("---------------------------------------");
		System.out.println("0.������һ���˵�");
		System.out.println("1.�����Ķ�");
		System.out.println("2.����TXT");
		System.out.println("---------------------------------------");
		System.out.print("��ѡ��");
		int xz = input.nextInt();
		switch (xz) {
		case 0:
			data.setBook(data2.getBook());
			data.setFlag("С˵�б�");
			xslb(data);
			break;
		case 1:
			ClientService cs1 = new ClientService();
			cs1.writer(data2);
			Data d1 = cs1.read();
			System.out.println("*************************");
			System.out.println("��ǰ����-�Ķ�" + d1.getBook().getName() + "\n");
			System.out.println("<<" + d1.getBook().getName() + ">> ����:" + d1.getBook().getAuthor() + "\n");
			System.out.println("�İ�:");
			System.out.println(d1.getBook().getDescription());
			System.out.println("  " + d1.getBook().getContent());
			System.out.println("*************************");
			System.out.print("������ʾ�б�����1������TXT������2����һҳ����3:");
			int xz1 = input.nextInt();
			switch (xz1) {
			case 1:
				xsxq(data2);
				break;
			case 2:
				d1.setFlag("����");
				xz(d1);
				break;
			case 3:
				d1.setReadPage(d1.getReadPage()+1);
				d1.setFlag("��һҳ");
				xyy(d1);
				break;
			default:
				System.out.println("*******************************************");
				System.out.println("�������");
				System.out.println("*******************************************\n");
				xsxq(data2);
				break;
			}
			break;
		case 2:
			ClientService cs2 = new ClientService();
			cs2.writer(data2);
			Data d2 = cs2.read();
			d2.setFlag("����");
			xz(d2);
			break;
		default:
			System.out.println("*******************************************");
			System.out.println("�������");
			System.out.println("*******************************************\n");
			xsxq(data2);
			break;
		}

	}

	//����
	private static void xz(Data d1) throws Exception {
		ClientService cs2 = new ClientService();
		cs2.writer(d1);
		Data d2 = cs2.read();
		System.out.println("*******************************************");
		System.out.println("���غ���ļ���:"+d2.getBook().getName()+".txt");
		System.out.println("���غ��·����:"+d2.getDownloadPath());
		boolean pd=false;
		try {
			OutputStream out=new FileOutputStream(d2.getDownloadPath()+d2.getBook().getName()+".txt");
			out.write(d2.getBook().getContent().getBytes());
			out.flush();
			out.close();
			pd=true;
		} catch (FileNotFoundException e) {
			System.out.println("�ļ��Ҳ���");
		} catch (IOException e) {
			System.out.println("IO����");
		}
		System.out.println("�ļ����ؽ��"+pd);
		System.out.println("*******************************************\n");
		data.setBook(d1.getBook());
		data.setFlag("С˵�б�");
		xslb(data);
	}

	//��ҳ�Ķ�
	private static void xyy(Data data2) throws Exception {
		ClientService cs1 = new ClientService();
		cs1.writer(data2);
		Data d1 = cs1.read();
		System.out.println("*************************");
		System.out.println("  " + d1.getBook().getContent());
		System.out.println("*************************");
		System.out.print("0.���أ������ַ���һҳ:");
		int xz1 = input.nextInt();
		switch (xz1) {
		case 0:
			data.setBook(data2.getBook());
			data.setFlag("С˵����");
			xsxq(data);
			break;
		default:
			d1.setReadPage(d1.getReadPage()+1);
			d1.setFlag("��һҳ");
			xyy(d1);
			break;
		}
		
	}

}
