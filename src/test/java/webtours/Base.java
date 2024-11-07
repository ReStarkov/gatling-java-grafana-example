package webtours;

import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.http.HttpDsl.http;

public class Base {
    private static final String url = "http://webtours.load-test.ru";
    private static final String port = ":1080";
    private static final String path = "/cgi-bin";
    public static final HttpProtocolBuilder httpProtocolCustom = http
            .baseUrl(url + port + path)
            .acceptLanguageHeader("ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
            .acceptEncodingHeader("gzip, deflate");
}
