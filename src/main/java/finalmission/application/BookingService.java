package finalmission.application;

import finalmission.domain.Booking;
import finalmission.domain.BookingDate;
import finalmission.domain.BookingRepository;
import finalmission.domain.GymRepository;
import finalmission.domain.HolidayChecker;
import finalmission.domain.Member;
import finalmission.domain.MemberRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final MemberRepository memberRepository;
    private final GymRepository gymRepository;
    private final BookingRepository bookingRepository;
    private final HolidayChecker holidayChecker;

    public Booking book(final String memberId, final UUID gymId, final LocalDate date) {
        if (holidayChecker.isHoliday(date)) {
            throw new IllegalArgumentException("공휴일에는 예약할 수 없습니다.");
        }
        var bookingDate = BookingDate.ofNew(date);
        var member = memberRepository.getById(memberId);
        var gym = gymRepository.getById(gymId);
        return bookingRepository.save(new Booking(member, gym, bookingDate));
    }

    public List<Booking> getMyBookings(final String memberId) {
        var member = memberRepository.getById(memberId);
        return bookingRepository.findAllByMember(member);
    }
}
