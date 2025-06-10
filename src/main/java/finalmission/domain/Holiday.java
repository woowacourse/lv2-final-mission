package finalmission.domain;

import java.time.LocalDate;
import java.util.stream.Stream;
import lombok.Getter;

public class Holiday {

    @Getter
    private final LocalDate date;
    @Getter
    private final String dateName;
    private final DateKind dateKind;

    public Holiday(LocalDate date, String dateName, String dateKindCode) {
        this.date = date;
        this.dateName = dateName;
        this.dateKind = DateKind.from(dateKindCode);
    }

    public String getDateKindName() {
        return dateKind.koreanLabel;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "date=" + date +
                ", dateName='" + dateName + '\'' +
                ", dateKind=" + dateKind +
                '}';
    }

    enum DateKind {
        INDEPENDENCE_DAY("1", "국경일"),
        MEMORIAL_DAY("2", "기념일"),
        TWENTY_FOUR_SOLAR_TERMS("3", "24절기"),
        ETC("4", "기타"),
        ;

        private final String code;
        private final String koreanLabel;

        DateKind(String code, String koreanLabel) {
            this.code = code;
            this.koreanLabel = koreanLabel;
        }

        public static DateKind from(String inputCode) {
            return Stream.of(values())
                    .filter(kind -> kind.code.equals(inputCode))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 공휴일 유형입니다."));
        }
    }
}
