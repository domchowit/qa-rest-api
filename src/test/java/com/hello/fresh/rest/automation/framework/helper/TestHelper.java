package com.hello.fresh.rest.automation.framework.helper;

import com.hello.fresh.rest.automation.framework.api.BookingApi;
import com.hello.fresh.rest.automation.framework.model.Booking;
import com.hello.fresh.rest.automation.framework.model.BookingDates;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.RandomStringUtils;

public class TestHelper {

  private static AtomicInteger atomicInteger;
  private static AtomicLong atomicLong = new AtomicLong(1);

  public static void setStartRoomId(int lastId){
    atomicInteger = new AtomicInteger(lastId +1 );
  }

  public static Booking generateBookings() {
    return Booking
        .builder()
        .bookingdates(getBookingDate())
        .depositpaid(true)
        .firstname(getRandomString(10))
        .lastname(getRandomString(10))
        .roomid(atomicInteger.getAndIncrement())
        .build();
  }

  private static BookingDates getBookingDate() {
    return BookingDates.builder().checkin(getNextDate()).checkout(getNextDate()).dateAndTimeFormat(false).build();
  }

  private static LocalDateTime getNextDate() {
    LocalDateTime date = LocalDateTime.now();
    LocalDateTime increasedDate = date.plusDays(atomicLong.getAndIncrement());
    return increasedDate;
  }

  public static String getRandomString(int length) {
    return RandomStringUtils.random(length, true, false);
  }

}
