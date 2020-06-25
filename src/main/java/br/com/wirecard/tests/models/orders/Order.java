package br.com.wirecard.tests.models.orders;

import br.com.wirecard.tests.models.customers.Customer;

public class Order {
	
	private String ownId;
	private Amount amount;
	private Item[] items;
	private Customer customer;
	
	public Amount getAmount() {
		return amount;
	}
	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	public Item[] getItems() {
		return items;
	}
	public void setItems(Item[] items) {
		this.items = items;
	}
	public Customer getCustomers() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getOwnId() {
		return ownId;
	}
	public void setOwnId(String ownId) {
		this.ownId = ownId;
	}
	

}