package finalmission.infrastructure;

import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import finalmission.infrastructure.jpa.MemberJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    @Override
    public Optional<Member> findById(long id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public Optional<Member> findByEmailAndPassword(String email, String password) {
        return memberJpaRepository.findByEmailAndPassword(email, password);
    }
}
