package kr.project;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



public class Project02_F extends JFrame implements ActionListener, ItemListener{
	private Choice chyear, chmonth;
	private JLabel yLabel, mLabel;
	private JTextArea area;
	GregorianCalendar gc;
	private int year, month;
	private JLabel[] dayLabel = new JLabel[7];
	private String[] day={"일","월","화","수","목","금","토"};
	private JButton[] days = new JButton[42];//7일이 6주이므로 42개의 버튼필요
	private JPanel selectPanel = new JPanel();
	private GridLayout grid = new GridLayout(7,7,5,5);//행,열,수평갭,수직갭
	private Calendar ca = Calendar.getInstance();
	private Dimension dimen, dimen1;
	private int xpos, ypos;
	
	public Project02_F() {
		//Calendar.Month() : 1월 0이므로 +1
		setTitle("오늘의 QT : " + ca.get(Calendar.YEAR) + "/" + (ca.get(Calendar.MONTH+1)) + "/" + (ca.get(Calendar.DATE)));
		setSize(900,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//X로 화면 구성
		dimen = Toolkit.getDefaultToolkit().getScreenSize();//화면의 크기
		dimen1 = this.getSize();//프레임 크기
		xpos = (int) (dimen.getWidth()/2 - dimen1.getWidth()/2);
		ypos = (int) (dimen.getHeight()/2 - dimen1.getHeight()/2);
		
		setLocation(xpos,ypos);
		setResizable(false);
		setVisible(true);
		
		chyear = new Choice();
		chmonth = new Choice();
		yLabel = new JLabel("년");
		mLabel = new JLabel("월");
		init();
		
	}
	


	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		area.setText("");
		String year = chyear.getSelectedItem();//선택된 객체의 내용
		String month = chmonth.getSelectedItem();//선택된 객체의 내용
		
		JButton btn = (JButton)arg0.getSource();
		String day = btn.getText();//메소드를 이용하여 현재 텍스트박스의 내용만 출력
		System.out.println(year+","+month+","+day);
		String bible = year+"-"+month+"-"+day;
		
		//Jsoup API : HTML 파싱
		String url ="https://sum.su.or.kr:8888/bible/today/Ajax/Bible/BosyMatter?qt_ty=QT1&Base_de="+bible+"&bibleType=1";
		
		try {
			Document doc = Jsoup.connect(url).post();
			//System.out.println(doc.toString());
			Element bible_text = doc.select(".bible_text").first();//주제목
			System.out.println(bible_text.text());
			Element bibleinfo_box = doc.select(".bibleinfo_box").first();//소재목 박스
			System.out.println(bibleinfo_box.text());
			//#dailybible_info
			Element dailybible_info = doc.select("#dailybible_info").first();//선택한 날짜
			System.out.println(dailybible_info.text());
	
			//GUI area 텍스트에 붙이기
			area.append(dailybible_info.text()+"\n");
			area.append(bible_text.text()+"\n");
			area.append(bibleinfo_box.text()+"\n");
			
			Elements body_list = doc.select(".body_list>li");//말씀
			for(Element li: body_list) {
				String line=li.select(".info").first().text();
				 if(line.length()>65) {
			 	  line=line.substring(0,36)+"\n"+line.substring(36,66)+"\n"+line.substring(66)+"\n";	
	 			  area.append(li.select(".num").first().text()+":"+line);
				 }else if(line.length()>35) {
				  line=line.substring(0,36)+"\n"+line.substring(36)+"\n";	
				  area.append(li.select(".num").first().text()+":"+line);	
				 }else {
				  area.append(li.select(".num").first().text()+":"+li.select(".info").first().text()+"\n");
			}
				 System.out.print(li.select(".num").first().text()+":");
					System.out.println(li.select(".info").first().text());
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {
		select();
		calendar();

	}
	public void select() {
		JPanel panel = new JPanel(grid);
		for(int i=2022; i>=2000;i--){
			chyear.add(String.valueOf(i));
		}
		for(int i=1; i <=12; i++){
			chmonth.add(String.valueOf(i));
		}
		for(int i=0;i<day.length;i++) {//요일 이름을 레이블에 출력
			dayLabel[i] = new JLabel(day[i], JLabel.CENTER);
			panel.add(dayLabel[i]);
			dayLabel[i].setBackground(Color.gray);
		}
		dayLabel[6].setForeground(Color.blue);//토요일 색상
		dayLabel[0].setForeground(Color.RED);//일요일 색상
		
		for(int i = 0; i < 42;i++){ //총 42개의 버튼
			days[i] = new JButton(""); // 제목이 없는 버튼으로 초기 생성
			if(i % 7 == 0)
				days[i].setForeground(Color.RED);
			else if(i % 7 == 6)
				days[i].setForeground(Color.BLUE);
			else
				days[i].setForeground(Color.BLACK);
			days[i].addActionListener(this);
			panel.add(days[i]);
		}
		selectPanel.add(chyear);
		selectPanel.add(yLabel);
		selectPanel.add(chmonth);
		selectPanel.add(mLabel);
		
		area = new JTextArea(60,40);
		area.setCaretPosition(area.getDocument().getLength());
		JScrollPane scrollPane = new JScrollPane(area);
		
		this.add(selectPanel, "North");
		this.add(panel, "Center");
		this.add(scrollPane, "East");
		
		String m = (ca.get(Calendar.MONTH)+1)+ "";
		String y = (ca.get(Calendar.YEAR))+ "";
		
		chyear.select(y);
		chmonth.select(m);
		chyear.addItemListener(this);
		chmonth.addItemListener(this);
		
	}
	public void calendar() {
		year = Integer.parseInt(chyear.getSelectedItem());
		month=Integer.parseInt(chmonth.getSelectedItem());
		gc = new GregorianCalendar(year, month-1, 1);
		int max = gc.getActualMaximum(gc.DAY_OF_MONTH);//해당 달의 최대 일 수 획득
		int week = gc.get(gc.DAY_OF_WEEK);//해당 달의 시작 요일
		System.out.println("Day_of_week" + week);
		String today = Integer.toString(ca.get(Calendar.DATE));
		String today_month = Integer.toString(ca.get(Calendar.MONTH)+1);
		System.out.println("today"+today);
		
		for(int i=0; i<days.length; i++) {
			days[i].setEnabled(true);
		}
		for(int i=0; i<week-1;i++) {// 시작을 이전의 버튼 비활성화
			days[i].setEnabled(false);
		}
		for(int i = week; i< max+week; i++){
			days[i-1].setText(String.valueOf(i-week+1));
			days[i-1].setBackground(Color.white);
			if(today_month.equals(String.valueOf(month))) {//오늘이 속한 달과 일치
				if(today.equals(days[i-1].getText())) {//버튼의 날짜와 일치
					days[i-1].setBackground(Color.CYAN);//버튼색 변경
				}
			}
		}
		for(int i = (max+week-1); i < days.length; i++){// 날짜가 없는 버튼
			days[i].setEnabled(false);
		}
		System.out.println("max+week :"+ (max+week) + ", week : " + week  );
	}
	
	
	
	//선택된 기존의 달력정보를 지우고 새로 그리는 코드
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		Color color = this.getBackground();
		if(arg0.getStateChange()==ItemEvent.SELECTED){
			for(int i = 0; i < 42; i++){//기존 달력지우고 새로 시작
				if( !days[i].getText().equals("")){ //기존 내용이 있다면
					days[i].setText(""); //지워
					days[i].setBackground(color);//그레이색으로 기본설정
			 }
			}
			calendar(); //달력 다시 부르기
	      }
		
	}
	
	//===========================================================0
	public static void main(String[] args) {
		new Project02_F();
		
		
		
	}

}
