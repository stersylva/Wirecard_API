package br.com.wirecard.tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.wirecard.core.BaseTest;
import br.com.wirecard.tests.models.common.TaxDocument;
import br.com.wirecard.tests.models.customers.Customer;
import br.com.wirecard.tests.models.orders.Amount;
import br.com.wirecard.tests.models.orders.Item;
import br.com.wirecard.tests.models.orders.Order;
import br.com.wirecard.tests.models.orders.SubTotals;
import br.com.wirecard.tests.models.payment.CreditCard;
import br.com.wirecard.tests.models.payment.FundingInstrument;
import br.com.wirecard.tests.models.payment.Holder;
import br.com.wirecard.tests.models.payment.Payment;
import br.com.wirecard.tests.models.payment.Phone;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class PaymentsTest extends BaseTest {
	
	private static String OWNID_ID = "ownId_" + System.nanoTime();
	private static Customer customer = new Customer();
	private static Order order = new Order();
	private static String orderId;
	
	@BeforeClass
	public static void orderPayment() {
		
		customer.setOwnId(OWNID_ID);
		customer.setFullname("teste de api");
		customer.setEmail("maria_jose@email.com");
		customer.setBirthDate("1980-5-10");
		TaxDocument document = new TaxDocument();
		document.setType("CPF");
		document.setNumber("10013390024");
		customer.setTaxDocument(document);
		
		ValidatableResponse response = given()
		 	.header("Authorization", "Basic " + getEncodedString())
		 		.body(customer)
			.when()
				.post("/customers")
			.then()
				.statusCode(201);
		customer.setId(response.extract().path("id").toString());
		
		order.setOwnId(OWNID_ID);
		Amount amount = new Amount();
		amount.setCurrency("BRL");
		amount.setSubtotals(new SubTotals(1500));
		
		Item item = new Item("desc", "CLOTHING", 1, "CAMISETA ESTAMPADA", 9500);
		Item items[] = new Item[1];
		items[0] = item;
				
		order.setAmount(amount);
		order.setItems(items);
		order.setCustomer(customer);
		
		ValidatableResponse responseOrder = given()
		 	.header("Authorization", "Basic " + getEncodedString())
		 		.body(order)
			.when()
				.post("/orders")
			.then()
				.statusCode(201);
		
		orderId = responseOrder.extract().path("id");
		
	}
	
	@Test
	public void paymentValidateFields() {
		
			given()
			.header("Authorization", "Basic " + getEncodedString())
			.body("{}")
			.when()
				.post("/orders/" + orderId + "/payments")
			.then()
				.statusCode(400)
				.body("errors", hasSize(1))
				.body("errors.path", hasItems("fundingInstrument"))
				.body("errors.description", hasItems("{payment.fundinginstrument.notnull}"))
			;
	}
	
	@Test
	public void tokenlessPayment() {
		
			given()
			.body("{}")
			.when()
				.post("/orders/" + orderId + "/payments")
			.then()
			.contentType(ContentType.HTML)
			.statusCode(401)
			.body("html.body", is(notNullValue()))
			;
	}
	
	@Test
	public void paymentSuccessCreditCard() {
		Payment payment = new Payment();		
		payment.setInstallmentCount(1);
		payment.setStatementDescriptor("minha loja");
		
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setMethod("CREDIT_CARD");
		CreditCard creditCard = new CreditCard();
		creditCard.setNumber("5555666677778884");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2022);
		creditCard.setCvc(123);
		creditCard.setStore(false);
		
		
		Holder holder = new Holder();
		holder.setFullname("joase da Silva");
		holder.setBirthdate("1988-12-30");
		
		TaxDocument taxDocument = new TaxDocument();
		taxDocument.setType("CPF");
		taxDocument.setNumber("33333333344");
		
		Phone phone = new Phone();
		phone.setCountryCode(55);
		phone.setAreaCode(11);
		phone.setNumber(66778899);
		
		payment.setFundingInstrument(fundingInstrument);
		fundingInstrument.setCreditCard(creditCard);
		creditCard.setHolder(holder);
		holder.setTaxDocument(taxDocument);
		
		
		given()
		.header("Authorization", "Basic " + getEncodedString())
			.body(payment)
		.when()
			.post("/orders/" + orderId + "/payments")
		.then()
			.statusCode(201)
			.body("id", is(notNullValue()))
			.body("status", is("IN_ANALYSIS"))
			.body("amount.total", is(11000))
			.body("fundingInstrument.method", is("CREDIT_CARD"))
			.body("acquirerDetails.authorizationNumber", is("T12996"))
			.body("events.type", hasItems("PAYMENT.IN_ANALYSIS", "PAYMENT.CREATED"))
			.body("events.createdAt", is(notNullValue()))
			
		;
	}
	
	@Test
	public void validateFieldFundingInstrument() {
		Payment payment = new Payment();		
		payment.setInstallmentCount(1);
		payment.setStatementDescriptor("minha loja");
		
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setMethod("");
		CreditCard creditCard = new CreditCard();
		creditCard.setNumber("5555666677778884");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2022);
		creditCard.setCvc(123);
		creditCard.setStore(false);
		
		
		Holder holder = new Holder();
		holder.setFullname("joase da Silva");
		holder.setBirthdate("1988-12-30");
		
		TaxDocument taxDocument = new TaxDocument();
		taxDocument.setType("CPF");
		taxDocument.setNumber("33333333344");
		
		Phone phone = new Phone();
		phone.setCountryCode(55);
		phone.setAreaCode(11);
		phone.setNumber(66778899);
		
		payment.setFundingInstrument(fundingInstrument);
		fundingInstrument.setCreditCard(creditCard);
		creditCard.setHolder(holder);
		holder.setTaxDocument(taxDocument);
		
		
		given()
		.header("Authorization", "Basic " + getEncodedString())
			.body(payment)
		.when()
			.post("/orders/" + orderId + "/payments")
		.then()
			.statusCode(400)
			.body("errors.code", hasItems("-"))
			.body("errors.path", hasItems("fundingInstrument.method"))
			.body("errors.description", hasItems("Valor inválido para o campo, consulte a documentação"))
			
			
		;
	}
	
	@Test
	public void validateFieldFundingInstrumentCrediCardNumber() {
		Payment payment = new Payment();		
		payment.setInstallmentCount(1);
		payment.setStatementDescriptor("minha loja");
		
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setMethod("CREDIT_CARD");
		CreditCard creditCard = new CreditCard();
		creditCard.setNumber("");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2022);
		creditCard.setCvc(123);
		creditCard.setStore(false);
		
		
		Holder holder = new Holder();
		holder.setFullname("joase da Silva");
		holder.setBirthdate("1988-12-30");
		
		TaxDocument taxDocument = new TaxDocument();
		taxDocument.setType("CPF");
		taxDocument.setNumber("33333333344");
		
		Phone phone = new Phone();
		phone.setCountryCode(55);
		phone.setAreaCode(11);
		phone.setNumber(66778899);
		
		payment.setFundingInstrument(fundingInstrument);
		fundingInstrument.setCreditCard(creditCard);
		creditCard.setHolder(holder);
		holder.setTaxDocument(taxDocument);
		
		
		given()
		.header("Authorization", "Basic " + getEncodedString())
			.body(payment)
		.when()
			.post("/orders/" + orderId + "/payments")
		.then()
			.statusCode(400)
			.body("errors.code", hasItems("PAY-641"))
			.body("errors.path", hasItems("fundingInstrument.creditCard.validNumber"))
			.body("errors.description", hasItems("O número informado não é um número de cartão válido"))
			
		;
	}
	
	@Test
	public void validateFieldFundingInstrumentCrediCardExpirationMonth() {
		Payment payment = new Payment();		
		payment.setInstallmentCount(1);
		payment.setStatementDescriptor("minha loja");
		
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setMethod("CREDIT_CARD");
		CreditCard creditCard = new CreditCard();
		creditCard.setNumber("5555666677778884");
		creditCard.setExpirationMonth(40);
		creditCard.setExpirationYear(2022);
		creditCard.setCvc(123);
		creditCard.setStore(false);
		
		
		Holder holder = new Holder();
		holder.setFullname("joase da Silva");
		holder.setBirthdate("1988-12-30");
		
		TaxDocument taxDocument = new TaxDocument();
		taxDocument.setType("CPF");
		taxDocument.setNumber("33333333344");
		
		Phone phone = new Phone();
		phone.setCountryCode(55);
		phone.setAreaCode(11);
		phone.setNumber(66778899);
		
		payment.setFundingInstrument(fundingInstrument);
		fundingInstrument.setCreditCard(creditCard);
		creditCard.setHolder(holder);
		holder.setTaxDocument(taxDocument);
		
		
		given()
		.header("Authorization", "Basic " + getEncodedString())
			.body(payment)
		.when()
			.post("/orders/" + orderId + "/payments")
		.then()
			.statusCode(400)
			.body("errors.code", hasItems("PAY-621"))
			.body("errors.path", hasItems(""))
			.body("errors.description", hasItems("O mês de expiração do cartão informado é inválido"))
			
		;
	}
	@Test
	public void validateFieldFundingInstrumentCrediCardExpirationYear() {
		Payment payment = new Payment();		
		payment.setInstallmentCount(1);
		payment.setStatementDescriptor("minha loja");
		
		FundingInstrument fundingInstrument = new FundingInstrument();
		fundingInstrument.setMethod("CREDIT_CARD");
		CreditCard creditCard = new CreditCard();
		creditCard.setNumber("5555666677778884");
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2000);
		creditCard.setCvc(123);
		creditCard.setStore(false);
		
		
		Holder holder = new Holder();
		holder.setFullname("joase da Silva");
		holder.setBirthdate("1988-12-30");
		
		TaxDocument taxDocument = new TaxDocument();
		taxDocument.setType("CPF");
		taxDocument.setNumber("33333333344");
		
		Phone phone = new Phone();
		phone.setCountryCode(55);
		phone.setAreaCode(11);
		phone.setNumber(66778899);
		
		payment.setFundingInstrument(fundingInstrument);
		fundingInstrument.setCreditCard(creditCard);
		creditCard.setHolder(holder);
		holder.setTaxDocument(taxDocument);
		
		
		given()
		.header("Authorization", "Basic " + getEncodedString())
			.body(payment)
		.when()
			.post("/orders/" + orderId + "/payments")
		.then()
			.statusCode(400)
			.body("errors.code", hasItems("PAY-624"))
			.body("errors.path", hasItems(""))
			.body("errors.description", hasItems("A data de expiração do cartão de crédito deve ser maior ou igual a data atual"))
			
		;
	}
	
	
}
