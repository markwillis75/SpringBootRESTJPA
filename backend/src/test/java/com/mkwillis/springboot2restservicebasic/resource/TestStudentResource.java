package com.mkwillis.springboot2restservicebasic.resource;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by Mark on 22/10/2018.
 */
public class TestStudentResource {

    private final String NAME = "name";
    private final String ID = "id";
    private final String PASSPORT = "passport";

    @BeforeClass
    public static void setDefaultParser(){
        RestAssured.defaultParser = Parser.JSON;
    }

    @Test
    public void testGetStudentsReturnsTwoStudents(){
        get("/students").
                then().
                assertThat().
                body(ID, hasSize(2)).
                body(ID, hasItems(10001, 10002));
    }

    @Test
    public void testGetStudentByValidIdReturnsOneStudent(){
        getStudent("10001").
                then().
                assertThat().
                body(NAME, equalTo("Bob")).
                and().
                body(PASSPORT, equalTo("E12345"));

        getStudent("10002").
                then().
                assertThat().
                body(NAME, equalTo("Sam")).
                and().
                body(PASSPORT, equalTo("E67890"));
    }

    @Test
    public void testGetStudentByInvalidIdReturns500(){
        getStudent("100").
                then().statusCode(500);
    }

    @Test
    public void testPostStudentCreatesNewStudent(){
        String name = "jack";
        String passport = "fake";

        Response response = createStudent(name, passport);
        response.then().statusCode(201);

        String location = response.getHeader("location");
        String id = getStudentIDFromHeader(response);

        Assert.assertNotNull(location);
        Assert.assertNotNull(id);

        getStudent(id).
                then().
                assertThat().
                statusCode(200).
                body(NAME, equalTo(name)).
                body(PASSPORT, equalTo(passport));

        deleteStudent(id);
    }

    @Test
    public void testUpdateStudentWithValidId(){

        String name = "jack";
        String passport = "fake";

        Response response = createStudent(name, passport);
        response.then().assertThat().statusCode(201);

        String id = getStudentIDFromHeader(response);

        name = "jane";
        passport = "legit";
        updateStudent(id, name, passport);

        response = getStudent(id);

        response.
                then().
                assertThat().
                statusCode(200).
                body(NAME, equalTo(name)).
                body(PASSPORT, equalTo(passport));

        deleteStudent(id);
    }

    @Test
    public void testDeleteStudentWithValidId(){
        String name = "Dave";
        String passport = "99999";

        Response response = createStudent(name, passport);
        String id = getStudentIDFromHeader(response);

        response = getStudent(id);
        response.
                then().
                assertThat().
                body(NAME, equalTo(name)).
                body(PASSPORT, equalTo(passport));

        deleteStudent(id);

        response = getStudent(id);
        response.
                then()
                .assertThat().
                statusCode(500);
    }



    //==================================================================================================================
    // Boiler plate
    //==================================================================================================================

    private String getStudentIDFromHeader(Response response){
        return response.getHeader("location").substring(response.getHeader("location").lastIndexOf('/') + 1);
    }

    private Response getStudent(String id){
        return get("/students/" + id);
    }

    private Response deleteStudent(String id){
        return delete("/students/" + id);
    }

    private Response updateStudent(String id, String name, String passport){
        JSONObject requestParams = new JSONObject();
        requestParams.put(NAME, name);
        requestParams.put(PASSPORT, passport);

        RequestSpecification request = given();

        request.header("Content-Type", "application/json");
        request.body(requestParams.toJSONString());

        return request.put("/students/" + id);
    }

    private Response createStudent(String name, String passport){
        JSONObject requestParams = new JSONObject();
        requestParams.put(NAME, name);
        requestParams.put(PASSPORT, passport);

        RequestSpecification request = given();

        request.header("Content-Type", "application/json").
                body(requestParams.toString());

        return request.post("/students");
    }
}