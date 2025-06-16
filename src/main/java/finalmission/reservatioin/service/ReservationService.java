package finalmission.reservatioin.service;

import finalmission.customer.entity.Customer;
import finalmission.customer.repository.CustomerJpaRepository;
import finalmission.omakase.entity.Omakase;
import finalmission.omakase.repository.OmakaseJpaRepository;
import finalmission.reservatioin.controller.dto.response.CurrentStateReservationResponse;
import finalmission.reservatioin.controller.dto.request.ReservationCreateRequest;
import finalmission.reservatioin.controller.dto.response.ReservationResponse;
import finalmission.reservatioin.entity.Reservation;
import finalmission.reservatioin.entity.ReservationTime;
import finalmission.reservatioin.entity.ReservationWithNumberOfPeople;
import finalmission.reservatioin.respository.ReservationJpaRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationJpaRepository reservationJpaRepository;
    private final OmakaseJpaRepository omakaseJpaRepository;
    private final CustomerJpaRepository customerJpaRepository;

    public ReservationService(
            ReservationJpaRepository reservationJpaRepository,
            OmakaseJpaRepository omakaseJpaRepository,
            CustomerJpaRepository customerJpaRepository
    ) {
        this.reservationJpaRepository = reservationJpaRepository;
        this.omakaseJpaRepository = omakaseJpaRepository;
        this.customerJpaRepository = customerJpaRepository;
    }

    public ReservationResponse save(long id, ReservationCreateRequest request) {
        Customer customer = getCustomerById(id);
        LocalDate date = request.reservationDate();
        ReservationTime time = request.reservationTime();
        Omakase omakase = getOmakaseByName(request.omakaseStoreName());

        validateDuplicateReservationByDateAndTime(time, date, omakase);

        Reservation save = reservationJpaRepository.save(new Reservation(customer, omakase, time, date));
        return ReservationResponse.of(save);
    }

    public void deleteById(Long customerId, Long id) {
        Reservation reservation = reservationJpaRepository.getReferenceById(id);
        if (reservation.isSameCustomerId(customerId)) {
            reservationJpaRepository.deleteById(id);
            return;
        }
        throw new IllegalArgumentException("[ERROR] 본인의 예약만 삭제할 수 있습니다.");
    }

    public List<ReservationResponse> findAllByMemberId(Long id) {
        return ReservationResponse.from(reservationJpaRepository.findAllByCustomerId(id));
    }

    private Customer getCustomerById(Long id) {
        return customerJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 회원입니다. 이름을 다시 확인해 주세요."));
    }

    private Omakase getOmakaseByName(String omakaseStoreName) {
        return omakaseJpaRepository
                .findByStoreName(omakaseStoreName)
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 업장입니다. 업장 이름을 다시 확인해 주세요."));
    }

    private void validateDuplicateReservationByDateAndTime(
            ReservationTime time,
            LocalDate date,
            Omakase omakase
    ) {
        Long count = reservationJpaRepository
                .countReservationByReservationTimeAndReservationDateAndOmakase
                        (time, date, omakase);
        if (count > 3) {
            throw new IllegalArgumentException("[ERROR] 해당 업장의 대기가 마감되었습니다. 다른 일자를 선택해 주세요.");
        }
    }

    public List<CurrentStateReservationResponse> findAllReservationWithNumberOfPeople() {
        List<ReservationWithNumberOfPeople> all = reservationJpaRepository.findAllWithRank();
        return CurrentStateReservationResponse.from(all);
    }
}
