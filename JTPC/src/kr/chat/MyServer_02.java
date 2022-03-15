package kr.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer_02 extends Thread{

	ServerSocket ss;
	
	public MyServer_02() {
	
		try {
			
			ss = new ServerSocket(3001);
		
			System.out.println("서버 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		MyServer_02 ms = new MyServer_02();
		ms.start();//채팅 시작 , run()
	}

	@Override
	public void run() {
	
		while(true) {
			try {
				
				Socket s =ss.accept();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream(),"UTF-8"));
					
				String msg = reader.readLine();//접속자가 보낸 메세지
				String ip = s.getInetAddress().getHostAddress();
				
				System.out.println(ip + " : " + msg);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}

	
}
