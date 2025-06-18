package finalmission.dto.request;

import finalmission.entity.Member;
import java.util.Date;

public record CreateTokenRequest(Member member, Date created) {
}
