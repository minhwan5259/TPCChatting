package kr.chat;

import java.net.ServerSocket;
import java.net.Socket;



public class MyServer_01 extends Thread{
	
	ServerSocket ss;
	
	public MyServer_01() {
		 
		try {
			ss = new ServerSocket(3000);
			
			System.out.println("서버 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void run() {
		//무한반복으로 서버가 항상 클라이언트 접속을 기다리는 상태
		while(true) {
			try {
				
				Socket s = ss.accept(); // 클라이언트 수락
				//대기중
				
				String ip = s.getInetAddress().getHostAddress();
				
				System.out.println(ip + "님이 접속");
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
		
		
		
	}
	
	public static void main(String[] args) {
	
		//프로그램 시작
		MyServer_01 ms = new MyServer_01();
		ms.start();//채팅 시작 , run()
		
		//채팅 시작
		//new MyServer_01().start();
	}
	
	
}
