package br.com.wirecard.core;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.BeforeClass;

import br.com.wirecard.tests.models.customers.Customer;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;

public class BaseTest implements Contantes {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = APP_BASE_URL;
		RestAssured.port = APP_PORT;
		RestAssured.basePath = APP_BASE_PATH;
		
		RequestSpecBuilder reqBuilder = new RequestSpecBuilder();
		reqBuilder.setContentType(APP_CONTENT_TYPE);
		RestAssured.requestSpecification = reqBuilder.build();
		
		ResponseSpecBuilder resBuilder = new ResponseSpecBuilder();
		resBuilder.expectResponseTime(Matchers.lessThan(MAX_TIMEOUT));
		RestAssured.responseSpecification = resBuilder.build();
		
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
	}
	
	public static String getEncodedString() {
		Map<String, String> login = new HashMap<String, String>();
		login.put("username", "QBM5CCEPHMPNFLGLST2Y0YOKGXCZLEMI");
		login.put("password", "REWSPWTUCYSCMHWJQGQB9XQM9O1OFPWNQSQQRQU0");
		
		String originalInput = login.get("username") + ":" + login.get("password");
		String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());
		
		return encodedString;
	}

}
