package com.hello.fresh.rest.automation.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.hello.fresh.rest.automation.framework.helper.TestHelper;
import com.hello.fresh.rest.automation.framework.model.Booking;
import com.hello.fresh.rest.automation.framework.model.BookingDTO;
import com.hello.fresh.rest.automation.framework.model.BookingDates;
import com.hello.fresh.rest.automation.framework.model.CreatedBooking;
import io.restassured.response.Response;
import java.time.LocalDateTime;
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

    //then
    assertThat(actualBooking.getBookingdates().getCheckin().getYear()).isEqualTo(expectedBooking.getBookingdates().getCheckin().getYear());
    assertThat(actualBooking.getBookingdates().getCheckin().getMonth()).isEqualTo(expectedBooking.getBookingdates().getCheckin().getMonth());
    assertThat(actualBooking.getBookingdates().getCheckin().getDayOfYear()).isEqualTo(expectedBooking.getBookingdates().getCheckin().getDayOfYear());
    assertThat(actualBooking.getBookingdates().getCheckout().getYear()).isEqualTo(expectedBooking.getBookingdates().getCheckout().getYear());
    assertThat(actualBooking.getBookingdates().getCheckout().getMonth()).isEqualTo(expectedBooking.getBookingdates().getCheckout().getMonth());
    assertThat(actualBooking.getBookingdates().getCheckout().getDayOfYear()).isEqualTo(expectedBooking.getBookingdates().getCheckout().getDayOfYear());
    assertThat(actualBooking.getRoomid()).isEqualTo(expectedBooking.getRoomid());
    assertThat(actualBooking.getFirstname()).isEqualTo(expectedBooking.getFirstname());
    assertThat(actualBooking.getDepositpaid()).isEqualTo(expectedBooking.getDepositpaid());
    assertThat(actualBooking.getBookingid()).isNotNull();
    assertThat(actualBooking.getBookingid()).isNotNegative();
    assertThat(response.getStatusCode()).isEqualTo(200);
  }

  @Test
  public void shouldNotBePossibleToBookARoomForTheSameDate() {
    //given
    int ROOM_ID = getNonExistingRoomId();
    LocalDateTime currentDate = LocalDateTime.now();
    Booking bookingOne = TestHelper.generateBookings();
    Booking bookingTwo = TestHelper.generateBookings();

    bookingOne
        .setBookingdates(BookingDates.builder()
            .checkin(currentDate)
            .checkout(currentDate.plusDays(1))
            .dateAndTimeFormat(false)
            .build());
    bookingOne.setRoomid(ROOM_ID);

    bookingTwo
        .setBookingdates(BookingDates.builder()
            .checkin(currentDate)
            .checkout(currentDate.plusDays(10))
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
    LocalDateTime currentDate = LocalDateTime.now();
    Booking expectedBooking = TestHelper.generateBookings();
    expectedBooking
        .setBookingdates(BookingDates.builder()
            .checkin(currentDate)
            .checkout(currentDate.minusDays(1))
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
    LocalDateTime currentDate = LocalDateTime.now();
    Booking expectedBooking = TestHelper.generateBookings();
    expectedBooking
        .setBookingdates(BookingDates.builder()
            .checkin(currentDate)
            .checkout(currentDate.plusDays(1))
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
    LocalDateTime currentDate = LocalDateTime.now();
    Booking expectedBooking = TestHelper.generateBookings();
    expectedBooking
        .setBookingdates(BookingDates.builder()
            .checkin(currentDate)
            .checkout(currentDate.plusDays(1))
            .dateAndTimeFormat(true)
            .build());

    //when
    Response createResponse = bookingApi.postBooking(expectedBooking);

    //then
    assertThat(createResponse.getStatusCode()).isEqualTo(CREATED_BOOKING_CODE);
  }

  //  @Test
  public void deleteTest() {
    //given
    LocalDateTime currentDate = LocalDateTime.now();
    Booking expectedBooking = TestHelper.generateBookings();
    expectedBooking
        .setBookingdates(BookingDates.builder()
            .checkin(currentDate)
            .checkout(currentDate.plusDays(1))
            .dateAndTimeFormat(true)
            .build());

    //when
    CreatedBooking createResponse = bookingApi.postBooking(expectedBooking).as(CreatedBooking.class);

    Response deleteResp = bookingApi.deleteBooking(createResponse.getBookingid());
    //then
    assertThat(deleteResp.getStatusCode()).isEqualTo(200);
  }
}
