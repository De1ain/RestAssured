package serialization;

import static io.restassured.RestAssured.*;

import java.util.Arrays;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import serialization.BodyPOJO;
import serialization.LocationPOJO;

public class SerializeTests {

	@Test
	public void Test1() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
//		RestAssured.baseURI = "https://rahulshettyacademy.com";

		BodyPOJO bodyPOJO = new BodyPOJO();
		LocationPOJO locationPOJO = new LocationPOJO();
		locationPOJO.setLat(-38.383494);
		locationPOJO.setLng(33.427362);
		bodyPOJO.setLocation(locationPOJO);
		bodyPOJO.setAccuracy(50);
		bodyPOJO.setName("Frontline House");
		bodyPOJO.setPhone_number("(+91) 983 893 3937");
		bodyPOJO.setAddress("29, side layout, cohen 09");
		bodyPOJO.setTypes(Arrays.asList("shoe_park", "shop"));
		bodyPOJO.setWebsite("http://google.com");
		bodyPOJO.setLanguage("French-IN");

		Response response = given().log().all()
				.queryParam("key", "qaclick123")
				.body(bodyPOJO)
				.when().post("/maps/api/place/add/json")
				.then().assertThat()
				.statusCode(200)
				.extract().response();

		String responseStr = response.asString();
		System.out.println(responseStr);
	}

}
