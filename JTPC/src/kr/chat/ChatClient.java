package kr.chat;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame implements Runnable{

	JTextArea area;//메세지 출력 공간
	JTextField input;
	JButton send_bt;
	JPanel south_p;
	
	//서버접속을 위한 객체
	Socket s;
	BufferedReader in;
	PrintWriter out;
	Thread t;

	public ChatClient() {
		area = new JTextArea();
		this.add(area);
		
		south_p = new JPanel(new BorderLayout());
		south_p.add(input = new JTextField()); //패널객체에 가운데 추가
		south_p.add(send_bt = new JButton("보내기"),
				BorderLayout.EAST);

		this.add(south_p, BorderLayout.SOUTH);
		
	
		//이벤트 감지
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				
				out.println("xx:~~X");
				
			}
		});
		
		send_bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				sendData(); //서버로 메세지 전달
				
			}
		});
		
		setBounds(100, 100, 400, 500);
		setVisible(true);
		
		connected();
		
		t = new Thread(this); //서버가 주는 메세지를 감지
		t.start();
	}
	
	//==============================================================
	//연결
	private void connected(){
		try {
			s = new Socket("221.145.64.151",3500);
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		new ChatClient();

	}

	@Override
	public void run() {
		
		while(true) {
			try {
				
				String msg = in.readLine();//대기상태
				if(msg.equals("xx:~~X"))
					break;
				if(msg !=null) {
					area.append(msg+ "\r\n");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		close();
		System.exit(0);
	}
	
	private void close() {
		try {
			if(out != null) {
				out.close();
			}
			if(in != null) {
				in.close();
			}
			if(s != null) {
				s.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private void sendData() {
		String msg = input.getText().trim();
		if(msg.length() > 0){
			
			out.println(msg);
		}
		input.setText(" ");//비우기
	}
	
	
	
}
