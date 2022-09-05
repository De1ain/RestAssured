import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utils.Payload;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

public class Basics {

	public static void main(String[] args) throws IOException {
		
		// given - all input details
		// when - submit the API - resource, http method
		// then - validate response
		
		// Add place -> Update place with new Address -> Get place to validate the new address appears in response
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
//		String addAddressResponse = given().log().all()
//		.queryParam("key", "qaclick123")
//		.header("Content-Type", "application/json")
//		.body(Payload.AddPlace())
//		.when().post("maps/api/place/add/json")
//		.then().assertThat()
//		.statusCode(200)
//		.body("scope", equalTo("APP"))
//		.header("Server", equalTo("Apache/2.4.41 (Ubuntu)"))
//		.extract().response().asString();		
		
		String addAddressResponse = given().log().all()
		.queryParam("key", "qaclick123")
		.header("Content-Type", "application/json")
		.body(new String(Files.readAllBytes(Paths.get("E:\\EclipseWorkspace\\RestAssured1\\src\\utils\\addPlace.json"))))
		.when().post("maps/api/place/add/json")
		.then().assertThat()
		.statusCode(200)
		.body("scope", equalTo("APP"))
		.header("Server", equalTo("Apache/2.4.41 (Ubuntu)"))
		.extract().response().asString();	
		
		JsonPath jp = ReusableMethods.rawToJson(addAddressResponse);
		String placeId = jp.getString("place_id");
		
		given().log().all()
		.queryParam("key","qaclick123")
		.header("Content-Type", "application/json")
		.body(Payload.UpdatePlace(placeId))
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all()
		.statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		String getUpdatedAddressResponse = given().log().all()
		.queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.header("Content-Type", "application/json")
		.when().get("maps/api/place/get/json")
		.then().assertThat()
		.statusCode(200)
		.extract().response().asString();
		
		JsonPath jp2 = ReusableMethods.rawToJson(getUpdatedAddressResponse);
		String actualAddress = jp2.getString("address");
		Assert.assertEquals(actualAddress, Payload.NEW_ADDRESS);
		
		
//		System.out.println(ReusableMethods.rawToJson(Payload.JSON_OBJ_COURSES));
	}

}
