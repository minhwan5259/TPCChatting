package kr.soldesk;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class DownloadBroker implements Runnable {

	private String address;
	private String filename;
	
	public DownloadBroker(String address, String filename) {
		super();
		this.address = address;
		this.filename = filename;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			//쓰기 스트림
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			
			URL url = new URL(address);
			InputStream is = url.openStream();
			//속도가 빠름
			BufferedInputStream input = new BufferedInputStream(is);
			
			int data;
			while((data=input.read()) != -1) {
				bos.write(data);
			}
			bos.close();
			input.close();
			System.out.println("download complete...!!");
			System.out.println(filename);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
