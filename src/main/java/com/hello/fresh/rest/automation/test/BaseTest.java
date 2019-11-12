package com.hello.fresh.rest.automation.test;

import com.hello.fresh.rest.automation.framework.api.BookingApi;
import com.hello.fresh.rest.automation.framework.conf.ConfigFeeder;
import com.hello.fresh.rest.automation.framework.helper.TestHelper;
import java.util.stream.IntStream;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;


abstract public class BaseTest {

  @Rule
  public TestWatcher watchman = new TestWatcher() {
    @Override
    protected void starting(Description description) {
      System.out.println("------------------------------------------------------------------");
      System.out.println(description.getTestClass() + " " + description.getMethodName() + "()");
    }

    @Override
    protected void finished(Description description) {
      System.out.println("------------------------------------------------------------------");

    }
  };
  protected BookingApi bookingApi;
  protected int CONFLICT_BOOKING_CODE = 409;
  protected int CREATED_BOOKING_CODE = 201;
  private String HOST = ConfigFeeder.INSTANCE.config.getString("maven.host.url");

  @Before
  public void setUp() {
    bookingApi = new BookingApi(HOST);
  }

  protected void feedDBBeforeTest(int amount) {
    IntStream.range(0, amount).forEach(n -> bookingApi.postBooking(TestHelper.generateBookings()));
  }

}
