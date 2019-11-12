package com.hello.fresh.rest.automation.framework.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.hello.fresh.rest.automation.framework.model.BookingDates;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class CustomBookingDatesSerializer extends StdSerializer<BookingDates> {

  private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
  private SimpleDateFormat dateAndTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

  public CustomBookingDatesSerializer() {
    this(null);
  }

  public CustomBookingDatesSerializer(Class t) {
    super(t);
  }

  @Override
  public void serialize(BookingDates bookingDates, JsonGenerator gen, SerializerProvider arg2)
      throws IOException {
    if (bookingDates.getDateAndTimeFormat()) {
      gen.writeStartObject();
      gen.writeStringField("checkin", dateAndTimeFormatter.format(bookingDates.getCheckin()));
      gen.writeStringField("checkout", dateAndTimeFormatter.format(bookingDates.getCheckout()));
      gen.writeEndObject();
    } else {
      gen.writeStartObject();
      gen.writeStringField("checkin", dateFormatter.format(bookingDates.getCheckin()));
      gen.writeStringField("checkout", dateFormatter.format(bookingDates.getCheckout()));
      gen.writeEndObject();
    }
  }
}

