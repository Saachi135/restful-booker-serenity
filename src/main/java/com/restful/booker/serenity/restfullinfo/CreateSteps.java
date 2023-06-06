package com.restful.booker.serenity.restfullinfo;

import com.restful.booker.serenity.constants.EndPoints;
import com.restful.booker.serenity.model.AuthorisationPojo;
import com.restful.booker.serenity.model.BookingPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import static io.restassured.RestAssured.given;

public class CreateSteps {

    @Step("Creating token admin :{0}, password :{1}")
    public ValidatableResponse createTest(String admin, String password) {
        AuthorisationPojo authorisationPojo = new AuthorisationPojo();
        authorisationPojo.setUsername(admin);
        authorisationPojo.setPassword(password);
        return given()
                .header("Content-Type", "application/json")
                .when()
                .body(authorisationPojo)
                .post(EndPoints.GET_AUTH)
                .then();
    }

    @Step()
    public ValidatableResponse createBooking(String firstname, String lastname, int totalprice, boolean depositpaid, String checkin, String checkout, String addistionalneeds) {
        BookingPojo.BookingDates dates = new BookingPojo.BookingDates();
        dates.setCheckin(checkin);
        dates.setCheckout(checkout);
        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstName(firstname);
        bookingPojo.setLastName(lastname);
        bookingPojo.setTotalprice(totalprice);
        bookingPojo.setDepositpaid(depositpaid);
        bookingPojo.setBookingdates(dates);
        bookingPojo.setAdditionalneeds(addistionalneeds);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .body(bookingPojo)
                .when()
                .post(EndPoints.GET_BOOKING)
                .then().log().all();
    }
}
