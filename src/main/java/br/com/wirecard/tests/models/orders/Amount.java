package br.com.wirecard.tests.models.orders;

public class Amount {
	
	private String currency;
	private SubTotals subtotals;
	
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public SubTotals getSubtotals() {
		return subtotals;
	}
	public void setSubtotals(SubTotals subtotals) {
		this.subtotals = subtotals;
	}

}
