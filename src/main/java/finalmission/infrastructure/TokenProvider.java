package finalmission.infrastructure;

import finalmission.domain.Member;

public interface TokenProvider {

    String issue(Member member);
}
