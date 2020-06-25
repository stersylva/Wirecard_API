package br.com.wirecard.tests.models.orders;

public class SubTotals {
	private Integer shipping;
	private Integer addition;
	private Integer discount;
	
	public SubTotals(Integer shipping) {
		this.shipping = shipping;
	}
	
	public Integer getShipping() {
		return shipping;
	}
	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}
	public Integer getAddition() {
		return addition;
	}
	public void setAddition(Integer addition) {
		this.addition = addition;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

}
