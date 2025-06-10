package finalmission.repository;

import finalmission.domain.Member;
import finalmission.domain.NicknameReservation;
import java.util.List;
import java.util.Optional;

public interface NicknameReservationRepository {
    NicknameReservation save(NicknameReservation reservation);
    List<NicknameReservation> findAllByMember(Member member);
    List<NicknameReservation> findAll();
    Optional<NicknameReservation> findById(long reservationId);
    void delete(NicknameReservation reservation);
}
