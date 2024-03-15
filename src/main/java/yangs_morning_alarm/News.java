package yangs_morning_alarm;

import java.net.URI;
import java.net.http.*;

import org.json.JSONArray;

public class News {
    public static String[] getHeadlines() throws Exception {
        String url = "https://yangs-morning-alarm.vercel.app/api/news";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET() // this is the default
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JSONArray rawHeadlinesArray = new JSONArray(response.body());
        // convert raw headlines array into proper String[]
        String[] headlinesArray = new String[rawHeadlinesArray.length()];
        for (int i = 0; i < rawHeadlinesArray.length(); i++) {
            headlinesArray[i] = rawHeadlinesArray.getString(i);
        }

        return headlinesArray;
    }
}
