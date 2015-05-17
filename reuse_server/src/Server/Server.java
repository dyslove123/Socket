package Server;

import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.naming.InitialContext;

import src.com.team8.License.License;

import com.team8.PerformanceManagement.PM;
import com.eva.me.cm.ConfigUtil;
import com.manager.failure.FailureManager;

public class Server {
	public static int PORT = 8080;  
	public Vector<Socket> socketlist;
	public void StartServer() { 

		socketlist=new Vector<Socket>();
	    ServerSocket s = null;  
	    Socket socket = null;  
	    
	    try {  
	        //设定服务端的端口号  
	        s = new ServerSocket(PORT);  
	        System.out.println("ServerSocket Start:"+s);  
	        //等待请求,此方法会一直阻塞,直到获得请求才往下走  
	        while(true){
	        	socket = s.accept(); 
	        	socketlist.add(socket);
	        	ServerThread sThread = new ServerThread();
	        	sThread.setSocket(socket);
	        	sThread.start();
	        }     
	    } catch (Exception e) {   
	    	e.printStackTrace();  
	    }finally{  
	    	System.out.println("Close.....");       
	    }  
	}
	public class ServerThread extends Thread{
		private Socket socket=null;
		BufferedReader br = null;
	    PrintWriter pw = null;
		public void setSocket(Socket s){
			this.socket=s;
		}
		
		@Override
		public void run() {
			try 
			{
				System.out.println("Connection accept socket:"+socket);   
				br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"Unicode"));  
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"Unicode")),true);  
				while(true){  
					String str;
					str = br.readLine();

					if(str.equals("END")){  
						break;  
					}
					System.out.println("Client Socket Message:"+str);  
					ProvideService(str);
				}     
				socketlist.remove(this.socket);
			} catch (IOException e) {
				e.printStackTrace();
			} 
			finally{
				try {
					pw.close();
					br.close();
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			super.run();
		}

		private void ProvideService(String str) throws IOException {
			for(int i=0;i<socketlist.size();i++)
			{
				if(socketlist.elementAt(i).isClosed())
				{
					socketlist.remove(i);
					i--;
					continue;
				}
				PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter((socketlist.elementAt(i)).getOutputStream(),"Unicode")),true);  
				pw.println(str);
				pw.flush();

			}
		}
	}
}
	