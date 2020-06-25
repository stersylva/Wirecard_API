package br.com.wirecard.tests.models.customers;

import br.com.wirecard.tests.models.common.TaxDocument;

public class Customer {
	
	private String id;
	private String ownId;
	private String fullname;
	private String email;
	private String birthDate;
	private TaxDocument taxDocument;
//	private String
//	private String
//	private String
//	private String
//	private String
//	private String
//	private String
//	private String
//	private String
//	private String
//	private String
	public String getOwnId() {
		return ownId;
	}
	public void setOwnId(String ownId) {
		this.ownId = ownId;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public TaxDocument getTaxDocument() {
		return taxDocument;
	}
	public void setTaxDocument(TaxDocument taxDocument) {
		this.taxDocument = taxDocument;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	

}
//"ownId":"meu_id_customer_0009", 
//"fullname":"Maria Oliveira",
//"email":"maria@email.com",
//"birthDate":"1980-5-10",
//"taxDocument":{  
//   "type":"CPF",
//   "number":"10013390023"
//},
//"phone":{  
//   "countryCode":"55",
//   "areaCode":"11",
//   "number":"22226842"
//},
//"shippingAddress":{  
//   "city":"Rio de Janeiro",
//   "district":"Ipanema",
//   "street":"Avenida Atlântica",
//   "streetNumber":"60",
//   "zipCode":"02446000",
//   "state":"RJ",
//   "country":"BRA"
//},
//"fundingInstrument":{  
//   "method":"CREDIT_CARD",
//   "creditCard":{  
//      "expirationMonth":"06",
//      "expirationYear":"22",
//      "number":"6362970000457013",
//      "cvc":"123",
//      "holder":{  
//         "fullname":"Maria Oliveira",
//         "birthdate":"1980-05-10",
//         "taxDocument":{  
//            "type":"CPF",
//            "number":"10013390023"
//         },
//         "billingAddress":{  
//            "city":"São Paulo",
//            "district":"Jardim Paulistano",
//            "street":"Avenida Brigadeiro Faria Lima",
//            "streetNumber":"123",
//            "zipCode":"01451000",
//            "state":"SP",
//            "country":"BRA"
//         },
//         "phone":{  
//            "countryCode":"55",
//            "areaCode":"11",
//            "number":"22226842"
//         