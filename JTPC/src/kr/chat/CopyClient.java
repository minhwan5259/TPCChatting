package kr.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class CopyClient {

	Socket s;
	BufferedReader in;
	PrintWriter out;
	ChatServer server;
	String ip;

	public CopyClient(Socket s, ChatServer server) {

		this.s = s;
		this.server = server;

		try {
			out = new PrintWriter(s.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			ip = s.getInetAddress().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 접속자를 기다림(대기)
		while (true) {

			try {
				String msg = in.readLine();
				if (msg.equals("xx:~~X")) {
					out.println("xx:~~X");
					server.removeClient(this);
					break;
				}
				server.sendMessage(ip + " : " + msg);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
