package session_10;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AuthorizationDemo {
	
	@Test
	public void GetWeatherDataByCity()
	{
		//create request specification
		RequestSpecification requestSpec = RestAssured.given();
		requestSpec.baseUri("https://api.openweathermap.org");
		requestSpec.basePath("/data/2.5/weather");
		requestSpec.queryParam("q", "delhi").queryParam("appid", "64f3e67c615a872833666c014eba0da3");
		Response response = requestSpec.get();
		
		Assert.assertEquals(response.statusCode()/* actual */, 200/* expected */, "check for status code");

		// print status line & response boy
		System.out.println("Responsne status line:" + response.statusLine());
		System.out.println("Response body:" + response.body().asString());

	}

}
