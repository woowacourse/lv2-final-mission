package finalmission.application;

import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;

    public void register(final String name) {
        var member = new Member(name);
        repository.save(member);
    }
}
