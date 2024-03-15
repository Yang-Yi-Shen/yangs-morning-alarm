package yangs_morning_alarm;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        // get weather data and unpack the function return
        ArrayList<Object> weatherData = Weather.getWeatherData();
        double wakeUpTemp = (double) weatherData.get(0);
        int wakeUpHumidity = (int) weatherData.get(1);
        double maxTemp = (double) weatherData.get(2);
        String maxTempTime = (String) weatherData.get(3);
        int avgHumidity = (int) weatherData.get(4);

        String weatherReport = String.format(
                "The current temperature is %.1f degrees. The current humidity is %d percent. Today's temperature will peak at %.2f degrees, at around %s o'clock. The day's average humidity will be %d percent.",
                wakeUpTemp, wakeUpHumidity, maxTemp, maxTempTime, avgHumidity);

        new TextToSpeech(weatherReport);
    }
}