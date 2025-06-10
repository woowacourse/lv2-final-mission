package finalmission.application;

import finalmission.domain.AuthInfo;
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

    public AuthInfo login(final String id, final String password) {
        return repository.findById(id)
            .filter(m -> m.isSamePassword(password))
            .map(Member::getId)
            .map(AuthInfo::new)
            .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 틀렸습니다."));
    }
}
