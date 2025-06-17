package finalmission.service;

import finalmission.dto.request.RentalRequest;
import finalmission.dto.response.RentalResponse;
import finalmission.entity.Book;
import finalmission.entity.Member;
import finalmission.entity.Rental;
import finalmission.repository.BookRepository;
import finalmission.repository.MemberRepository;
import finalmission.repository.RentalRepository;
import java.util.List;
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

        Member member = memberRepository.findById(rentalRequest.memberId()).get();
        Book book = bookRepository.findById(rentalRequest.bookId()).get();
        Rental rental = new Rental(member, book, rentalRequest.rentalDate(), rentalRequest.returnDate());
        return RentalResponse.from(rentalRepository.save(rental));
    }
}
