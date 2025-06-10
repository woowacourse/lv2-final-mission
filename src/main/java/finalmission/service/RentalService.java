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

    public RentalService(RentalRepository rentalRepository, BookRepository bookRepository,
                         MemberRepository memberRepository) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public List<RentalResponse> getRentals() {
        return rentalRepository.findAll()
                .stream()
                .map(RentalResponse::from)
                .toList();
    }

    public RentalResponse createRental(RentalRequest rentalRequest) {
        Member member = memberRepository.findById(rentalRequest.memberId()).get();
        Book book = bookRepository.findById(rentalRequest.bookId()).get();
        Rental rental = new Rental(member, book, rentalRequest.rentalDate(), rentalRequest.returnDate());
        return RentalResponse.from(rentalRepository.save(rental));
    }
}
