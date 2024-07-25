package es.uah.client.client.views;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserLocationService {

    private static final String API_URL = "http://ip-api.com/json/";

    public static double[] getUserLocation() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(API_URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.body());

            if ("success".equals(jsonNode.get("status").asText())) {
                double lat = jsonNode.get("lat").asDouble();
                double lon = jsonNode.get("lon").asDouble();
                return new double[]{lat, lon};
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

