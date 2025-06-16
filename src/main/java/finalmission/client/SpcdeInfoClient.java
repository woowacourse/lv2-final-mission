package finalmission.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.client.dto.SpcdeInfoResponseWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SpcdeInfoClient {

    private static String serviceKey;

    public SpcdeInfoClient(@Value("${spcde-info.service-key}") String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public static SpcdeInfoResponseWrapper holidayInfoAPI(String year, String month) throws java.io.IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo");
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);
        urlBuilder.append("&" + URLEncoder.encode("solYear", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("solMonth", "UTF-8") + "=" + URLEncoder.encode(month, "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("_type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() < 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        return stringToMap(sb.toString());
    }

    public static SpcdeInfoResponseWrapper stringToMap(String json) {
        ObjectMapper mapper = new ObjectMapper();
        SpcdeInfoResponseWrapper response = null;

        try {
            response = mapper.readValue(json, SpcdeInfoResponseWrapper.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
