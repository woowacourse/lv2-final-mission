package library.member.repository;

import java.util.List;
import library.member.domain.Member;

public interface MemberRepository {
    List<Member> findByEmail(String email);
    Member save(Member member);
}
