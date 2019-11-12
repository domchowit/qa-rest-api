package com.hello.fresh.rest.automation.framework.endpoint;


public enum BookingEndpoint implements Endpoint {

  BOOKINGS("/booking/"),
  BOOKING(BOOKINGS.path() + "%d");

  private String endpointPath;

  BookingEndpoint(String endpointPath) {
    this.endpointPath = endpointPath;
  }

  @Override
  public String path() {
    return endpointPath;
  }
}
