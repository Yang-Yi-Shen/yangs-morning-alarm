package yangs_morning_alarm;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Weather {
    public static void main(String[] args) throws Exception {
        String url = "https://api.open-meteo.com/v1/cma?latitude=22.6163&longitude=120.3133&hourly=temperature_2m,relative_humidity_2m&timezone=Asia%2FSingapore&forecast_days=1";
        
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET() // this is the default
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }
}