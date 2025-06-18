package finalmission.infrastructure;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

@Component
public class PublicHolidayHandler {

    private final String serviceKey;

    public PublicHolidayHandler(
            @Value("${security.holiday.api.service-key}") String serviceKey
    ) {
        this.serviceKey = serviceKey;
    }

    @SuppressWarnings("deprecation")
    public boolean isPublicHoliday(LocalDate date) {
        try {
            // 공휴일 확인 (공공데이터 API 호출)
            String year = String.valueOf(date.getYear());
            String month = String.format("%02d", date.getMonthValue());

            String urlBuilder = "https://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo" + "?"
                    + URLEncoder.encode("solYear", StandardCharsets.UTF_8) + "="
                    + URLEncoder.encode(year, StandardCharsets.UTF_8)
                    + "&"
                    + URLEncoder.encode("solMonth", StandardCharsets.UTF_8) + "="
                    + URLEncoder.encode(month, StandardCharsets.UTF_8)
                    + "&"
                    + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8)
                    + "=" + serviceKey;

            URL url = new URL(urlBuilder);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(
                            conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300
                                    ? conn.getInputStream() : conn.getErrorStream(), StandardCharsets.UTF_8)
            );

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            // XML 파싱
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            ByteArrayInputStream input = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
            Document document = builder.parse(input);

            XPath xPath = XPathFactory.newInstance().newXPath();
            XPathExpression expr = xPath.compile("/response/body/items/item/locdate/text()");
            NodeList nodeList = (NodeList) expr.evaluate(document, XPathConstants.NODESET);

            String target = date.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
            for (int i = 0; i < nodeList.getLength(); i++) {
                if (target.equals(nodeList.item(i).getTextContent())) {
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // 로그 + fallback 처리
        }

        return false;
    }
}
