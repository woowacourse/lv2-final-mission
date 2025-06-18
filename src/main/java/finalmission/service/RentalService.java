package finalmission.service;

import finalmission.dto.request.RentalRequest;
import finalmission.dto.response.RentalResponse;
import finalmission.entity.Book;
import finalmission.entity.Member;
import finalmission.entity.Rental;
import finalmission.repository.RentalRepository;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final MemberService memberService;
    private final BookService bookService;
    private final HolidayService holidayService;

    public RentalService(RentalRepository rentalRepository, MemberService memberService, BookService bookService,
            HolidayService holidayService) {
        this.rentalRepository = rentalRepository;
        this.memberService = memberService;
        this.bookService = bookService;
        this.holidayService = holidayService;
    }

    public List<RentalResponse> getRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(RentalResponse::from)
                .toList();
    }

    public RentalResponse createRental(RentalRequest rentalRequest) {
        validateHoliday(rentalRequest);
        validateRentalPeriod(rentalRequest);
        validateDuplicateRental(rentalRequest);
        validateRentalLimit(rentalRequest);
        validateAvailableStock(rentalRequest);

        Member member = memberService.findById(rentalRequest.memberId());
        Book book = bookService.findById(rentalRequest.bookId());
        Rental rental = new Rental(member, book, rentalRequest.rentalDate(), rentalRequest.returnDate());
        return RentalResponse.from(rentalRepository.save(rental));
    }

    private void validateHoliday(RentalRequest rentalRequest) {
        if (holidayService.isHoliday(rentalRequest.rentalDate())) {
            throw new IllegalArgumentException("공휴일 및 주말에는 대여가 불가능합니다.");
        }
        if (holidayService.isHoliday(rentalRequest.returnDate())) {
            throw new IllegalArgumentException("공휴일 및 주말에는 반납이 불가능합니다.");
        }
    }

    private void validateRentalPeriod(RentalRequest rentalRequest) {
        LocalDate rentalDate = rentalRequest.rentalDate();
        LocalDate returnDate = rentalRequest.returnDate();
        Long bookId = rentalRequest.bookId();
        if (returnDate.isBefore(rentalDate)) {
            throw new IllegalArgumentException("반납 날짜는 대여 날짜보다 이전일 수 없습니다.");
        }
        int period = bookService.findPeriodById(bookId);

        int betweenDays = Period.between(rentalDate, returnDate).getDays();
        if (betweenDays > period) {
            throw new IllegalArgumentException("대여 가능 날짜를 초과하였습니다.");
        }
    }

    private void validateDuplicateRental(RentalRequest rentalRequest) {
        List<Rental> existingRentals = rentalRepository.findByMemberIdAndBookId(
                rentalRequest.memberId(), rentalRequest.bookId());

        if (!existingRentals.isEmpty()) {
            throw new IllegalArgumentException("동일한 책은 대여 불가능합니다.");
        }
    }

    private void validateRentalLimit(RentalRequest rentalRequest) {
        List<Rental> existingRental = rentalRepository.findByMemberId(rentalRequest.memberId());
        if (existingRental.size() > 3) {
            throw new IllegalArgumentException("대여는 최대 3권까지만 가능합니다.");
        }
    }

    private void validateAvailableStock(RentalRequest rentalRequest) {
        bookService.validateAvailableStock(rentalRequest.bookId());
    }
}
