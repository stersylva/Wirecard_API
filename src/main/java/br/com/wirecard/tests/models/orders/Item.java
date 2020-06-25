package br.com.wirecard.tests.models.orders;

public class Item {
	private String product;
	private String category;
	private Integer quantity;
	private String detail;
	private Integer price;
	
	public Item (String product, String category, Integer quantity, String detail, Integer price) {
		this.product = product;
		this.category = category;
		this.quantity = quantity;
		this.detail = detail;
		this.price = price;
	}
	
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	

}
