package finalmission.member;

import java.util.List;
import finalmission.member.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final RestClient restClient;

    @Autowired
    public MemberService(
            MemberRepository memberRepository,
            RestClient.Builder restClientBuilder,
            @Value("${secret.api-key}") String apiKey) {
        this.memberRepository = memberRepository;
        this.restClient = restClientBuilder
                .baseUrl("https://randommer.io/api")
                .defaultHeader("X-API-KEY", apiKey)
                .build();
    }

    public void createMember(final String phoneNumber) {
        String name = getRandomName();
        Member member = new Member(name, phoneNumber);
        memberRepository.save(member);
    }

    public String getRandomName() {
        List<String> names = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/Name")
                        .queryParam("nameType", "fullname")
                        .queryParam("quantity", "1")
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});

        return names.getFirst();
    }
}
