package finalmission.repository;

import finalmission.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query("SELECT r FROM Rental r WHERE r.member.id = :memberId AND r.book.id = :bookId")
    List<Rental> findByMemberIdAndBookId(@Param("memberId") Long memberId, @Param("bookId") Long bookId);

    @Query("SELECT r FROM Rental r WHERE r.member.id = :memberId")
    List<Rental> findByMemberId(@Param("memberId") Long memberId);
}
