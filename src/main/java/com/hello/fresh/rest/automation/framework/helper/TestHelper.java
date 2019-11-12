package com.hello.fresh.rest.automation.framework.helper;

import com.hello.fresh.rest.automation.framework.model.Booking;
import com.hello.fresh.rest.automation.framework.model.BookingDates;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.RandomStringUtils;

public class TestHelper {

  private static AtomicInteger atomicInteger = new AtomicInteger(10000);
  private static AtomicLong atomicLong = new AtomicLong(1);

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
    return BookingDates.builder().checkin(getNextDate()).checkout(getNextDate()).dateAndTimeFormat(true).build();
  }

  private static Date getNextDate() {
    LocalDate date = LocalDate.now();
    LocalDate increasedDate = date.plusDays(atomicLong.getAndIncrement());
    Date convertedDate = Date.from(increasedDate.atStartOfDay()
        .atZone(ZoneId.systemDefault())
        .toInstant());
    return convertedDate;
  }

  public static String getRandomString(int length) {
    return RandomStringUtils.random(length, true, false);
  }

}
