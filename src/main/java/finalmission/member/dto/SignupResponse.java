package finalmission.member.dto;

import finalmission.member.model.Member;

public record SignupResponse(
        String message
) {

    public static SignupResponse success(Member member) {
        String name = member.getName();
        return new SignupResponse(name+ "님 회원가입에 성공하였습니다.");
    }
}
