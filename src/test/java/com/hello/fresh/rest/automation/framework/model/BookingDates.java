package com.hello.fresh.rest.automation.framework.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hello.fresh.rest.automation.framework.util.CustomBookingDatesSerializer;
import com.hello.fresh.rest.automation.framework.util.LocalDateDeserializer;
import java.time.LocalDateTime;
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
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDateTime checkin;
  @JsonDeserialize(using = LocalDateDeserializer.class)
  private LocalDateTime checkout;
}