package yangs_morning_alarm;

import java.net.URI;
import java.net.http.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class Weather {
    // rename main function to getWeatherData later
    public static void main(String[] args) throws Exception {
        String url = "https://api.open-meteo.com/v1/cma?latitude=22.6163&longitude=120.3133&hourly=temperature_2m,relative_humidity_2m&timezone=Asia%2FSingapore&forecast_days=1";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET() // this is the default
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject weatherData = new JSONObject(response.body());
        JSONArray rawTimeArray = weatherData.getJSONObject("hourly").getJSONArray("time");
        JSONArray rawTempArray = weatherData.getJSONObject("hourly").getJSONArray("temperature_2m");
        JSONArray rawHumidityArray = weatherData.getJSONObject("hourly").getJSONArray("relative_humidity_2m");

        // convert time units from <date>T<time> to just <time> format
        String[] parsedTimeArray = new String[rawTimeArray.length()];
        for (int i = 0; i < rawTimeArray.length(); i++) {
            String time = rawTimeArray.getString(i);
            String hour = time.substring(time.indexOf('T') + 1, time.indexOf(':'));
            parsedTimeArray[i] = hour + ":00";
        }

        // turn temperature & humidity arrays into arrays of normal datatypes, as opposed to JSON arrays
        double[] tempArray = new double[rawTempArray.length()];
        for (int i = 0; i < rawTempArray.length(); i++) {
            tempArray[i] = rawTempArray.getDouble(i);
            System.out.println(tempArray[i]);
        }
        int[] humidityArray = new int[rawHumidityArray.length()];
        for (int i = 0; i < rawHumidityArray.length(); i++) {
            humidityArray[i] = rawHumidityArray.getInt(i);
        }
    }
}