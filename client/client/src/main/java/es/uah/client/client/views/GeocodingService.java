package es.uah.client.client.views;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GeocodingService {

    private static final String API_KEY = "7b7b23cb1baa42fbb6badd9c00f343fc";

    public static double[] getCoordinates(String location) {
        try {
            String encodeLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);
            System.out.println("encodeLocation: " + encodeLocation);

            String url = "https://api.opencagedata.com/geocode/v1/json?q=" + encodeLocation + "&key=" + API_KEY;

            System.out.println("url: " + url);
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .GET()
                    .build();

            System.out.println("request: " + request);

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.body());

            System.out.println("response: " + response);

            if (jsonNode.has("results") && !jsonNode.get("results").isEmpty()) {
                JsonNode firstResult = jsonNode.get("results").get(0);
                double lat = firstResult.get("geometry").get("lat").asDouble();
                double lng = firstResult.get("geometry").get("lng").asDouble();
                return new double[]{lat, lng};
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
