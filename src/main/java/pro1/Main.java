package pro1;

import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {

        Properties config = new Properties();
        config.load(new FileInputStream("app.config"));
        int yearFrom = Integer.parseInt(config.getProperty("yearFrom"));
        int yearTo = Integer.parseInt(config.getProperty("yearTo"));
        String room = config.getProperty("room");
        String term = config.getProperty("term");

        HttpClient httpClient = HttpClient
                .newBuilder().build();
        for(int r=yearFrom; r<=yearTo; r++)
        {
            String url = "https://stag-demo.uhk.cz/ws/services/rest2/rozvrhy/getRozvrhByMistnost?semestr="+term+"&rok="+r+"&budova=J&mistnost="+room+"&outputFormat=json";
            HttpRequest httpRequest = HttpRequest
                    .newBuilder().uri(new URI(url))
                    .build();
            HttpResponse<String> response= httpClient.send(
                    httpRequest,
                    HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            Gson gson = new Gson();
            Schedule schedule =
                    gson.fromJson(json, Schedule.class);
            int sum = 0;
            for (ScheduleItem item : schedule.items)
            {
                sum += item.personsCount;
            }
            System.out.println("Rok: "+r+": "+sum+" osob");
        }
    }
}