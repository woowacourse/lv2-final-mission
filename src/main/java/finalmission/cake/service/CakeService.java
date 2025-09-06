package finalmission.cake.service;

import finalmission.cake.dto.AvailableTimeResponse;
import finalmission.cake.dto.BasicCakeResponse;
import finalmission.cake.dto.CakeDetailsResponse;
import finalmission.cake.dto.CakeReservationRequest;
import finalmission.cake.dto.CakeReservationResponse;
import finalmission.cake.dto.MemberCakesResponse;
import finalmission.cake.model.Cake;
import finalmission.cake.model.Flavor;
import finalmission.cake.model.Reservation;
import finalmission.cake.model.ReservationTime;
import finalmission.cake.model.Size;
import finalmission.cake.repository.CakeRepository;
import finalmission.cake.repository.FlavorRepository;
import finalmission.cake.repository.ReservationRepository;
import finalmission.cake.repository.ReservationTimeRepository;
import finalmission.cake.repository.SizeRepository;
import finalmission.exception.BadRequestException;
import finalmission.exception.NotFoundException;
import finalmission.member.model.Member;
import finalmission.member.repository.MemberRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CakeService {

    private final CakeRepository cakeRepository;
    private final FlavorRepository flavorRepository;
    private final SizeRepository sizeRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationTimeRepository reservationTimeRepository;
    private final MemberRepository memberRepository;

    public List<BasicCakeResponse> findAll() {
        List<Cake> cakes = cakeRepository.findAllByIsAvailable(true);
        return cakes.stream()
                .map(cake -> new BasicCakeResponse(cake.getId(), cake.getName(), cake.getDescription(),
                        cake.getPrice()))
                .toList();
    }

    public CakeDetailsResponse findDetails() {
        List<Flavor> flavors = flavorRepository.findAll();
        List<Size> sizes = sizeRepository.findAll();
        return CakeDetailsResponse.of(flavors, sizes);
    }

    public List<AvailableTimeResponse> findCakeAvailableTime(Long cakeId, LocalDate date) {
        List<ReservationTime> bookedTimes = reservationRepository.findByCakeIdAndDate(cakeId, date)
                .stream()
                .map(Reservation::getTime)
                .toList();
        return getAvailableTimes(bookedTimes);
    }

    private List<AvailableTimeResponse> getAvailableTimes(List<ReservationTime> bookedTimes) {
        List<AvailableTimeResponse> availableTimes = new ArrayList<>();
        for (ReservationTime time : reservationTimeRepository.findAll()) {
            if (bookedTimes.contains(time)) {
                availableTimes.add(AvailableTimeResponse.from(time, false));
                continue;
            }
            availableTimes.add(AvailableTimeResponse.from(time, true));
        }
        return availableTimes;
    }

    public CakeReservationResponse create(CakeReservationRequest request, Long memberId) {
        validateReservation(request);
        Reservation reservation = reservationRepository.save(buildReservation(request, memberId));
        return CakeReservationResponse.from(reservation);
    }

    private Reservation buildReservation(CakeReservationRequest request, Long memberId) {
        ReservationTime time = reservationTimeRepository.findById(request.timeId())
                .orElseThrow(NotFoundException::timeNotFound);
        Cake cake = cakeRepository.findById(request.cakeId())
                .orElseThrow(NotFoundException::cakeNotFound);
        Flavor flavor = flavorRepository.findById(request.flavorId())
                .orElseThrow(NotFoundException::flavorNotFound);
        Size size = sizeRepository.findById(request.sizeId())
                .orElseThrow(NotFoundException::sizeNotFound);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NotFoundException::memberNotFound);

        return new Reservation(request.date(), time, member, cake, flavor, size, request.lettering());
    }

    private void validateReservation(CakeReservationRequest request) {
        if (isDateAndTimeInvalid(request.cakeId(), request.date(), request.timeId())) {
            throw BadRequestException.pickUpTimeInvalid();
        }
    }

    private boolean isDateAndTimeInvalid(Long cakeId, LocalDate date, Long timeId) {
        return reservationRepository.findByCakeIdAndDateAndTimeId(cakeId, date, timeId).isPresent();
    }

    public List<MemberCakesResponse> getMemberCakeReservations(Long memberId) {
        List<Reservation> memberReservations = reservationRepository.findByMemberId(memberId);
        return memberReservations.stream()
                .map(MemberCakesResponse::from)
                .toList();
    }

    public void deleteCakeReservation(Long reservationId, Long memberId) {
        Reservation reservation = findReservationByIdAndMemberId(reservationId, memberId);
        reservationRepository.delete(reservation);
    }

    public CakeReservationResponse updateReservation(Long memberId, Long reservationId, CakeReservationRequest cakeReservationRequest) {
        Reservation reservation = findReservationByIdAndMemberId(memberId, reservationId);
        reservation.update(buildReservation(cakeReservationRequest, memberId));
        return CakeReservationResponse.from(reservation);
    }

    private Reservation findReservationByIdAndMemberId(Long cakeId, Long memberId) {
        Optional<Reservation> reservationOptional = reservationRepository.findByIdAndMemberId(cakeId, memberId);
        if (reservationOptional.isEmpty()) {
            throw NotFoundException.reservationNotFound();
        }
        return reservationOptional.get();
    }
}
