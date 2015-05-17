package Client;

import java.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import javax.management.modelmbean.RequiredModelMBean;

import Server.*;

public class MainClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MySocket ms=new MySocket("胡圣托");
		
		ms.Required("127.0.0.1", Server.PORT);
			// 客户端socket指定服务器的地址和端口号
		System.out.println(ms.get);
			// 同服务器原理一样
			

	}

	

}
