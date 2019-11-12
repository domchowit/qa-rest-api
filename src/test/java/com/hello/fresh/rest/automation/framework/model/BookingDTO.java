package com.hello.fresh.rest.automation.framework.model;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookingDTO {
  List<Booking> bookings;
}
