package kr.project;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.soldesk.BookDTO;

public class Project01_A {

	public static void main(String[] args) {
		//Object(BookDTO) = JSON(String)
		BookDTO dto = new BookDTO("JAVA", 25000, "한빛미디어", 520);
		Gson g = new Gson();
		String json = g.toJson(dto);
		System.out.println(json);
		
		//JSON(String) => Object(BookDTO)
		BookDTO dto1 = g.fromJson(json, BookDTO.class);
		System.out.println(dto1);
		
		//=======================================
		//Object(List<BookDTO>) = JSON(String) : [{},{},{}....]
		List<BookDTO> list = new ArrayList<BookDTO>();
		list.add(new BookDTO("JAVA1", 25000, "한빛미디어1", 520));
		list.add(new BookDTO("JAVA2", 24000, "한빛미디어2", 330));
		list.add(new BookDTO("JAVA3", 23000, "한빛미디어3", 220));
		
		String listJson = g.toJson(list);
		System.out.println(listJson);
		
		//JSON(String) => Object(List<BookDTO>)
		List<BookDTO> list1=g.fromJson(listJson, new TypeToken<List<BookDTO>>(){}.getType()); //reflect
		for(BookDTO vo:list1) {
			System.out.println(vo);
		}
		
		
		
		
	}
}
