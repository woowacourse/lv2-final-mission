package finalmission.service;

import finalmission.dto.MemberRegisterDto;
import finalmission.dto.NameGenerateRequestDto;
import finalmission.infrastructure.randommer.RandommerRestClient;
import finalmission.model.Member;
import finalmission.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    public static final int RANDOM_NAME_DEFAULT_QUANTITY = 10;

    private final MemberRepository memberRepository;
    private final RandommerRestClient randommerRestClient;

    public void signUp(MemberRegisterDto memberRegisterDto) {
        if (memberRegisterDto.name() == null) {
            String randomName = randommerRestClient.generateSingleName();
            Member member = memberRegisterDto.toMember(randomName);
            memberRepository.save(member);
            return;
        }

        Member member = memberRegisterDto.toMember();
        memberRepository.save(member);
    }

    public List<String> getRandomNames(int quantity) {
        if (quantity == 0) {
            quantity = RANDOM_NAME_DEFAULT_QUANTITY;
        }
        return randommerRestClient.generateNames(new NameGenerateRequestDto(quantity));
    }
}
