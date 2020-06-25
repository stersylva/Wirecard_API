package br.com.wirecard.core;

import io.restassured.http.ContentType;

public interface Contantes {
	
	String APP_BASE_URL = "https://sandbox.moip.com.br";
	Integer APP_PORT = 443;
	String APP_BASE_PATH = "/v2";
	
	ContentType APP_CONTENT_TYPE = ContentType.JSON;
	
	Long MAX_TIMEOUT = 9000L;

}
