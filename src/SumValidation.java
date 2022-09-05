import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import utils.Payload;

public class SumValidation {
	
	@Test
	public void sumOfCourses() {
		JsonPath jp = new JsonPath(Payload.JSON_OBJ_COURSES);
		
		int coursesAmount = jp.getInt("courses.size()");
		int purchaseAmount = jp.getInt("dashboard.purchaseAmount");
		System.out.println("coursesAmount: " + coursesAmount);
		System.out.println("purchaseAmount: " + purchaseAmount);
				
		int actualSum = 0;
		for (int i = 0; i < coursesAmount; i++) {
			actualSum += jp.getInt("courses[" + i + "].price") * jp.getInt("courses[" + i + "].copies");
		}
		System.out.println("actualSum: " + actualSum);
		
		Assert.assertEquals(actualSum, purchaseAmount);
	}
}
