package edu.agh.mobile.contacts.lab.test.rest;


import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

import edu.agh.mobile.contacts.lab.test.Configuration;


public class PositiveBackendTest {

    public static final String NEW_ID = "4";
    public static final String NEW_NAME = "Janosik Górski";

    @Test
    public void forNewUserCreationShouldBeAccepted() {
    	
    	int initialUserNumber = getUserNumber();

        //@formatter:off
        given().
                log().all().
                baseUri(Configuration.CONTACTS_SERVER).
                formParam("id", NEW_ID).
                formParam("name", NEW_NAME).
                formParam("phone", "004 400 400").
                formParam("email", "janosik@tatry.pl").
        when().
                post("/catalog/service/contacts").
        then().
                log().all().
                statusCode(201); //HTTP Created
        //@formatter:on

        int userNumber = getUserNumber();
        String newUserName = getNewUsernName(NEW_ID);

        assertThat(userNumber, greaterThan(initialUserNumber));
        assertThat(newUserName, equalTo(NEW_NAME));
    }

    private int getUserNumber() {
        
        //@formatter:off
        String answer = 
        given().
                log().all().
                baseUri(Configuration.CONTACTS_SERVER).
        when().
                get("/catalog/service/contacts/count/").
        then().
                log().all().
                statusCode(200).extract().asString();
        //@formatter:on
        
        //System.out.println(answer);
        
        return Integer.parseInt(answer);
    }
    
    private String getNewUsernName(String userId) {
        return
        //@formatter:off
        given().
                log().all().
                baseUri(Configuration.CONTACTS_SERVER).
                pathParam("id", userId).
        when().
                get("/catalog/service/contacts/{id}").
        then().
                log().all().
                statusCode(200).extract().jsonPath().getString("name");
        //@formatter:on
    }

}
