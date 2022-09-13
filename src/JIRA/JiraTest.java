package JIRA;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraTest {
	private static String COMMENT_TEXT = "My comment from Rest Assured.";
	private static String BASE_URI = "http://localhost:8080";
	private static SessionFilter sf = new SessionFilter();
	private static String ISSUED_ID;
	
	@BeforeSuite
	private void loginUsingCookies() {
		RestAssured.baseURI = BASE_URI;
		
		given()
		.header("Content-Type", "application/json")
		.body("{ \"username\": \"borodin.tim\", \"password\": \"070788\" }")
		.filter(sf)
		.when().post("/rest/auth/1/session")
		.then().assertThat()
		.statusCode(200);
	}
	
	@Test
//	@Ignore
	public void  Test1AddComment() {
		RestAssured.baseURI = BASE_URI;
		
		given()
		.header("Content-Type", "application/json")
		.body("{ \"username\": \"borodin.tim\", \"password\": \"070788\" }")
		.filter(sf)
		.when().post("/rest/auth/1/session")
		.then().assertThat()
		.statusCode(200);
		
		String issueRes = given()
		.pathParam("key", "RAAUT-1")
		.header("Content-Type", "application/json")
		.body("{\r\n"
				+ "    \"body\": \""+COMMENT_TEXT+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}")
		.filter(sf)
		.when().post("/rest/api/2/issue/{key}/comment")
		.then().assertThat()
		.statusCode(201)
		.extract().asString();
		System.out.println(issueRes);
		JsonPath jp = new JsonPath(issueRes);
		String issueID = jp.getString("id");
		ISSUED_ID = issueID;
		System.out.println("ISSUED_ID: " + ISSUED_ID);
	}
	
	@Test
	@Ignore
	public void Test2AddAttachment() {
		// POST api/2/issue/{issueIdOrKey}/attachments
		// curl -D- -u admin:admin -X POST -H "X-Atlassian-Token: no-check" -F "file=@myfile.txt" http://myhost/rest/api/2/issue/TEST-123/attachments
		
		RestAssured.baseURI = BASE_URI;
		
		given()
		.pathParam("key", "RAAUT-1")
		.header("X-Atlassian-Token","no-check")
		.header("Content-Type", "multipart/form-data")
		.body("")		
		.filter(sf)
		.multiPart("file", new File("E:\\EclipseWorkspace\\RestAssured1\\src\\JIRA\\TextFile.txt"))
		.when().post("/rest/api/2/issue/{key}/attachments")
		.then().assertThat()
		.statusCode(200);
	}
	
	@Test
	public void Test3GetIssueDetails() {
		//GET /rest/api/2/issue/{issueIdOrKey}
		
		String message = null;
		String issueID = null;
		RestAssured.baseURI = BASE_URI;
		
		String issueRes = given()
//		.relaxedHTTPSValidation()
		.pathParam("key", "RAAUT-1")
		.queryParams("fields", "attachment")
		.queryParams("fields", "comment")
		.header("Content-Type", "application/json")
		.filter(sf)
		.when().get("/rest/api/2/issue/{key}")
		.then().assertThat()
		.statusCode(200)
		.extract().asString();
		
		// Comments check
		JsonPath jp = new JsonPath(issueRes);
		int commentLength = jp.getInt("fields.comment.comments.size()");
		System.out.println(commentLength);
		
		String comment1 = jp.getString("fields.comment.comments[0].body");
		System.out.println(comment1);
		String comment2 = jp.getString("fields.comment.comments[1].body");
		System.out.println(comment2);
		Assert.assertEquals(comment1, "My comment 1 from Rest Assured.");
		Assert.assertEquals(comment2, "My comment 2 from Rest Assured.");
		
		String newestComment = jp.getString("fields.comment.comments[" + (commentLength - 1) + "].body");
		System.out.println(newestComment);
		Assert.assertEquals(newestComment, COMMENT_TEXT);
		
		for (int i = 0; i < commentLength; i++) {
			issueID = jp.getString("fields.comment.comments["+i+"].id");
			if(ISSUED_ID.equals(issueID)) {
				System.out.println("Checking issue " + ISSUED_ID);
				message = jp.getString("fields.comment.comments["+i+"].body");
			}
		}
		Assert.assertEquals(message, COMMENT_TEXT);
		
		// Attachments check
		int attachmentsLength = jp.getInt("fields.attachment.size()");
		System.out.println(attachmentsLength);
		for (int i = 0; i < attachmentsLength; i++) {
			System.out.println(jp.getString("fields.attachment["+i+"].filename") + " " + jp.getString("fields.attachment["+i+"].created"));
		}
	}
}
