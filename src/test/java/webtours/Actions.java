package webtours;

import com.github.javafaker.Faker;
import io.gatling.javaapi.http.HttpRequestActionBuilder;

import static io.gatling.javaapi.core.CoreDsl.css;
import static io.gatling.javaapi.core.CoreDsl.regex;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class Actions {

    private static final String USERNAME = "John";
    private static final String PASSWORD = "12345";
    static Faker faker = new Faker();
    private static final int DEPARTURE_CITY_INDEX = faker.number().numberBetween(1, 5);
    private static final int ARRIVAL_CITY_INDEX = faker.number().numberBetween(6, 10);
    private static final int FLIGHT_INDEX = faker.number().numberBetween(1, 4);

    public static HttpRequestActionBuilder getMain = http("open main page")
            .get("/welcome.pl")
            .check(regex("Web Tours").exists());

    public static HttpRequestActionBuilder toNavigate = http("go to navigate")
            .get("/nav.pl?in=home")
            .check(regex("Web Tours Navigation Bar").exists())
            .check(css("input[name='userSession']", "value").saveAs("userSessionValue"));

    public static HttpRequestActionBuilder logInStep1 = http("send username and pass")
            .post("/login.pl")
            .formParam("username", USERNAME)
            .formParam("password", PASSWORD)
            .formParam("userSession", "#{userSessionValue}")
            .check(status().is(200))
            .check(regex("User password was correct").exists());

    public static HttpRequestActionBuilder logInStep2 = http("get info after login step 1")
            .get("/nav.pl?page=menu&in=home")
            .check(status().is(200));

    public static HttpRequestActionBuilder logInStep3 = http("get info after login step 2")
            .get("/login.pl?intro=true")
            .check(status().is(200))
            .check(regex("Welcome to Web Tours").exists());

    public static HttpRequestActionBuilder goToSearch = http("go to search")
            .get("/welcome.pl?page=search")
            .check(status().is(200))
            .check(regex("User has returned to the search page").exists());

    public static HttpRequestActionBuilder getFlightsMenu = http("get flights menu")
            .get("/nav.pl?page=menu&in=flights")
            .check(status().is(200))
            .check(css("a[href='welcome.pl?page=search']", "href").exists());

    public static HttpRequestActionBuilder goToReservation = http("go to reservation")
            .get("/reservations.pl?page=welcome")
            .check(status().is(200))
            .check(regex("Flight Selections").exists())
            .check(
                    css("select[name='depart'] option:nth-child(" + DEPARTURE_CITY_INDEX + ")").saveAs("DepartureCity"),
                    css("select[name='depart'] option:nth-child(" + ARRIVAL_CITY_INDEX + ")").saveAs("ArrivalCity")
            );

    public static final HttpRequestActionBuilder createReservation = http("create reservation")
            .post("/reservations.pl")
            .formParam("depart", "#{DepartureCity}")
            .formParam("departDate", "11/03/2024")
            .formParam("arrive", "#{ArrivalCity}")
            .formParam("returnDate", "11/04/2024")
            .formParam("numPassengers", "1")
            .formParam("seatPref", "None")
            .formParam("seatType", "Coach")
            .formParam("findFlights.x", "23")
            .formParam("findFlights.y", "4")
            .formParam(".cgifields", "roundtrip")
            .formParam(".cgifields", "seatType")
            .formParam(".cgifields", "seatPref")
            .check(status().is(200))
            .check(regex("Flight Selections").exists())
            .check(
                    css("input[name='outboundFlight']", "value").find(FLIGHT_INDEX).saveAs("flightChose")
            );

    public static final HttpRequestActionBuilder setFlight = http("choose flight")
            .post("/reservations.pl")
            .formParam("outboundFlight", "#{flightChose}")
            .formParam("numPassengers", "1")
            .formParam("advanceDiscount", "0")
            .formParam("seatType", "Coach")
            .formParam("reserveFlights.x", "20")
            .formParam("reserveFlights.y", "12")
            .check(status().is(200))
            .check(regex("Flight Reservation").exists());

    public static final HttpRequestActionBuilder setPayments = http("choose payments details")
            .post("/reservations.pl")
            .formParam("firstName", USERNAME)
            .formParam("lastName", "Snow")
            .formParam("address1", "Pushkina")
            .formParam("address2", "Moscow")
            .formParam("pass1", "John Snow")
            .formParam("creditCard", "112233445566")
            .formParam("expDate", "02/25")
            .formParam("numPassengers", "1")
            .formParam("seatType", "Coach")
            .formParam("seatPref", "None")
            .formParam("outboundFlight", "#{flightChose}")
            .formParam("advanceDiscount", "0")
            .formParam("JSFormSubmit", "off")
            .formParam("buyFlights.x", "34")
            .formParam("buyFlights.y", "4")
            .formParam(".cgifields", "saveCC")
            .check(status().is(200))
            .check(regex("Thank you for booking through Web Tours").exists());
}
