package finalmission;

import finalmission.mungPlan.domain.PlanDate;
import finalmission.mungPlan.domain.Reservation;
import finalmission.mungPlan.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class DBHelper {

    @PersistenceContext
    EntityManager em;

    public Reservation insertReservation(Reservation reservation) {
        if(reservation.getPlanDate().getId() == null) {
            em.persist(reservation.getPlanDate());
        }
        if(reservation.getUser().getId() == null) {
            em.persist(reservation.getUser());
        }
        if(reservation.getId() == null) {
            em.persist(reservation);
        }
        em.flush();
        return reservation;
    }

    public User insertUser(User user) {
        if(user.getId() == null) {
            em.persist(user);
        }
        return user;
    }

    public PlanDate insertPlanDate(PlanDate planDate) {
        if(planDate.getId() == null) {
            em.persist(planDate);
        }
        return planDate;
    }

}
