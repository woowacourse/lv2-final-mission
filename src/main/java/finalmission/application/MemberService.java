package finalmission.application;

import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository repository;

    public void register(final String id, final String password, final String name) {
        var member = new Member(id, password, name);
        repository.save(member);
    }
}
