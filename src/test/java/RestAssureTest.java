import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;


public class RestAssureTest {
    Properties properties;
    private static final Logger log = Logger.getLogger(RestAssureTest.class.getName());

    @Test
    public void Test1() {

        RestAssured.baseURI = "https://maps.googleapis.com";

        //given when then extract

        given()
                .param("location", "-33.8670522,151.1957362")
                .param("radius", "1500")
                .param("key", "AIzaSyBZYdoX1VLx1xWP643haPHX3PaJ7-SanbM")
                .when().get("/maps/api/place/nearbysearch/json").then().assertThat()
                .statusCode(200).and().contentType(ContentType.JSON).and().body("results[0].name", Matchers.equalTo("Sydney"))
                .and().body("results[0].id", Matchers.equalTo("044785c67d3ee62545861361f8173af6c02f4fae"))
                .and().header("server", "scaffolding on HTTPServer2");
//                .header("ddd","sss").cookie("ss","ddd").body("sss","ssss");


    }

    @Test
    public void postData() {


        RestAssured.baseURI = "http://216.10.245.166";

        given().

                queryParam("key", "qaclick123").

                body("{" +

                        "\"location\": {" +

                        "\"lat\": -33.8669710," +

                        "\"lng\": 151.1958750" +

                        "}," +

                        "\"accuracy\": 50," +

                        "\"name\": \"Google Shoes!\"," +

                        "\"phone_number\": \"(02) 9374 4000\"," +

                        "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\"," +

                        "\"types\": [\"shoe_store\"]," +

                        "\"website\": \"http://www.google.com.au/\"," +

                        "\"language\": \"en-AU\"" +

                        "}").

                when().

                post("/maps/api/place/add/json").

                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().

                body("status", Matchers.equalTo("OK"));

// Create a place =response (place id)

// delete Place = (Request - Place id)


    }


    @BeforeTest
    public void getData() throws IOException {
        properties = new Properties();
        FileInputStream inputStream = new FileInputStream("src\\test\\files\\env.properties");
        properties.load(inputStream);
        properties.getProperty("HOST");


    }

    @Test
    public void grabData() {

        // Task 1 grab the response

        RestAssured.baseURI = properties.getProperty("HOST");
        System.out.println(properties.getProperty("HOST"));

        Response res = given().

                queryParam("key", properties.getProperty("KEY")).log().all().

                body(payLoad.getPostData()).

                when().

                post(Data.postDataRest()).

                then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().

                body("status", Matchers.equalTo("OK")).extract().response();

        String responseString = res.asString();

        System.out.println(responseString);


        //Task 2:  Grab the reference from response

        JsonPath js = new JsonPath(responseString);
        String reference = js.get("reference");
        System.out.println(reference);
        log.info("farukakyol");

        int count = js.get("reference.size()");
        log.info(count);


        for(int i = 0 ; i < count;i++){

            log.info(js.get("reference["+i+"]"));

        }






    }
}

