package session_09;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BearerToken {

	@Test
	public void BearerToken() {
		
		// https://gorest.co.in/public/v2/users
		// create requsest specification
		RequestSpecification requestSpec = RestAssured.given();

		requestSpec.baseUri("https://gorest.co.in");
		requestSpec.basePath("/public/v2/users");

		JSONObject payload = new JSONObject();
		payload.put("name", "mohityadav");
		payload.put("gender", "male");
		payload.put("email", "mohit123@gmail.com");
		payload.put("status", "Active");

		String AuthToken = "Bearer e08cd54bdacfada13313987d3446ee9f4c4b89afef208db4e7e4cecb056264f2";

		requestSpec.headers("Authorization", AuthToken)
		.contentType(ContentType.JSON)
		.body(payload.toJSONString());

		// perform post request
		Response response = requestSpec.post();

		// validate status code
		Assert.assertEquals(response.statusCode()/* actual */, 201/* expected */, "check for status code");

		// print status line & response boy
		System.out.println("Responsne status line:" + response.statusLine());
		System.out.println("Response body:" + response.body().asString());

	}
}
