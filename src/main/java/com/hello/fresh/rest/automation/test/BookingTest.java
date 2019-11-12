package com.hello.fresh.rest.automation.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.hello.fresh.rest.automation.framework.helper.TestHelper;
import com.hello.fresh.rest.automation.framework.model.Booking;
import com.hello.fresh.rest.automation.framework.model.BookingDTO;
import com.hello.fresh.rest.automation.framework.model.BookingDates;
import com.hello.fresh.rest.automation.framework.model.CreatedBooking;
import io.restassured.response.Response;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

public class BookingTest extends BaseTest {

  @Test
  public void shouldReturnAtLeastTwoBookings() {
    //given
    Integer EXPECTED_BOOKINGS_AMOUNT = 2;
    feedDBBeforeTest(10);
    //when
    BookingDTO response = bookingApi.getBookings().andReturn().as(BookingDTO.class);
    int bookingsAmount = response.getBookings().size();
    //then
    assertThat(bookingsAmount).isGreaterThan(EXPECTED_BOOKINGS_AMOUNT);

  }

  @Test
  public void shouldReturnCorrectBooking() {
    //given
    Booking expectedBooking = TestHelper.generateBookings();
    CreatedBooking createResponse = bookingApi.postBooking(expectedBooking).as(CreatedBooking.class);

    //when
    Response response = bookingApi.getBooking(createResponse.getBookingid());
    Booking actualBooking = response.as(Booking.class);
    assertThat(response.getStatusCode()).isEqualTo(200);
  }

  @Test
  public void shouldNotBePossibleToBookARoomForTheSameDate() {
    //given
    int ROOM_ID = 1500;
    Date currentDate = new Date();
    Booking bookingOne = TestHelper.generateBookings();
    Booking bookingTwo = TestHelper.generateBookings();

    bookingOne
        .setBookingdates(BookingDates.builder()
            .checkin(currentDate)
            .checkout(new Date(currentDate.getTime() + TimeUnit.DAYS.toMillis(1)))
            .dateAndTimeFormat(false)
            .build());
    bookingOne.setRoomid(ROOM_ID);

    bookingTwo
        .setBookingdates(BookingDates.builder()
            .checkin(currentDate)
            .checkout(new Date(currentDate.getTime() + TimeUnit.DAYS.toMillis(2)))
            .dateAndTimeFormat(false)
            .build());
    bookingTwo.setRoomid(ROOM_ID);

    //when
    Response createBookingOne = bookingApi.postBooking(bookingOne);
    Response createBookingTwo = bookingApi.postBooking(bookingTwo);

    //then
    assertThat(createBookingOne.getStatusCode()).isEqualTo(CREATED_BOOKING_CODE);
    assertThat(createBookingTwo.getStatusCode()).isEqualTo(CONFLICT_BOOKING_CODE);
  }

  @Test
  public void checkoutShouldBeGraterThanCheckIn() {
    //given
    Date currentDate = new Date();
    Booking expectedBooking = TestHelper.generateBookings();
    expectedBooking
        .setBookingdates(BookingDates.builder()
            .checkin(currentDate)
            .checkout(new Date(currentDate.getTime() - TimeUnit.DAYS.toMillis(1)))
            .dateAndTimeFormat(false)
            .build());

    //when
    Response createResponse = bookingApi.postBooking(expectedBooking);

    //then
    assertThat(createResponse.getStatusCode()).isEqualTo(CONFLICT_BOOKING_CODE);
  }

  @Test
  public void shouldAcceptDateOnlyFormat() {
    //given
    Date currentDate = new Date();
    Booking expectedBooking = TestHelper.generateBookings();
    expectedBooking
        .setBookingdates(BookingDates.builder()
            .checkin(currentDate)
            .checkout(new Date(currentDate.getTime() + TimeUnit.DAYS.toMillis(1)))
            .dateAndTimeFormat(false)
            .build());

    //when
    Response createResponse = bookingApi.postBooking(expectedBooking);

    //then
    assertThat(createResponse.getStatusCode()).isEqualTo(CREATED_BOOKING_CODE);
  }

  @Test
  public void shouldAcceptDateAndTimeFormat() {
    //given
    Date currentDate = new Date();
    Booking expectedBooking = TestHelper.generateBookings();
    expectedBooking
        .setBookingdates(BookingDates.builder()
            .checkin(currentDate)
            .checkout(new Date(currentDate.getTime() + TimeUnit.DAYS.toMillis(1)))
            .dateAndTimeFormat(true)
            .build());

    //when
    Response createResponse = bookingApi.postBooking(expectedBooking);

    //then
    assertThat(createResponse.getStatusCode()).isEqualTo(CREATED_BOOKING_CODE);
  }

  @Test
  public void deleteTest() {
    //given
    Date currentDate = new Date();
    Booking expectedBooking = TestHelper.generateBookings();
    expectedBooking
        .setBookingdates(BookingDates.builder()
            .checkin(currentDate)
            .checkout(new Date(currentDate.getTime() + TimeUnit.DAYS.toMillis(1)))
            .dateAndTimeFormat(true)
            .build());

    //when
    CreatedBooking createResponse = bookingApi.postBooking(expectedBooking).as(CreatedBooking.class);

    Response deleteResp = bookingApi.deleteBooking(createResponse.getBookingid());
    //then
    assertThat(deleteResp.getStatusCode()).isEqualTo(200);
  }
}
