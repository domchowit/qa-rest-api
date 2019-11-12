package com.hello.fresh.rest.automation.framework.api;

import static io.restassured.RestAssured.given;

import com.hello.fresh.rest.automation.framework.endpoint.Endpoint;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class RestClient {

  protected String baseUrl;

  protected RestClient(String host) {
    this.baseUrl = host;
  }

  protected Response doGetAndReturnResponse(Endpoint endpoint) {
    return given()
        .baseUri(baseUrl)
        .log().method()
        .log().uri()
        .get(endpoint.path()).andReturn();
  }

  protected Response doGetAndReturnResponse(String endpoint) {
    return given()
        .baseUri(baseUrl)
        .log().method()
        .log().uri()
        .get(endpoint).andReturn();
  }

  protected Response doGetWithHeadersAndReturnResponse(String endpoint, Headers headers) {
    Response response = given()
        .baseUri(baseUrl)
        .headers(headers)
        .get(endpoint).andReturn();
    return response;
  }

  protected Response doPostEndReturnResponse(Endpoint endpoint, String body) {
    return given()
        .baseUri(baseUrl)
        .contentType("application/json")
        .body(body)
        .log().method()
        .log().uri()
        .log().body()
        .post(endpoint.path()).andReturn();
  }

  protected Response doDeleteAndReturnResponse(String endpoint) {
    return given()
        .baseUri(baseUrl)
        .log().method()
        .log().uri()
        .log().body()
        .delete(endpoint).andReturn();
  }

}
