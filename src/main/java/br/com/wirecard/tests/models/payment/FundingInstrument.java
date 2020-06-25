package br.com.wirecard.tests.models.payment;

public class FundingInstrument {
	private String method;
	private CreditCard creditCard;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	public Boolean getStore() {
		return store;
	}
	public void setStore(Boolean store) {
		this.store = store;
	}
	private Boolean store;

}
