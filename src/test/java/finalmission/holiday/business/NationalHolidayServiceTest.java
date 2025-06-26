package finalmission.holiday.business;

import finalmission.holiday.database.HolidayRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Transactional
class NationalHolidayServiceTest {

    @Autowired
    private NationalHolidayService nationalHolidayService;

    @Autowired
    private HolidayRepository holidayRepository;

    @MockitoBean
    private NationalHolidayRestClient nationalHolidayRestClient;

    @Test
    void 올해의_공휴일을_외부로부터_모두_불러올_수_있다() {
        // Given
        int originalCount = holidayRepository.findAll().size();
        when(nationalHolidayRestClient.getHolidays(anyInt(), anyInt()))
                .thenReturn("""
                        {
                            "response": {
                                "header": {
                                    "resultCode": "00",
                                    "resultMsg": "NORMAL SERVICE."
                                },
                                "body": {
                                    "items": {
                                        "item": [
                                            {
                                                "dateKind": "01",
                                                "dateName": "공휴일1",
                                                "isHoliday": "Y",
                                                "locdate": 20250101,
                                                "seq": 1
                                            },
                                            {
                                                "dateKind": "01",
                                                "dateName": "공휴일2",
                                                "isHoliday": "Y",
                                                "locdate": 20250102,
                                                "seq": 2
                                            }
                                        ]
                                    },
                                    "numOfRows": 10,
                                    "pageNo": 1,
                                    "totalCount": 2
                                }
                            }
                        }
                        """);

        // When
        nationalHolidayService.createNationalHolidaysOfThisYear();

        // Then
        assertThat(holidayRepository.findAll().size()).isEqualTo(originalCount + 24);
    }
}