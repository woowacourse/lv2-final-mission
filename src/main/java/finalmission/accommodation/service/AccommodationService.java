package finalmission.accommodation.service;

import finalmission.accommodation.domain.Accommodation;
import finalmission.accommodation.dto.AccommodationResponse;
import finalmission.accommodation.dto.CreateAccommodationRequest;
import finalmission.accommodation.repository.AccommodationRepository;
import finalmission.global.DataNotFoundException;
import finalmission.global.ForbiddenException;
import finalmission.member.domain.Member;
import finalmission.reservation.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final ReservationRepository reservationRepository;

    public AccommodationService(AccommodationRepository accommodationRepository,
                                ReservationRepository reservationRepository) {
        this.accommodationRepository = accommodationRepository;
        this.reservationRepository = reservationRepository;
    }

    public AccommodationResponse create(Member admin, CreateAccommodationRequest request) {
        Accommodation accommodation = new Accommodation(
                request.name(),
                request.description(),
                request.address(),
                admin
        );
        return AccommodationResponse.of(accommodationRepository.save(accommodation));
    }

    @Transactional
    public void delete(Member admin, long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 숙소입니다."));

        if (!accommodation.getUser().equals(admin)) {
            throw new ForbiddenException("숙소를 삭제할 권한이 없습니다.");
        }

        if (reservationRepository.existsByAccommodation(accommodation)) {
            throw new IllegalArgumentException("예약이 존재하는 숙소는 삭제할 수 없습니다.");
        }

        accommodationRepository.delete(accommodation);
    }
}
