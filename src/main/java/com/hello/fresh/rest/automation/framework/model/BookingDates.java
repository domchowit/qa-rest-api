package com.hello.fresh.rest.automation.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hello.fresh.rest.automation.framework.util.CustomBookingDatesSerializer;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"dateAndTimeFormat"})
@JsonSerialize(using = CustomBookingDatesSerializer.class)
public class BookingDates {

  private Boolean dateAndTimeFormat;
  private Date checkin;
  private Date checkout;
}