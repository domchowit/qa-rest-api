package com.hello.fresh.rest.automation.framework.api;

import com.hello.fresh.rest.automation.framework.endpoint.BookingEndpoint;
import com.hello.fresh.rest.automation.framework.model.Booking;
import io.restassured.response.Response;

public class BookingApi extends RestClient {

  public BookingApi(String host) {
    super(host);
  }

  public Response getBookings() {
    return doGetAndReturnResponse(BookingEndpoint.BOOKINGS);
  }

  public Response postBooking(Booking booking) {
    return doPostEndReturnResponse(BookingEndpoint.BOOKINGS, booking.toString());
  }

  public Response getBooking(int id) {
    return doGetAndReturnResponse(String.format(BookingEndpoint.BOOKING.path(), id));
  }

  public Response deleteBooking(int id) {
    return doDeleteAndReturnResponse(String.format(BookingEndpoint.BOOKING.path(), id));
  }

}
