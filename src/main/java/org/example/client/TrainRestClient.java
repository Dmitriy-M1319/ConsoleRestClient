package org.example.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javafaker.Faker;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.dto.TrainStationDto;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Random;

public class TrainRestClient {

    public static final String URL = "http://localhost:8080/train-station/create";
    public final ObjectMapper mapper = new ObjectMapper();

    public final String[] COLORS = {"red", "yellow", "blue", "green", "brown", "purple", "orange"};
    private CloseableHttpClient client;

    public TrainRestClient() {
        mapper.registerModule(new JavaTimeModule());
        client = HttpClients.createDefault();
    }

    public void post() throws IOException {
        TrainStationDto dto = genDto();
        String dtoToJson = mapper.writeValueAsString(dto);
        HttpPost p = new HttpPost(URL);
        StringEntity entity = new StringEntity(dtoToJson);
        p.setEntity(entity);
        p.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = client.execute(p);
        System.out.println(EntityUtils.toString(response.getEntity()));
    }
    public void closeClient() throws IOException
    {
        client.close();
    }

    public TrainStationDto genDto()
    {
        TrainStationDto dto = new TrainStationDto();
        Random r = new Random();
        dto.setStationColor(COLORS[r.nextInt(COLORS.length)]);
        dto.setTrainNumber(r.nextInt());
        Faker faker = new Faker(new Locale("en-US"));
        dto.setStationName(faker.address().city());
        dto.setTimetable(LocalTime.now());
        return dto;
    }
}
