package finalmission.application;

import finalmission.domain.AuthenticationException;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Booking modifyDate(final UUID id, final String memberId, final LocalDate dateToModify) {
        if (holidayChecker.isHoliday(dateToModify)) {
            throw new IllegalArgumentException("공휴일에는 예약할 수 없습니다.");
        }
        var member = memberRepository.getById(memberId);
        var booking = bookingRepository.getById(id);
        if (booking.ownedBy(member)) {
            var toModify = BookingDate.ofNew(dateToModify);
            booking.modifyDate(toModify);
            return booking;
        }
        throw new AuthenticationException("수정하려는 사용자가 예약자와 일치하지 않습니다. 현재 사용자 ID : " + memberId + ", 예약자 ID : " + booking.getMember().getId());
    }
}
