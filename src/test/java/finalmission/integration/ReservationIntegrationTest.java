package finalmission.integration;

import static finalmission.helper.DocsFilterFactory.createDocumentFilter;
import static finalmission.helper.RestAssuredRequestUtils.sendDeleteWithFilter;
import static finalmission.helper.RestAssuredRequestUtils.sendGetWithFilter;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import io.restassured.filter.Filter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.payload.FieldDescriptor;

public class ReservationIntegrationTest extends IntegrationTest {

    private static final String DOCS_BASE_DIR = "reservation";

    private static final List<FieldDescriptor> RESERVATION_RESPONSES_FIELDS = List.of(
            fieldWithPath("[].id").description("예약 ID"),
            fieldWithPath("[].lecture.sport").description("운동 종목"),
            fieldWithPath("[].lecture.date").description("수업 날짜"),
            fieldWithPath("[].member.name").description("예약자 이름")
    );

    @Nested
    @DisplayName("예약 API")
    class ReservationApi {

        @Test
        @DisplayName("예약 목록 조회 API")
        void findReservations() {
            Filter filter = createDocumentFilter(DOCS_BASE_DIR, "find-all",
                    responseFields(RESERVATION_RESPONSES_FIELDS));

            sendGetWithFilter("/reservations", spec, filter)
                    .then().log().all().statusCode(200);
        }

        @Test
        @DisplayName("예약 삭제 API")
        void deleteReservation() {
            Filter filter = createDocumentFilter(DOCS_BASE_DIR, "delete",
                    pathParameters(parameterWithName("id").description("예약 ID")));

            sendDeleteWithFilter("/reservations/{id}", spec, filter, 1)
                    .then().statusCode(204);
        }
    }
}
