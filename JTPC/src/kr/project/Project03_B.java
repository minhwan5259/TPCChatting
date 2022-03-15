package kr.project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

public class Project03_B {

	public static void main(String[] args) {
		try {
			
			Workbook wb = new HSSFWorkbook();//메모리에 가상의 워크북 만들기
			Sheet sheet = wb.createSheet("My Sample Excel"); //시트 만들기
			InputStream is = new FileInputStream("pic.jpg");
			byte[] bytes = IOUtils.toByteArray(is);//이미지를 바이트 단위로 읽어서 저장할 배열방 선언
			int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
			is.close();
			
			CreationHelper helper = wb.getCreationHelper();
			Drawing drawing = sheet.createDrawingPatriarch(); 
			ClientAnchor anchor = helper.createClientAnchor();
		
			anchor.setCol1(1);
			anchor.setRow1(2);
			anchor.setCol1(2);
			anchor.setRow1(3);
			
			//지정된 위치에 이미지 생성
			Picture poct = drawing.createPicture(anchor, pictureIdx);
			
			Cell cell = sheet.createRow(2).createCell(1);
			int w = 20*256; //폭 하나당 256분의 1로 축소
			sheet.setColumnWidth(1, w);
			
			short h = 120*20;
			cell.getRow().setHeight(h);
			
			FileOutputStream fileOut = new FileOutputStream("myfile.xls");//Excel 파일 생성
			wb.write(fileOut); //파일에 이미지 저장됨
			fileOut.close();
			System.out.println("이미지 데이터 성공");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
