import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import utils.Payload;

public class ComplexJsonParser {

	public static void main(String[] args) {
		
		JsonPath jp = new JsonPath(Payload.JSON_OBJ_COURSES);
				
		// 1. Print No of courses returned by API
		int coursesAmount = jp.getInt("courses.size()");
		System.out.println("coursesAmount: " + coursesAmount);

		// 2.Print Purchase Amount
		int purchaseAmount = jp.getInt("dashboard.purchaseAmount");
		System.out.println("purchaseAmount: " + purchaseAmount);
		
		// 3. Print Title of the first course
		String firstCourseTitle = jp.getString("courses[0].title");
		System.out.println("firstCourseTitle: " + firstCourseTitle);
		
		// 4. Print All course titles and their respective Prices
		String[] arr = new String[coursesAmount];
		for (int i = 0; i < coursesAmount; i++) {
			arr[i] = jp.get("courses[" + i + "].title") + "(" + jp.get("courses[" + i + "].price").toString()+")";
		}
		System.out.println("Courses titles: " + Arrays.toString(arr));
		List<String> listOfCourses = jp.getList("courses");
		System.out.println("listOfCourses: " + listOfCourses);
		
		// 5. Print no of copies sold by RPA Course
		String courseName = "RPA";
		String title = null;
		int copies = -1;
		for (int i = 0; i < coursesAmount; i++) {
			title = jp.get("courses[" + i + "].title");
			if(title.equalsIgnoreCase(courseName)) {
				copies = jp.getInt("courses[" + i + "].copies");
				break;
			}
		}
		System.out.println("Copies of \"" + courseName + "\" course: " + copies);
		
		// 6. Verify if Sum of all Course prices matches with Purchase Amount
		int actualSum = 0;
		for (int i = 0; i < coursesAmount; i++) {
			actualSum += jp.getInt("courses[" + i + "].price") * jp.getInt("courses[" + i + "].copies");
		}
		System.out.println("actualSum: " + actualSum);
		Assert.assertEquals(actualSum, purchaseAmount);
	}

}
