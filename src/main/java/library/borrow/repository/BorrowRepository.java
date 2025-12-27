package library.borrow.repository;

import java.util.List;
import java.util.Optional;
import library.borrow.domain.Borrow;
import library.member.domain.Member;

public interface BorrowRepository {
    Borrow save(Borrow borrow);
    Optional<Borrow> findById(Long id);
    List<Borrow> findByMember(Member member);
    void delete(Borrow borrow);
} 