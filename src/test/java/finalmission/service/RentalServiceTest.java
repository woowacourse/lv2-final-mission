package finalmission.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.dto.request.RentalRequest;
import finalmission.dto.response.RentalResponse;
import finalmission.repository.BookRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
class RentalServiceTest {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void 전체_대여_조회() {

        // given & when
        List<RentalResponse> rentals = rentalService.getRentals();

        // then
        assertThat(rentals).hasSize(1);
    }

    @Test
    void 대여_추가() {

        // given
        Long memberId = 1L;
        Long bookId = 2L;
        LocalDate rentalDate = LocalDate.of(2024, 1, 2);
        LocalDate returnDate = LocalDate.of(2024, 1, 16);
        RentalRequest rentalRequest = new RentalRequest(memberId, bookId, rentalDate, returnDate);

        // when
        RentalResponse rental = rentalService.createRental(rentalRequest);

        // then
        assertThat(rental.member().getId()).isEqualTo(memberId);
        assertThat(rental.book().getId()).isEqualTo(bookId);
        assertThat(rental.rentalDate()).isEqualTo(rentalDate);
        assertThat(rental.returnDate()).isEqualTo(returnDate);
    }

    @Test
    void 공휴일에_대여_불가능() {

        // given
        Long memberId = 1L;
        Long bookId = 1L;
        LocalDate rentalDate = LocalDate.of(2024, 1, 1);
        LocalDate returnDate = LocalDate.of(2024, 1, 15);
        RentalRequest rentalRequest = new RentalRequest(memberId, bookId, rentalDate, returnDate);

        // when & then
        assertThatThrownBy(() -> rentalService.createRental(rentalRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 반납_날짜가_대여_날짜_이전일_경우_예외처리() {
        // given
        Long memberId = 1L;
        Long bookId = 1L;
        LocalDate rentalDate = LocalDate.of(2024, 1, 3);
        LocalDate returnDate = LocalDate.of(2024, 1, 2);
        RentalRequest rentalRequest = new RentalRequest(memberId, bookId, rentalDate, returnDate);

        // when & then
        assertThatThrownBy(() -> rentalService.createRental(rentalRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 대여_가능_기간을_초과할_경우_예외처리() {
        // given
        Long memberId = 1L;
        Long bookId = 1L;
        LocalDate rentalDate = LocalDate.of(2024, 1, 2);
        LocalDate returnDate = LocalDate.of(2024, 1, 17);
        RentalRequest rentalRequest = new RentalRequest(memberId, bookId, rentalDate, returnDate);

        // when & then
        assertThatThrownBy(() -> rentalService.createRental(rentalRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 동일한_책_중복_대여_불가능() {
        // given
        Long memberId = 1L;
        Long bookId = 2L;
        LocalDate rentalDate = LocalDate.of(2024, 1, 2);
        LocalDate returnDate = LocalDate.of(2024, 1, 16);
        RentalRequest rentalRequest = new RentalRequest(memberId, bookId, rentalDate, returnDate);

        // when
        rentalService.createRental(rentalRequest);

        // then
        assertThatThrownBy(() -> rentalService.createRental(rentalRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
