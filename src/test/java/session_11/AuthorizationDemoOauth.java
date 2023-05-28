package session_11;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AuthorizationDemoOauth {

	static String accessToken;

	@Test
	public void GetAccessToken() {

		String clientId = "AVccQOnG0HfuBzI6Gib8J0W6qBnpMyPVt0hpqX6InuuPwcKm75bVJ1Nwkfhl7fyq1ps5CzAEvM14V3OW";
		String clientSecret = "EKEUSVwE1IpN0ujXgkbkfE5HAn-zjYCJPDRpyRM-LDrACspnQsd18Tvnh1xcfmRFbT24F31wtYsZa6u5";

		// create request Specification
		RequestSpecification requestSpec = RestAssured.given();

		// specify URL
		requestSpec.baseUri("https://api-m.sandbox.paypal.com");
		requestSpec.basePath("/v1/oauth2/token");

		// Basic authorization
		Response response = requestSpec.auth().preemptive().basic(clientId, clientSecret)
				.param("grant_type", "client_credentials").post();

		response.prettyPrint();

		// print status code & status line
		System.out.println("Response code:" + response.statusCode());
		System.out.println("status line:" + response.statusLine());

		// validate repsonse code
		Assert.assertEquals(response.statusCode(), 200, "check for response code");

		// get access token from response body.

		accessToken = response.getBody().path("access_token");

		System.out.println("access token:" + accessToken);

	}

	@Test(dependsOnMethods = "GetAccessToken")
	public void ListInvoice() {
		// page=3&page_size=4&total_count_required=true
		Response res = RestAssured.given().auth().oauth2(accessToken)
				.queryParam("page", "3")
				.queryParam("page_size", "4")
				.queryParam("total_count_required", "true")
				.get("https://api-m.sandbox.paypal.com/v1/invoicing/invoices");

		System.out.println("\n------------------LIST INVOICE------------------------");

		res.prettyPrint();
		// print status code & status line
		System.out.println("Response code:" + res.statusCode());
		System.out.println("status line:" + res.statusLine());

		// validate repsonse code
		Assert.assertEquals(res.statusCode(), 200, "check for response code");

	}

}
