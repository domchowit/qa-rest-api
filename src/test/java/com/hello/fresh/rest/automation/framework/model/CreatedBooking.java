package com.hello.fresh.rest.automation.framework.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatedBooking {
  Integer bookingid;
  Booking booking;
}
