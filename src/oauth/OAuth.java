package oauth;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class OAuth {
	
	@Test
	public void TestOAuthGrantTypeAuthorizationCode() throws InterruptedException {
		
		// get authorization code
//		String URL="https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php";
//		System.setProperty("webdriver.chrome.driver", "E://ChromeDriver//chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		driver.get(URL);
//		WebElement inputEmail = driver.findElement(By.id("identifierId"));
////		WebElement inputEmail = driver.findElement(By.xpath("//*[@id=\"identifierId\"]"));
////		WebElement inputEmail = driver.findElement(By.cssSelector("input[type='email']"));
//		inputEmail.sendKeys("borodin.tim@gmail.com");
//		Thread.sleep(2000);
////		WebElement btn = driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/div/button/span"));
//		WebElement btnNext = driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/div/button/span"));
//		btnNext.click();
//		Thread.sleep(2000);
//		WebElement inputPassword = driver.findElement(By.cssSelector("input[type='password']"));
//		inputPassword.sendKeys("myPassword");
//		inputPassword.sendKeys(Keys.ENTER);
//		Thread.sleep(2000);	
//		String currentURL = driver.getCurrentUrl();
		String currentURL = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qgk7WRcM6rZMELCi69LLZ7GXRXirmMWy32xFTIFIR0iBKWs1itSxPLcCa3cTH4EKg&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";
//		driver.close();
		
		String partialCode = currentURL.split("code=")[1];
		String authorizationCode = partialCode.split("&scope=")[0];
		System.out.println("code: " +  authorizationCode);
		
		// get access_token
		String accessTokenResponse = given()
		.urlEncodingEnabled(false)
		.queryParams("code", authorizationCode)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.queryParams("scope", "https://www.googleapis.com/auth/userinfo.email")
		.queryParams("response_type", "code")
		.when().post("https://www.googleapis.com/oauth2/v4/token")
		.asString()
		;
		System.out.println("accessToken: " + accessTokenResponse);
		JsonPath jp = new JsonPath(accessTokenResponse);
		String accessToken = jp.getString("access_token");
		System.out.println("accessToken: " + accessToken);
		
		String response = given()
		.queryParam("access_token", accessToken)
		.when().get("https://rahulshettyacademy.com/getCourse.php")
//		.then()
//		.extract()
		.asString()
		;
		
		System.out.println("response: " + response);
	}
	
	@Test
	public void TestOAuthGrantTypeClientCredentials() throws InterruptedException {
		
	}

}
