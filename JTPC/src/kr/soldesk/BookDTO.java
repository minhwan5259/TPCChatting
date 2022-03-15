package kr.soldesk;

public class BookDTO {

	private String title;
	private int price;
	private String company;
	private int page;
	
	public BookDTO(String title, int price, String company, int page) {
		this.title=title;
		this.price=price;
		this.company=company;
		this.page=page;
	}
	
	

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "BookDTO [title="+title+", price ="+price+", company ="+company+", page ="+page+"]";
	}



	public String getTitle() {
		return title;
	}
	
	
	public void setTitle(String title) {
		this.title = title;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
	
	
}
