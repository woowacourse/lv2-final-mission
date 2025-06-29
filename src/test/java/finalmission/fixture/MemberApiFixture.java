package finalmission.fixture;

import finalmission.member.ui.dto.MemberResponse;
import finalmission.member.ui.dto.SignUpRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import org.springframework.http.HttpStatus;

public class MemberApiFixture {

    public static final List<SignUpRequest> SIGN_UP_REQUESTS = List.of(
            new SignUpRequest("name1", "one@doesnexist.domain", "password1"),
            new SignUpRequest("name2", "two@doesnexist.domain", "password2"),
            new SignUpRequest("name3", "three@doesnexist.domain", "password3"),
            new SignUpRequest("name4", "four@doesnexist.domain", "password4")
    );

    private MemberApiFixture() {
    }

    public static SignUpRequest signUpRequest1() {
        if (SIGN_UP_REQUESTS.isEmpty()) {
            throw new IllegalStateException("회원 픽스처 개수가 부족합니다.");
        }
        return SIGN_UP_REQUESTS.get(0);
    }

    public static SignUpRequest signUpRequest2() {
        if (SIGN_UP_REQUESTS.size() < 2) {
            throw new IllegalStateException("회원 픽스처 개수가 부족합니다.");
        }
        return SIGN_UP_REQUESTS.get(1);
    }

    public static SignUpRequest signUpRequest3() {
        if (SIGN_UP_REQUESTS.size() < 3) {
            throw new IllegalStateException("회원 픽스처 개수가 부족합니다.");
        }
        return SIGN_UP_REQUESTS.get(2);
    }

    public static MemberResponse signUp(final SignUpRequest signUpRequest) {
        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(signUpRequest)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract().as(MemberResponse.class);
    }

    public static List<MemberResponse> signUpMembers(final int count) {
        if (SIGN_UP_REQUESTS.size() < count) {
            throw new IllegalStateException("회원 픽스처의 개수는 최대 " + SIGN_UP_REQUESTS.size() + "개만 가능합니다.");
        }

        return SIGN_UP_REQUESTS.stream()
                .limit(count)
                .map(member -> RestAssured.given().log().all()
                        .contentType(ContentType.JSON)
                        .body(member)
                        .when().post("/members")
                        .then().log().all()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract().as(MemberResponse.class)
                )
                .toList();
    }
}
