import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import utils.Payload;

import static io.restassured.RestAssured.*;

public class DynamicJson {
	
	@Test(dataProvider="BooksData") 
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = given().log().all()
		.header("Content-Type", "application/json")
		.body(Payload.AddBook(isbn, aisle))
		.when().post("/Library/Addbook.php")
		.then().assertThat()
		.statusCode(200)
		.extract().response().asString();
		
		JsonPath jp = ReusableMethods.rawToJson(response);
		String id = jp.get("ID");
		System.out.println("ID: " + id);
	}
	
	@DataProvider(name="BooksData")
	public Object[][] getData() {
		return new Object[][] {{"uioas","232"},{"yuifg", "585"},{"qwevb", "989"}};
	}
	
	@Test(dataProvider="BooksData")
	public void deleteBook(String isbn, String aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = given().log().all()
		.header("Content-Type", "application/json")
		.body(Payload.deleteBook(isbn+aisle))
		.when().delete("/Library/DeleteBook.php")
		.then().assertThat()
		.statusCode(200)
		.extract().response().asString();
		
		JsonPath jp = ReusableMethods.rawToJson(response);
		System.out.println(jp);
	}

}
