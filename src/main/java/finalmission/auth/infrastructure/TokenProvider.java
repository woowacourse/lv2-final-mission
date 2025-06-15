package finalmission.auth.infrastructure;

import finalmission.member.domain.Member;

public interface TokenProvider {

    String issue(Member member);
}
