package finalmission.domain;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    void deleteById(long id);

    List<Reservation> findAll();

    List<Reservation> findAllByMemberId(long memberId);
}
