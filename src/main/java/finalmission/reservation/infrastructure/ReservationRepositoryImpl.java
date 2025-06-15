package finalmission.reservation.infrastructure;

import finalmission.reservation.domain.Reservation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationRepositoryImpl implements ReservationRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Reservation> findByToiletIdAndDate(Long toiletId, LocalDate date) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reservation> cq = cb.createQuery(Reservation.class);
        Root<Reservation> reservation = cq.from(Reservation.class);

        List<Predicate> predicates = getConditions(cb, reservation, toiletId, date);

        cq.select(reservation)
                .where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(cq).getResultList();
    }

    private static List<Predicate> getConditions(CriteriaBuilder cb, Root<Reservation> reservation, Long toiletId,
                                                 LocalDate date) {
        List<Predicate> predicates = new ArrayList<>();
        if (toiletId != null) {
            predicates.add(cb.equal(reservation.get("toilet").get("id"), toiletId));
        }
        if (date != null) {
            predicates.add(cb.equal(reservation.get("date"), date));
        }
        return predicates;
    }
}
