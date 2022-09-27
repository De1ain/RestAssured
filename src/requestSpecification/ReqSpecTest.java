package requestSpecification;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ReqSpecTest {
	
	@Test
	public void TestReqSpec1() throws IOException {
		RequestSpecification reqSpec = given()
		.spec(RequestSpec.REQ_SPEC)
		.body(new String(Files.readAllBytes(Paths.get("src\\utils\\addPlace.json"))));
		
		Response res = reqSpec
		.when().post("maps/api/place/add/json")
		.then().spec(ResponseSpec.RES_SPEC)
		.extract().response();	
		
		String resStr = res.asString();
		
		System.out.println("resStr: " + resStr);
		
		JsonPath jp = new JsonPath(resStr);
		String status = jp.getString("status");
		if(status.equals("OK")) {
			System.out.println("TEST PASSED!");
		}
	}

}
