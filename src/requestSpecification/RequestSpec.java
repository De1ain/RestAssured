package requestSpecification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpec {
	
	public static final RequestSpecification REQ_SPEC = new RequestSpecBuilder()
			.setContentType(ContentType.JSON)
			.setBaseUri("https://rahulshettyacademy.com")
			.addQueryParam("key", "qaclick123")
			.build();
}
