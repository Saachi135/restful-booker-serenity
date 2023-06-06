package com.restful.booker.serenity.crudtest;

import com.restful.booker.serenity.restfullinfo.CreateSteps;
import com.restful.booker.serenity.restfullinfo.DeleteSteps;
import com.restful.booker.serenity.restfullinfo.ReadSteps;
import com.restful.booker.serenity.restfullinfo.UpdateSteps;
import com.restful.booker.serenity.testbase.TestBase;
import com.restful.booker.serenity.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class CRUDTest extends TestBase {
    static String username = "admin";
    static String password = "password123";
    static String firstname = "Gauran" + TestUtils.getRandomValue();
    static String lastname = "Pancha";
    static int totalprice = 100;
    static boolean depositpaid = true;
    static String additionalneeds = "Breakfast";
    static String checkin = "2024-01-06";
    static String checkout = "2024-01-20";
    static int bookingid;
    static String token;

    @Steps
    CreateSteps create;

    @Title("Creating token.")
    @Test
    public  void test001(){
        ValidatableResponse response = create.createTest(username,password);
        response.log().all().statusCode(200);
        token = response.extract().path("token");
        System.out.println(token);
    }
    @Title("Creating booking and verifying booking created.")
    @Test
    public void test002(){
       ValidatableResponse response = create.createBooking(firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);
       response.log().all().statusCode(200);
       bookingid = response.extract().path("bookingid");
       HashMap<String, Object> value = response.extract().path("");
       Assert.assertThat(value, hasValue(bookingid));
    }

    @Steps
    ReadSteps readSteps;

    @Title("Getting All IDs.")
    @Test
    public void test003() {
        ValidatableResponse response = readSteps.getAllID();
        response.log().all().statusCode(200);
        List<String> booking = response.extract().path("bookingid");
        Assert.assertTrue(booking.contains(bookingid));
    }

    @Title("Getting Single ID.")
    @Test
    public void test004() {
        ValidatableResponse response = readSteps.getSingleId(bookingid);
        response.log().all().statusCode(200);
        HashMap<String, Object> value = response.extract().path("");
        Assert.assertThat(value, hasValue(firstname));
    }

    @Steps
    UpdateSteps updateSteps;

    @Title("Updating Single ID.")
    @Test
    public void test005() {
        firstname = firstname + "-updated";
        updateSteps.updateBooking(token, bookingid, firstname, lastname, totalprice, depositpaid, checkin, checkout, additionalneeds);
        ValidatableResponse response = updateSteps.getUserbyId(token, bookingid);
        response.log().all().statusCode(200);
        HashMap<String, Object> value = response.extract().path("");
        Assert.assertThat(value, hasValue(firstname));
    }

    @Steps
    DeleteSteps deleteSteps;

    @Title("Deleting the ID and verifying deletion.")
    @Test
    public void test006() {
        deleteSteps.deleteUser(token, bookingid).statusCode(201);
        deleteSteps.getUserById(token, bookingid).statusCode(404);
    }
}
