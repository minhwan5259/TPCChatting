package kr.project;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import kr.soldesk.ExcelVo;



public class Project03_D {

	public static void main(String[] args) {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			
			System.out.print("책제목 : ");
			String title = br.readLine();
			System.out.print("책저자 : ");
			String author = br.readLine();
			System.out.print("출판사 : ");
			String company = br.readLine();
			
			ExcelVo vo =new ExcelVo(title, author, company);
			
			getIsbnImg(vo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private static void getIsbnImg(ExcelVo vo)  {
		try {
			String openApi = "https://openapi.naver.com/v1/search/book_adv.xml?d_titl="+URLEncoder.encode(vo.getTitle(),"UTF-8")
			+"&d_auth="+URLEncoder.encode(vo.getAuthor(),"UTF-8")
			+"&d_publ="+URLEncoder.encode(vo.getCompany(),"UTF-8");
			
			URL url = new URL(openApi);
			
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", "Irm0rMq5W8NydAerqfxk");
			con.setRequestProperty("X-Naver-Client-Secret", "BLQrnq4mbB");
			
			int responseCode = con.getResponseCode();
			
			BufferedReader br1 = null;
			
			if(responseCode == 200) {
				 br1=new BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));			 
			 }else {
				 br1=new BufferedReader(new InputStreamReader(con.getErrorStream()));
			 }
			String inputLine;
			StringBuffer response = new StringBuffer();  
			while((inputLine = br1.readLine()) != null ) {
				response.append(inputLine);
			}
			br1.close();
			//System.out.println(response.toString());
			Document doc = Jsoup.parse(response.toString());
			//System.out.println(doc.toString());
			Element total = doc.select("total").first();
			//======================================================
			if(!(total.text().equals("0"))) {
				Element isbn = doc.select("isbn").first();
				String isbnStr = isbn.text();
				String isbn_find = isbnStr.split(" ")[0]; //공백을 기준으로 자른뒤 첫번째 데이터 가져오기
				vo.setIsbn(isbn_find);
				//==================================================
				String imgDoc = doc.toString();
				//indexOF("<img>")+5 : 앞에서부터 읽되 <img>글자제외 즉 <img>
				String imgTag= imgDoc.substring(imgDoc.indexOf("<img>")+5);
				
				//System.out.println(imgTag);
				String imgURL = imgTag.substring(0,imgTag.indexOf("?"));//처음부터 ?전까지 읽음
				//System.out.println(imgURL);
				String fileName = imgURL.substring(imgURL.lastIndexOf("/")+1); //마지막 "/"기호를 읽되 +1하이ㅕ "/"제외시킴
				//System.out.println(fileName);
				vo.setImgurl(fileName);
				System.out.println(vo);
				
			}else {
				System.out.println("검색데이터가 없습니다.");
			}
				
				
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
