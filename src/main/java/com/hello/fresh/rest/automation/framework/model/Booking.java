package com.hello.fresh.rest.automation.framework.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Booking extends BaseModel {

  int bookingid;
  int roomid;
  String firstname;
  String lastname;
  Boolean depositpaid;
  BookingDates bookingdates;

  public int getBookingid() {
    return bookingid;
  }

  public void setBookingid(int bookingid) {
    this.bookingid = bookingid;
  }

  public int getRoomid() {
    return roomid;
  }

  public void setRoomid(int roomid) {
    this.roomid = roomid;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public Boolean getDepositpaid() {
    return depositpaid;
  }

  public void setDepositpaid(Boolean depositpaid) {
    this.depositpaid = depositpaid;
  }

  public BookingDates getBookingdates() {
    return bookingdates;
  }

  public void setBookingdates(BookingDates bookingdates) {
    this.bookingdates = bookingdates;
  }
}