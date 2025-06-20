package finalmission.external;

import java.util.Arrays;
import java.util.Objects;

public enum HolidayApiResultCode {

    SUCCESS("00", "성공"),

    APPLICATION_ERROR("01", "어플리케이션 에러"),
    NO_OPENAPI_SERVICE_ERROR("12", "해당 오픈 API 서비스가 없거나 폐기됨"),
    SERVICE_ACCESS_DENIED_ERROR("20", "서비스 접근거부"),
    SERVICE_KEY_IS_NOT_REGISTERED_ERROR("30", "등록되지 않은 서비스키"),
    DEADLINE_HAS_EXPIRED_ERROR("31", "활용기간 만료"),
    UNREGISTERED_IP_ERROR("32", "등록되지 않은 IP"),

    HTTP_ERROR("04", "HTTP 에러"),
    LIMITED_NUMBER_OF_SERVICE_REQUESTS_EXCEEDS_ERROR("22", "서비스 요청제한횟수 초과에러"),
    LIMITED_NUMBER_OF_SERVICE_REQUESTS_PER_SECOND_EXCEEDS_ERROR("23", "최대동시 요청수 초과에러"),

    UNKNOWN_ERROR("99", "기타에러");

    private final String code;
    private final String description;

    HolidayApiResultCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static HolidayApiResultCode fromCode(final String code) {
        return Arrays.stream(HolidayApiResultCode.values())
                .filter(holidayApiResultCode -> Objects.equals(holidayApiResultCode.code, code))
                .findAny()
                .orElse(UNKNOWN_ERROR);
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
