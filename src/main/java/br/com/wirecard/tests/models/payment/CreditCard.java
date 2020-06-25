package br.com.wirecard.tests.models.payment;

public class CreditCard {
	private String number;
	private Integer expirationMonth;
	private Integer expirationYear;
	private Integer cvc;
	private Holder holder;
	private Boolean store; 
	public Boolean getStore() {
		return store;
	}
	public void setStore(Boolean store) {
		this.store = store;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Integer getExpirationMonth() {
		return expirationMonth;
	}
	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}
	public Integer getExpirationYear() {
		return expirationYear;
	}
	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}
	public Integer getCvc() {
		return cvc;
	}
	public void setCvc(Integer cvc) {
		this.cvc = cvc;
	}
	public Holder getHolder() {
		return holder;
	}
	public void setHolder(Holder holder) {
		this.holder = holder;
	}

}
