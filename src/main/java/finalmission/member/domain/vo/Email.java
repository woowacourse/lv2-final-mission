package finalmission.member.domain.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record Email(String value) {
    public Email {
        // TODO: 이메일 정규식 검증
    }
}
