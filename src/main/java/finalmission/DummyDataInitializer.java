package finalmission;

import finalmission.business.model.entity.Member;
import finalmission.business.model.entity.Reservation;
import finalmission.business.service.AuthService;
import finalmission.business.service.MemberService;
import finalmission.business.service.ReservationService;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("local")
@RequiredArgsConstructor
public class DummyDataInitializer {
    private final MemberService memberService;
    private final ReservationService reservationService;
    private final AuthService authService;

    @PostConstruct
    public void initialize() {
        Member ddiyong = Member.create("띠용", "ddiyong@gmail.com", "1234");
        String passportId1 = "DDI1YONG2";
        Member nakamura = Member.create("나까무라", "nkmr@gmail.com", "1234");
        String passportId2 = "NKMRNKMR1";
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
        LocalDateTime afterTomorrow = tomorrow.plusDays(1);
        String incheon = "인천국제공항";
        String osaka = "간사이국제공항";
        String flightCode = "BHANG123";
        Reservation reservation = Reservation.create(ddiyong, passportId1,
                tomorrow, tomorrow.plusHours(2), incheon, osaka,
                flightCode);
        Reservation reservation2 = Reservation.create(ddiyong, passportId1,
                tomorrow.plusHours(2), tomorrow.plusHours(4), osaka, incheon,
                flightCode);

        Reservation reservation3 = Reservation.create(nakamura, passportId2,
                tomorrow.plusHours(2), tomorrow.plusHours(4), osaka, incheon,
                flightCode);

        Reservation reservation4 = Reservation.create(nakamura, passportId2,
                afterTomorrow, afterTomorrow.plusHours(2), incheon, osaka,
                flightCode);

        saveAllMember(ddiyong, nakamura);
        saveAllReservation(reservation, reservation2, reservation3, reservation4);
    }

    private void saveAllMember(final Member... members) {
        for (Member member : members) {
            memberService.save(member);
        }
    }

    private void saveAllReservation(final Reservation... reservations) {
        for (Reservation reservation : reservations) {
            reservationService.save(reservation);
        }
    }

}
