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
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class OrdersTest extends BaseTest {
	
	private static String OWNID_ID = "ownId_" + System.nanoTime();
	private static Customer customer = new Customer();
	
	@BeforeClass
	public static void customerOrder() {
		
		customer.setOwnId(OWNID_ID);
		customer.setFullname("teste de api");
		customer.setEmail("maria_jose@email.com");
		customer.setBirthDate("1980-5-10");
		TaxDocument document = new TaxDocument();
		document.setType("CPF");
		document.setNumber("10013390023");
		customer.setTaxDocument(document);
		
		ValidatableResponse response = given()
		 	.header("Authorization", "Basic " + getEncodedString())
		 		.body(customer)
			.when()
				.post("/customers")
			.then()
				.statusCode(201);
		customer.setId(response.extract().path("id").toString());
		
	}
	
	@Test
	public void tokenlessOrder() {
			
			given()
				.body("")
			.when()
				.post("/orders")
			.then()
				.contentType(ContentType.HTML)
				.statusCode(401)
				.body("html.body", is(notNullValue())) 
			;
	}
	
	@Test
	public void orderValidateFields() {
		
			given()
			.header("Authorization", "Basic " + getEncodedString())
				.body("{}")
			.when()
				.post("/orders")
			.then()
				.statusCode(400)
				.body("errors", hasSize(2))
				.body("errors.code", hasItems("ORD-001", "ORD-010"))
				.body("errors.path", hasItems("ownId", "items"))
				.body("errors.description", hasItems("É necessario informar seu identificador próprio",
						"É necessario informar pelo menos um item."))
			;
	}
	
	@Test
	public void orderSuccess() {

		Order order = new Order();
		order.setOwnId(OWNID_ID);
		
		Amount amount = new Amount();
		amount.setCurrency("BRL");
		amount.setSubtotals(new SubTotals(1500));
		
		Item item = new Item("Camisa", "CLOTHING", 1, "CAMISETA ESTAMPADA", 9500);
		Item items[] = new Item[1];
		items[0] = item;
				
		order.setAmount(amount);
		order.setItems(items);
		order.setCustomer(customer);
		
			given()
			.header("Authorization", "Basic " + getEncodedString())
				.body(order)
			.when()
				.post("/orders")
			.then()
				.statusCode(201)
				.body("id", is(notNullValue()))
				.body("ownId", is(OWNID_ID))
				.body("status", is("CREATED"))
				.body("items.price", hasItems(9500))
				.body("items.quantity", hasItems(1))
				.body("items.product", hasItems("Camisa"))
				.body("items.category", hasItems("CLOTHING"))
				
			;
	}
	
	@Test
	public void validateFieldItemProduct() {

		Order order = new Order();
		order.setOwnId(OWNID_ID);
		
		Amount amount = new Amount();
		amount.setCurrency("BRL");
		amount.setSubtotals(new SubTotals(1500));
		
		Item item = new Item("", "CLOTHING", 1, "CAMISETA ESTAMPADA", 9500);
		Item items[] = new Item[1];
		items[0] = item;
				
		order.setAmount(amount);
		order.setItems(items);
		order.setCustomer(customer);
		
			given()
			.header("Authorization", "Basic " + getEncodedString())
				.body(order)
			.when()
				.post("/orders")
			.then()
				.statusCode(400)
				.body("errors.code", hasItems("ORD-011"))
				.body("errors.description", hasItems("Informe o nome do produto"))
			;
	}
	@Test
	public void validateFieldCategory() {

		Order order = new Order();
		order.setOwnId(OWNID_ID);
		
		Amount amount = new Amount();
		amount.setCurrency("BRL");
		amount.setSubtotals(new SubTotals(1500));
		
		Item item = new Item("desc", "", 1, "CAMISETA ESTAMPADA", 9500);
		Item items[] = new Item[1];
		items[0] = item;
				
		order.setAmount(amount);
		order.setItems(items);
		order.setCustomer(customer);
		
			given()
			.header("Authorization", "Basic " + getEncodedString())
				.body(order)
			.when()
				.post("/orders")
			.then()
				.statusCode(400)
				.body("errors.code", hasItems("ORD-037"))
				.body("errors.description", hasItems("Categoria informada não é válida"))
			;
	}
	@Test
	public void validateFieldCategoryInvalid() {

		Order order = new Order();
		order.setOwnId(OWNID_ID);
		
		Amount amount = new Amount();
		amount.setCurrency("BRL");
		amount.setSubtotals(new SubTotals(1500));
		
		Item item = new Item("desc", "ANIMALS_AND_PET_SUPPLI", 1, "CAMISETA ESTAMPADA", 9500);
		Item items[] = new Item[1];
		items[0] = item;
				
		order.setAmount(amount);
		order.setItems(items);
		order.setCustomer(customer);
		
			given()
			.header("Authorization", "Basic " + getEncodedString())
				.body(order)
			.when()
				.post("/orders")
			.then()
				.statusCode(400)
				.body("errors.code", hasItems("ORD-037"))
				.body("errors.description", hasItems("Categoria informada não é válida"))
			;
	}
	@Test
	public void validateFieldQuantity() {

		Order order = new Order();
		order.setOwnId(OWNID_ID);
		
		Amount amount = new Amount();
		amount.setCurrency("BRL");
		amount.setSubtotals(new SubTotals(1500));
		
		Item item = new Item("desc", "CLOTHING", 0 ,"CAMISETA ESTAMPADA", 9500);
		Item items[] = new Item[1];
		items[0] = item;
				
		order.setAmount(amount);
		order.setItems(items);
		order.setCustomer(customer);
		
			given()
			.header("Authorization", "Basic " + getEncodedString())
				.body(order)
			.when()
				.post("/orders")
			.then()
				.statusCode(400)
				.body("errors.path", hasItems("items[0].quantity"))
				.body("errors.description", hasItems("must be between 1 and 999999"))
			;
	}
	@Test
	public void validateFieldDetail() {

		Order order = new Order();
		order.setOwnId(OWNID_ID);
		
		Amount amount = new Amount();
		amount.setCurrency("BRL");
		amount.setSubtotals(new SubTotals(1500));
		
		Item item = new Item("desc", "CLOTHING", 1 ,"", 9500);
		Item items[] = new Item[1];
		items[0] = item;
				
		order.setAmount(amount);
		order.setItems(items);
		order.setCustomer(customer);
		
			given()
			.header("Authorization", "Basic " + getEncodedString())
				.body(order)
			.when()
				.post("/orders")
			.then()
				.statusCode(201)
				.body("items.detail", hasItems(""))
			;
	}
	@Test
	public void validateFieldPrice() {

		Order order = new Order();
		order.setOwnId(OWNID_ID);
		
		Amount amount = new Amount();
		amount.setCurrency("BRL");
		amount.setSubtotals(new SubTotals(1500));
		
		Item item = new Item("desc", "CLOTHING", 1 ,"CAMISETA ESTAMPADA", null);
		Item items[] = new Item[1];
		items[0] = item;
				
		order.setAmount(amount);
		order.setItems(items);
		order.setCustomer(customer);
		
			given()
			.header("Authorization", "Basic " + getEncodedString())
				.body(order)
			.when()
				.post("/orders")
			.then()
				.statusCode(400)
				.body("errors.code", hasItems("ORD-014"))
				.body("errors.path", hasItems("items[0].price"))
				.body("errors.description", hasItems("O item deve ter um valor"))
			;
	}
	
	@Test
	public void validateFieldPriceInvalid() {

		Order order = new Order();
		order.setOwnId(OWNID_ID);
		
		Amount amount = new Amount();
		amount.setCurrency("BRL");
		amount.setSubtotals(new SubTotals(1500));
		
		Item item = new Item("desc", "CLOTHING", 1 ,"CAMISETA ESTAMPADA", -500);
		Item items[] = new Item[1];
		items[0] = item;
				
		order.setAmount(amount);
		order.setItems(items);
		order.setCustomer(customer);
		
			given()
			.header("Authorization", "Basic " + getEncodedString())
				.body(order)
			.when()
				.post("/orders")
			.then()
				.statusCode(400)
				.body("errors.code", hasItems("ORD-009"))
				.body("errors.path", hasItems("items[0].price"))
				.body("errors.description", hasItems("Todos os valores devem ser maiores que zero"))
			;
	}
	
	
}
