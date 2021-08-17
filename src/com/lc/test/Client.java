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
		System.out.println("程序结束！");
	}

	//菜单
	private static void main() throws Exception {
		System.out.println("欢迎使用在线迷你TXT小说管理器");
		System.out.println("---------------------------------------");
		System.out.println("1:登录");
		System.out.println("2:注册");
		System.out.println("3:退出");
		System.out.println("---------------------------------------");
		System.out.print("请选择:");
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
			System.out.println("欢迎下次使用!");
			System.out.println("****************************************\n");
			break;
		default:
			System.out.println("输入错误!");
			main();
			break;
		}
	}

	//注册
	private static void zc() throws Exception {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("当前操作:用户注册");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("请输入登录名:");
		String name = input.next();
		System.out.print("请输入密码:");
		String pwd = input.next();
		System.out.print("请再次输入密码:");
		String pwd1 = input.next();
		if (Objects.equals(pwd, pwd1)) {
			User u = new User(name, pwd);
			data.setUser(u);
			data.setFlag("登录");
			ClientService cs = new ClientService();
			cs.writer(data);
			Data d = cs.read();
			if (Objects.equals(d.getFlag(), "成功")) {
				System.out.println("****************************************");
				System.out.println("用户已存在!");
				System.out.println("****************************************\n");
				zc();
			} else {
				data.setFlag("注册");
				ClientService cs1 = new ClientService();
				cs1.writer(data);
				Data d1 = cs1.read();
				if (Objects.equals(d1.getFlag(), "成功")) {
					System.out.println("****************************************");
					System.out.println("用户注册成功请先登录!");
					System.out.println("****************************************\n");
					locad();
				} else {
					System.out.println("****************************************");
					System.out.println("用户注册失败,请重新注册!");
					System.out.println("****************************************\n");
					zc();
				}
			}
		} else {
			System.out.println("****************************************");
			System.out.println("两次密码输入不一致，请重新注册");
			System.out.println("****************************************\n");
			zc();
		}
	}

	//登录
	private static void locad() throws Exception {
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("当前操作:用户登录");
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("请输入登录名:");
		String name = input.next();
		System.out.print("请输入密码:");
		String pwd = input.next();
		User u = new User(name, pwd);
		data.setUser(u);
		data.setFlag("登录");
		ClientService cs = new ClientService();
		cs.writer(data);
		Data d = cs.read();
		if (Objects.equals(d.getFlag(), "成功")) {
			System.out.println("****************************************");
			System.out.println("登录成功");
			System.out.println("****************************************\n");
			Story();
		} else {
			System.out.println("****************************************");
			System.out.println("登录失败");
			System.out.println("****************************************\n");
			locad();
		}

	}

	//小说菜单
	private static void Story() throws Exception {
		System.out.println("---------------------------------------");
		System.out.println("0.返回上级菜单:");
		data.setFlag("小说菜单");
		ClientService cs = new ClientService();
		cs.writer(data);
		Data d = cs.read();
		List<Book> list = d.getBookList();
		for (int i = 0; i < list.size(); i++) {
			System.out.println((i + 1) + "." + list.get(i).getType());
		}
		System.out.println("---------------------------------------");
		System.out.print("请选择：");
		int xz = input.nextInt();
		if (xz == 0) {
			locad();
		}
		data.setBook(list.get(xz - 1));
		data.setFlag("小说列表");
		xslb(data);
	}

	//小说阅读和小说
	private static void xslb(Data data2) throws Exception {
		ClientService cs1 = new ClientService();
		cs1.writer(data2);
		Data d1 = cs1.read();
		List<Book> list1 = d1.getBookList();
		System.out.println("----------------------TXT小说列表-------------------");
		System.out.println("序号\t名称\t作者\t\t简介");
		for (int i = 0; i < list1.size(); i++) {
			Book b = list1.get(i);
			System.out.println((i + 1) + "\t" + b.getName() + "\t" + b.getAuthor() + "\t\t" + b.getDescription());

		}
		System.out.println("----------------------小说列表结束-------------------");
		System.out.print("阅读和下载请选择文件序号，上传TXT请输入-1，返回请输入0:");
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
			data.setFlag("小说详情");
			xsxq(data);
			break;
		}
	}

	//上传
	private static void sc(Data d1) throws Exception {
		System.out.print("请输入小说名:");
		String name=input.next();
		System.out.print("请输入作者:");
		String author=input.next();
		System.out.print("请输入简介:");
		String jj=input.next();
		System.out.print("请输入上传的txt:");
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
		d1.setFlag("上传");
		d1.setBook(bb);
		ClientService cs1 = new ClientService();
		cs1.writer(d1);
		Data d2 = cs1.read();
		if(d2.getFlag().equals("成功")) {
			System.out.println("****************************************");
			System.out.println("小说上传成功");
			System.out.println("****************************************\n");
		}else {
			System.out.println("****************************************");
			System.out.println("小说上传失败");
			System.out.println("****************************************\n");
		}
		System.out.print("继续上传按1返回任意:");
		int dfg=input.nextInt();
		if(dfg==1) {
			sc(d1);
		}else {
			xslb(d1);
		}
	}

	//阅读
	private static void xsxq(Data data2) throws Exception {
		System.out.println("---------------------------------------");
		System.out.println("0.返回上一级菜单");
		System.out.println("1.在线阅读");
		System.out.println("2.下载TXT");
		System.out.println("---------------------------------------");
		System.out.print("请选择");
		int xz = input.nextInt();
		switch (xz) {
		case 0:
			data.setBook(data2.getBook());
			data.setFlag("小说列表");
			xslb(data);
			break;
		case 1:
			ClientService cs1 = new ClientService();
			cs1.writer(data2);
			Data d1 = cs1.read();
			System.out.println("*************************");
			System.out.println("当前操作-阅读" + d1.getBook().getName() + "\n");
			System.out.println("<<" + d1.getBook().getName() + ">> 作者:" + d1.getBook().getAuthor() + "\n");
			System.out.println("文案:");
			System.out.println(d1.getBook().getDescription());
			System.out.println("  " + d1.getBook().getContent());
			System.out.println("*************************");
			System.out.print("继续显示列表输入1，下载TXT请输入2，下一页输入3:");
			int xz1 = input.nextInt();
			switch (xz1) {
			case 1:
				xsxq(data2);
				break;
			case 2:
				d1.setFlag("下载");
				xz(d1);
				break;
			case 3:
				d1.setReadPage(d1.getReadPage()+1);
				d1.setFlag("下一页");
				xyy(d1);
				break;
			default:
				System.out.println("*******************************************");
				System.out.println("输入错误");
				System.out.println("*******************************************\n");
				xsxq(data2);
				break;
			}
			break;
		case 2:
			ClientService cs2 = new ClientService();
			cs2.writer(data2);
			Data d2 = cs2.read();
			d2.setFlag("下载");
			xz(d2);
			break;
		default:
			System.out.println("*******************************************");
			System.out.println("输入错误");
			System.out.println("*******************************************\n");
			xsxq(data2);
			break;
		}

	}

	//下载
	private static void xz(Data d1) throws Exception {
		ClientService cs2 = new ClientService();
		cs2.writer(d1);
		Data d2 = cs2.read();
		System.out.println("*******************************************");
		System.out.println("下载后的文件名:"+d2.getBook().getName()+".txt");
		System.out.println("下载后的路径是:"+d2.getDownloadPath());
		boolean pd=false;
		try {
			OutputStream out=new FileOutputStream(d2.getDownloadPath()+d2.getBook().getName()+".txt");
			out.write(d2.getBook().getContent().getBytes());
			out.flush();
			out.close();
			pd=true;
		} catch (FileNotFoundException e) {
			System.out.println("文件找不到");
		} catch (IOException e) {
			System.out.println("IO错误");
		}
		System.out.println("文件下载结果"+pd);
		System.out.println("*******************************************\n");
		data.setBook(d1.getBook());
		data.setFlag("小说列表");
		xslb(data);
	}

	//分页阅读
	private static void xyy(Data data2) throws Exception {
		ClientService cs1 = new ClientService();
		cs1.writer(data2);
		Data d1 = cs1.read();
		System.out.println("*************************");
		System.out.println("  " + d1.getBook().getContent());
		System.out.println("*************************");
		System.out.print("0.返回，任意字符下一页:");
		int xz1 = input.nextInt();
		switch (xz1) {
		case 0:
			data.setBook(data2.getBook());
			data.setFlag("小说详情");
			xsxq(data);
			break;
		default:
			d1.setReadPage(d1.getReadPage()+1);
			d1.setFlag("下一页");
			xyy(d1);
			break;
		}
		
	}

}
