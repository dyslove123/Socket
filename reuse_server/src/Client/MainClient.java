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
		MySocket ms=new MySocket("��ʥ��");
		
		ms.Required("127.0.0.1", Server.PORT);
			// �ͻ���socketָ���������ĵ�ַ�Ͷ˿ں�
		System.out.println(ms.get);
			// ͬ������ԭ��һ��
			

	}

	

}
