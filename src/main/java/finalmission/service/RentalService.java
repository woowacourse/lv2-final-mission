package finalmission.service;

import finalmission.dto.request.RentalRequest;
import finalmission.dto.response.RentalResponse;
import finalmission.entity.Book;
import finalmission.entity.Member;
import finalmission.entity.Rental;
import finalmission.repository.BookRepository;
import finalmission.repository.MemberRepository;
import finalmission.repository.RentalRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final HolidayService holidayService;

    public RentalService(RentalRepository rentalRepository, BookRepository bookRepository,
            MemberRepository memberRepository, HolidayService holidayService) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.holidayService = holidayService;
    }

    public List<RentalResponse> getRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(RentalResponse::from)
                .toList();
    }

    public RentalResponse createRental(RentalRequest rentalRequest) {
        if (holidayService.isHoliday(rentalRequest.rentalDate())) {
            throw new IllegalArgumentException("공휴일에는 대여가 불가능합니다.");
        }

        validateRentalPeriod(rentalRequest);

        Member member = memberRepository.findById(rentalRequest.memberId()).get();
        Book book = bookRepository.findById(rentalRequest.bookId()).get();
        Rental rental = new Rental(member, book, rentalRequest.rentalDate(), rentalRequest.returnDate());
        bookRepository.removeOneStockById(book.getId());
        return RentalResponse.from(rentalRepository.save(rental));
    }

    public void validateRentalPeriod(RentalRequest rentalRequest) {
        LocalDate rentalDate = rentalRequest.rentalDate();
        LocalDate returnDate = rentalRequest.returnDate();
        Long bookId = rentalRequest.bookId();
        if(returnDate.isBefore(rentalDate)) {
            throw new IllegalArgumentException("반납 날짜는 대여 날짜보다 이전일 수 없습니다.");
        }
        int period = bookRepository.findPeriodById(bookId).get();
        int betweenDays = Period.between(rentalDate, returnDate).getDays();
        if(betweenDays >  period) {
            throw new IllegalArgumentException("대여 가능 날짜를 초과하였습니다.");
        }
    }
}
