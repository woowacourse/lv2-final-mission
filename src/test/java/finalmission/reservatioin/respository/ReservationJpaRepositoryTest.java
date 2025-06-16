package finalmission.reservatioin.respository;

import static finalmission.omakase.entity.Rating.MIDDLE;
import static finalmission.reservatioin.entity.ReservationTime.LUNCH;
import static org.assertj.core.api.Assertions.assertThat;

import finalmission.customer.entity.Customer;
import finalmission.customer.repository.CustomerJpaRepository;
import finalmission.omakase.entity.Omakase;
import finalmission.omakase.repository.OmakaseJpaRepository;
import finalmission.reservatioin.entity.Reservation;
import finalmission.reservatioin.entity.ReservationWithNumberOfPeople;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ReservationJpaRepositoryTest {

    @Autowired
    ReservationJpaRepository reservationJpaRepository;
    @Autowired
    OmakaseJpaRepository omakaseJpaRepository;
    @Autowired
    CustomerJpaRepository customerJpaRepository;

    @Autowired
    private EntityManager em;

    private Customer customer;
    private Omakase omakase;
    private Reservation reservation;
    private LocalDate date;

    @BeforeEach
    void setUp() {
        customer = new Customer("neo", "우아한네오", "neo@com", "1234");
        omakase = new Omakase("스시준", MIDDLE);
        date = LocalDate.now();
        reservation = new Reservation(customer, omakase, LUNCH, date);

        customerJpaRepository.save(customer);
        omakaseJpaRepository.save(omakase);
    }

    @Test
    @DisplayName("현재 예약 인원을 카운트한다.")
    void countReservation() {
        //given
        reservationJpaRepository.save(reservation);
        em.flush();

        //when
        Long count = reservationJpaRepository
                .countReservationByReservationTimeAndReservationDateAndOmakase(
                        LUNCH, date, omakase
                );

        //then
        assertThat(count).isEqualTo(1L);
    }

    @Test
    @DisplayName("특정 회원의 예약을 조회한다.")
    void findAllByCutomerId() {
        //given
        reservationJpaRepository.save(reservation);
        em.flush();

        //when
        List<Reservation> all = reservationJpaRepository.findAllByCustomerId(customer.getId());

        //then
        assertThat(all).containsExactly(reservation);
    }


    @Test
    @DisplayName("현재 예약 현황을 조회한다.")
    void findCurrentStateReservation() {
        //given
        reservationJpaRepository.save(reservation);
        em.flush();

        //when
        List<ReservationWithNumberOfPeople> all = reservationJpaRepository.findAllWithRank();
        ReservationWithNumberOfPeople first = all.getFirst();

        //then
        assertThat(first.getRank()).isEqualTo(1L);
    }
}
