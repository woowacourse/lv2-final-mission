package finalmission.service;

import finalmission.domain.Nickname;
import finalmission.service.dto.NicknameGenerateCondition;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.Builder;

@Primary
@Component
public class RandomNicknameGenerator implements NicknameGenerator {

    private final RestClient restClient;

    public RandomNicknameGenerator(@Qualifier("randomNicknameGenerator") Builder builder) {
        this.restClient = builder.build();
    }

    public List<Nickname> generate(NicknameGenerateCondition condition) {
        validateCondition(condition);
        String response = requestGenerateNicknames(condition);
        return parseNicknames(response);
    }

    private void validateCondition(NicknameGenerateCondition condition) {
        if (condition.length() < 2 || condition.length() > 4) {
            throw new IllegalArgumentException("최소 2글자, 최대 4글자의 닉네임만 추천할 수 있습니다.");
        }
    }

    private String requestGenerateNicknames(NicknameGenerateCondition condition) {
        return restClient.post()
                .uri("https://www.dailyest.co.kr/contents/character.ajax.php?o_num={length}", condition.length())
                .contentType(MediaType.TEXT_HTML)
                .retrieve()
                .body(String.class);
    }

    private List<Nickname> parseNicknames(String response) {
        String tagRemoved = response.replaceAll("<[^>]*>", ";");
        StringTokenizer stringTokenizer = new StringTokenizer(tagRemoved, ";");
        List<Nickname> generatedNicknames = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            String name = stringTokenizer.nextToken();
            generatedNicknames.add(new Nickname(name));
        }
        return generatedNicknames;
    }
}
