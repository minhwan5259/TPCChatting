package kr.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Project04F_MultiChatServer {

	HashMap clients;
	
	public Project04F_MultiChatServer() {
		clients = new HashMap();
		Collections.synchronizedMap(clients);//동기화를 맞추어서 thread 충돌을 막음
	}
	
	public void start() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(9100);
			System.out.println("Start Server");
			
			while(true) {
				//try {
					
					socket = serverSocket.accept();//서버지만 클라이언트 기본정보 들어옴
					//IP와 Port 번호 출력
					System.out.println(socket.getInetAddress() + " : " + socket.getPort()+ " connect");
					//Tread생성
					ServerReceiver thread = new ServerReceiver(socket);
					
					thread.start();//run 실행됨
					
				//} catch (Exception e) {
				//	e.printStackTrace();
				//}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	void sendToAll(String msg) {
		Iterator iterator = clients.keySet().iterator();
		
		while(iterator.hasNext()) { //모든 접속자에게 알림
			try {
				DataOutputStream out = (DataOutputStream) clients.get(iterator.next());
				out.writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	///=================================================
	public static void main(String[] args) {
		Project04F_MultiChatServer  mcs= new Project04F_MultiChatServer();
		mcs.start();
		
		
		
	}
	
	
	
	
	//===========================================
	//Inner Class
	class ServerReceiver extends Thread{
		Socket socket;
		DataInputStream in;
		DataOutputStream out;
		
		public ServerReceiver(Socket socket) {
			this.socket=socket;
			
			try {
				//클라이언트 입출력 스트림 생성
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
							
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//chatting start

		@Override
		public void run() {
			String name = "";
			try {
				
				name=in.readUTF(); //kim hong
				if(clients.get(name) != null) {//같은 이름이 존재
					out.writeUTF("#Already exist name : "+ name );
					out.writeUTF("#Please reconnect by other name" );
					System.out.println(socket.getInetAddress()+":"+socket.getPort()+ " disconnect!");
					in.close();
					out.close();
					socket.close();
					socket = null;
					
				}else { //같은 이름이 존재하지 않을 경우
					sendToAll("#"+name+"Join!");
					clients.put(name, out);
					while(in != null) {
						sendToAll(in.readUTF());
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(socket != null) {
					sendToAll("#"+name+"Exit!");
					clients.remove(name);
					System.out.println(socket.getInetAddress()+":"+socket.getPort()+ " disconnect!");
				}
			}
		}
		
	}
}
