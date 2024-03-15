package yangs_morning_alarm;

import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class Weather {
    public static ArrayList<Object> getWeatherData() throws Exception {
        String url = "https://api.open-meteo.com/v1/cma?latitude=22.6163&longitude=120.3133&hourly=temperature_2m,relative_humidity_2m&timezone=Asia%2FSingapore&forecast_days=1";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET() // this is the default
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONObject rawWeatherData = new JSONObject(response.body());
        JSONArray rawTimeArray = rawWeatherData.getJSONObject("hourly").getJSONArray("time");
        JSONArray rawTempArray = rawWeatherData.getJSONObject("hourly").getJSONArray("temperature_2m");
        JSONArray rawHumidityArray = rawWeatherData.getJSONObject("hourly").getJSONArray("relative_humidity_2m");

        // convert time units from <date>T<time> to just <time> format
        String[] parsedTimeArray = new String[rawTimeArray.length()];
        for (int i = 0; i < rawTimeArray.length(); i++) {
            String time = rawTimeArray.getString(i);
            String hour = time.substring(time.indexOf('T') + 1, time.indexOf(':'));
            parsedTimeArray[i] = hour + ":00";
        }

        // turn temperature & humidity arrays into arrays of normal datatypes, as
        // opposed to JSON arrays
        double[] tempArray = new double[rawTempArray.length()];
        for (int i = 0; i < rawTempArray.length(); i++) {
            tempArray[i] = rawTempArray.getDouble(i);
        }
        int[] humidityArray = new int[rawHumidityArray.length()];
        for (int i = 0; i < rawHumidityArray.length(); i++) {
            humidityArray[i] = rawHumidityArray.getInt(i);
        }

        // find highest temperature of the day & time of highest temperature of the day, and temperature when I wake up
        double maxTemp = 0;
        String maxTempTime = "00:00";
        double wakeUpTemp;

        for (int i = 0; i < tempArray.length; i++) {
            if (tempArray[i] > maxTemp) {
                maxTemp = tempArray[i];
                maxTempTime = parsedTimeArray[i];
            }
        }
        wakeUpTemp = tempArray[7];

        // find avg. humidity of the day, and humidity when I wake up
        int avgHumidity;
        int wakeUpHumidity;
        int totalHumidity = 0;

        for (int humidity : humidityArray) {
            totalHumidity += humidity;
        }
        avgHumidity = totalHumidity / humidityArray.length;
        wakeUpHumidity = humidityArray[7];

        ArrayList<Object> weatherData = new ArrayList<Object>();
        weatherData.add(wakeUpTemp);
        weatherData.add(wakeUpHumidity);
        weatherData.add(maxTemp);
        weatherData.add(maxTempTime);
        weatherData.add(avgHumidity);

        return weatherData;
    }
}