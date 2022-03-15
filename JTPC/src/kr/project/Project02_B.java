package kr.project;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import kr.soldesk.DownloadBroker;


public class Project02_B {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url ="https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter?qt_ty=QT1";
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			
			System.out.print("[입력 -> 년(yyyy)-월(mm)-일(dd) : ");
			String bible = br.readLine();
			
			url += "&Base_de="+bible + "&bibleType=1";
			System.out.println("===========================================");
			Document doc = Jsoup.connect(url).post();//get, post
			
			Element bible_text = doc.select(".bible_text").first();
			System.out.println(bible_text.text());
			System.out.println("===========================================");
			Element bibleinfo_box = doc.select(".bibleinfo_box").first();
			System.out.println(bibleinfo_box.text());
			
			//내용
			System.out.println("===========================================");
			Elements liList = doc.select(".body_list > li");//소제목
			for(Element el : liList) {
				
			System.out.print(el.select(".num").first().text()+" : ");
			System.out.println(el.select(".info").first().text());
			}
			
			System.out.println("===========================================");
			
			/*
			//리소스 다운로드(mp3, image) 
			//오디오
			Element tag = doc.select("source").first(); //오디오	
			String dPath = tag.attr("src").trim(); //속성을 붙음
			System.out.println(dPath);
			String filename = dPath.substring(dPath.lastIndexOf("/")+1);
			System.out.println(filename);
			*/
			
			//이미지 다운로드
			Element tag = doc.select(".img>img").first(); //오디오	
			String dPath = "https://sum.su.or.kr:8888"+tag.attr("src").trim(); //속성을 붙음
			System.out.println(dPath);
			String filename = dPath.substring(dPath.lastIndexOf("/")+1);
			System.out.println(filename);
			
			
			
			Runnable r = new DownloadBroker(dPath, filename);
			
			Thread dLoad = new Thread(r); //스레드 구현
			dLoad.start();//다운로드 시작
			
			for(int i=0;i<10;i++) {
				try {
					
					Thread.sleep(1000);//1초
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.print("  "+(i+1));
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
