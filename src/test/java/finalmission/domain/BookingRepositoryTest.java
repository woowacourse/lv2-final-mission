package finalmission.domain;

import static finalmission.TestFixtures.anyDate;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import finalmission.RepositoryTestHelper;
import finalmission.TestFixtures;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(RepositoryTestHelper.class)
class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RepositoryTestHelper helper;

    @Test
    @DisplayName("예약을 저장한다.")
    void save() {
        var booking = new Booking(
            helper.saveAnyMember(),
            helper.saveAnyGym(),
            anyDate()
        );

        var savedBooking = bookingRepository.save(booking);

        assertThat(bookingRepository.findById(savedBooking.getId())).hasValue(booking);
    }

    @Test
    @DisplayName("조회하려는 ID가 없으면 예외가 발생한다.")
    void getById() {
        assertThatThrownBy(() -> bookingRepository.getById(UUID.randomUUID()))
            .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("특정 사용자의 예약을 조회한다.")
    void findAllByMember() {
        var member = helper.saveAnyMember();

        bookingRepository.save(new Booking(member, helper.saveAnyGym(), anyDate()));
        bookingRepository.save(new Booking(member, helper.saveAnyGym(), anyDate()));
        bookingRepository.save(new Booking(member, helper.saveAnyGym(), anyDate()));

        var bookings = bookingRepository.findAllByMember(member);

        assertThat(bookings).hasSize(3);
    }
}
