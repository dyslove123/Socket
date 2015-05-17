package Client;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MySocket {
		String push;
		String get;

		public MySocket(String Message) {
			this.push = Message;
		}

		public void Required(String IP, int PORT) {
			Socket socket = null;
			BufferedReader br = null;
			PrintWriter pw = null;
			try {
				
				// 客户端socket指定服务器的地址和端口号
				socket = new Socket(IP, PORT);
				System.out.println("Socket=" + socket);
				// 同服务器原理一样
				br = new BufferedReader(new InputStreamReader(
						socket.getInputStream(),"Unicode"));

				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
						socket.getOutputStream(),"Unicode")));
				
				pw.println(push);
				pw.flush();
				get=br.readLine()+br.readLine();
				pw.println("END");
				pw.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					System.out.println("close......");
					br.close();
					pw.close();
					socket.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}