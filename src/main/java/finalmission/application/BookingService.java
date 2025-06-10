package finalmission.application;

import finalmission.domain.Booking;
import finalmission.domain.BookingDate;
import finalmission.domain.BookingRepository;
import finalmission.domain.GymRepository;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final MemberRepository memberRepository;
    private final GymRepository gymRepository;
    private final BookingRepository bookingRepository;

    public Booking book(final String memberId, final UUID gymId, final LocalDate date) {
        var bookingDate = BookingDate.ofNew(date);
        var member = memberRepository.getById(memberId);
        var gym = gymRepository.getById(gymId);
        var booking = new Booking(member, gym, bookingDate);
        return bookingRepository.save(booking);
    }
}
