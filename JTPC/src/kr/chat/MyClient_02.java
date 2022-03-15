package kr.chat;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MyClient_02 {

	public static void main(String[] args) {
		System.out.println("입력 : ");
		Scanner scan = new Scanner(System.in);
		String msg = scan.nextLine();
		
		if((msg != null) && msg.trim().length() > 0) {
			Socket s= null;
			
			try {
				
				s = new Socket("221.145.64.151", 3001);
				
				//문자열을 보내기 위한 스트림을 준비
				PrintWriter out = new PrintWriter(s.getOutputStream());
				//서버에 문자열 보내기
				out.write(msg);
				//다음 메세지를 위하여 스트림 비우기
				out.flush();
				
				if(out != null) {
					out.close(); //스트림 닫기
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(s != null) {
						s.close();
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
	}
	
	
}
