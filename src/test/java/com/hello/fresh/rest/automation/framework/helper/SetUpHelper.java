package com.hello.fresh.rest.automation.framework.helper;

import com.hello.fresh.rest.automation.framework.conf.ConfigFeeder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

public class SetUpHelper {

  private RequestSpecification reqSpecification;
  private String HOST;

  public SetUpHelper() {
    setHostUrl();
    setGlobalRequestConfiguration();
  }

  private void setHostUrl() {
    this.HOST = ConfigFeeder.INSTANCE.config.getString("maven.host.url");
  }

  private void setGlobalRequestConfiguration() {
    this.reqSpecification = new RequestSpecBuilder()
        .setBaseUri(HOST)
        .addFilter(new ResponseLoggingFilter())
        .build()
        .log().method().log().uri();
  }

  public RequestSpecification getCustomRequestSpecification() {
    return this.reqSpecification;
  }
}
