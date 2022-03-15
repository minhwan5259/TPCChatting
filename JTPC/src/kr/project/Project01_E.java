package kr.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Project01_E {

	public static void map_service(String point_x, String point_y, String address) {
		
		String URL_STATICMAP = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster?";
		try {
			String pos = URLEncoder.encode(point_x+ " "+ point_y, "UTF-8");
			String url = URL_STATICMAP;
			
			url+="w=700&h=500";
			url+="&center="+point_x+","+point_y+"&level=16&maptype=satellite&scale=2";//찾는 위치가 중앙에 오도록 url설정
			
			//풍선도움말
			url+="&markers=type:t|size:mid|pos:"+pos+"|label:"+URLEncoder.encode(address, "UTF-8");
			
	         URL u = new URL(url);
	         //HttpURLConnection : URL연동해주는 API클래스
	         HttpURLConnection con=(HttpURLConnection)u.openConnection();
	         con.setRequestMethod("GET");
	         con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "uby5g5cc40"); //API정보 (요청 헤더, 헤더명)
	         con.setRequestProperty("X-NCP-APIGW-API-KEY", "6qk1sYG5MJ4vwyoWzI4ttesrfQ4MieFSBqI5w45g");
	         BufferedReader br;  //라인단위로 읽어들이기 위한 객체 생성
			
	         int responseCode=con.getResponseCode();  //200
	         if(responseCode == 200) {
	            //웹에서 넘어온 데이터
	        	 InputStream is = con.getInputStream();
	        	 int read =0;
	        	 byte[] bytes =new byte[1024]; //이미지는 바이트 단위로 처리하여 받아오기위한 배열방 설정
	        	 String tempName = Long.valueOf(new Date().getTime()).toString();//랜덤이름 생성
	        	 File f = new File(tempName + ".jpg");
	        	 f.createNewFile(); //파일 생성
	        	 
	        	 //출력스트림
	        	  OutputStream outputStream = new FileOutputStream(f);
	        	  while((read=is.read(bytes)) != -1) {//-1:끝이 아니면
	        		  outputStream.write(bytes, 0, read); //0부터 읽어들인 만큼 바이트로 변환
	        		  
	        	  }
	        	  is.close();
	        	 
	         }else {
	        	 br=new BufferedReader(new InputStreamReader(con.getErrorStream()));
	        	 String inputline;
	        	 StringBuffer response = new StringBuffer();
	        	 
	        	 while((inputline=br.readLine()) !=null) {
	        		 response.append(inputline);  //스트림버퍼에 담기
	        	 	}
	     		 br.close();
	     		 System.out.println(response.toString());
	         }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		// https://naveropenapi.apigw.ntruss.com/map-static/v2/raster

		String client_id = "uby5g5cc40";
		String client_secret = "6qk1sYG5MJ4vwyoWzI4ttesrfQ4MieFSBqI5w45g";
		// new InputStreamReader(System.in) : 키보드로 입력한 데이터를 문자로 변경
		BufferedReader io = new BufferedReader(new InputStreamReader(System.in));
		  try {
		         System.out.print("주소를 입력하세요 : ");
		         String address=io.readLine();
		         String addr=URLEncoder.encode(address,"UTF-8");
		         String reqUrl="https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query="+addr;
		         
		         URL url=new URL(reqUrl);
		         //HttpURLConnection : URL연동해주는 API클래스
		         HttpURLConnection con=(HttpURLConnection)url.openConnection();
		         con.setRequestMethod("GET");
		         con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", client_id); //API정보 (요청 헤더, 헤더명)
		         con.setRequestProperty("X-NCP-APIGW-API-KEY", client_secret);
		         BufferedReader br;  //라인단위로 읽어들이기 위한 객체 생성
		         
		         int responseCode=con.getResponseCode();  //200
		         
		         if(responseCode == 200) {
		             //웹에서 넘어온 데이터
		             br=new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
		             
		          }else {
		             br=new BufferedReader(new InputStreamReader(con.getErrorStream()));
		          }
		         
		         String line;
		         StringBuffer response=new StringBuffer();
		         
		         String x = "";
		         String y = "";
		         String z = "";//roadAddress
		         
		         while((line=br.readLine()) !=null) {
		            response.append(line);  //스트림버퍼에 담기
		         }
		         br.close();
		         
		         JSONTokener tokener=new JSONTokener(response.toString());  //JSON객체
		         JSONObject object=new JSONObject(tokener); //Object로 변환
		         System.out.println(object.toString());
		         
		         JSONArray arr=object.getJSONArray("addresses"); //주소가 여러개일때 반복문으로 출력하기위한 컬렉션
					for(int i=0; i<arr.length(); i++) {
						JSONObject temp=(JSONObject)arr.get(i);
						System.out.println("roadAddress : "+ temp.get("roadAddress"));
						System.out.println("jibunAddress : "+ temp.get("jibunAddress"));
						System.out.println("경도 : "+ temp.get("x"));
						System.out.println("위도 : "+ temp.get("y"));
						x=(String)temp.getString("x");
						y=(String)temp.getString("y");
						z=(String)temp.getString("roadAddress");
						
					}
					//추가된 부분
		         map_service(x,y,z);
		         
		      } catch (Exception e) {
		         e.printStackTrace();
		      }
		        
	}

}
