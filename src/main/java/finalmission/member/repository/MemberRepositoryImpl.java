package finalmission.member.repository;

import finalmission.member.domain.Member;
import finalmission.member.domain.MemberRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepository {

    private final JpaMemberRepository jpaMemberRepository;

    public MemberRepositoryImpl(final JpaMemberRepository jpaMemberRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
    }

    @Override
    public Member save(final Member member) {
        return jpaMemberRepository.save(member);
    }
}
