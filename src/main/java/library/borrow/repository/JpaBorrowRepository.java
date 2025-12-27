package library.borrow.repository;

import java.util.List;
import library.borrow.domain.Borrow;
import library.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaBorrowRepository extends BorrowRepository, JpaRepository<Borrow, Long> {
    List<Borrow> findByMember(Member member);
} 