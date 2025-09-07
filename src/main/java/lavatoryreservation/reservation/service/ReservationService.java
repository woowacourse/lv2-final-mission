package lavatoryreservation.reservation.service;

import java.time.LocalDateTime;
import java.util.List;
import lavatoryreservation.exception.ReservationException;
import lavatoryreservation.external.nameartist.NamingArtistClient;
import lavatoryreservation.member.domain.Member;
import lavatoryreservation.member.service.MemberService;
import lavatoryreservation.reservation.domain.Reservation;
import lavatoryreservation.reservation.domain.ToiletTime;
import lavatoryreservation.reservation.dto.AddReservationDto;
import lavatoryreservation.reservation.dto.DeleteReservationDto;
import lavatoryreservation.reservation.repository.ReservationRepository;
import lavatoryreservation.toilet.domain.Toilet;
import lavatoryreservation.toilet.service.ToiletService;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final MemberService memberService;
    private final ToiletService toiletService;
    private final NamingArtistClient namingArtistClient;
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository, MemberService memberService,
                              ToiletService toiletService, NamingArtistClient namingArtistClient) {
        this.reservationRepository = reservationRepository;
        this.memberService = memberService;
        this.toiletService = toiletService;
        this.namingArtistClient = namingArtistClient;
    }

    public Long addReservation(AddReservationDto addReservationDto) {
        Member member = memberService.getById(addReservationDto.memberId());
        Toilet toilet = toiletService.getById(addReservationDto.toiletId());
        LocalDateTime startTime = addReservationDto.startTime();
        LocalDateTime endTime = addReservationDto.endTime();
        ToiletTime toiletTime = new ToiletTime(startTime, endTime);
        String alias = namingArtistClient.getNewName();

        toilet.getLavatory().validateUseableMember(member);
        validateContinuousReservationTime(startTime);
        return reservationRepository.save(new Reservation(member, toilet, toiletTime, alias)).getId();
    }

    private void validateContinuousReservationTime(LocalDateTime endTime) {
        LocalDateTime startTime = endTime.minusHours(1L);
        if (reservationRepository.existsByToiletTime_EndTimeBetween(startTime, endTime)) {
            throw new ReservationException("1시간 연속으로 예약할 수 없습니다");
        }
    }

    public void deleteReservation(DeleteReservationDto deleteReservationDto, Long memberId) {
        Member member = memberService.getById(memberId);
        Reservation reservation = getById(deleteReservationDto.reservationId());
        validateSameOwner(reservation, member);
        reservationRepository.deleteById(deleteReservationDto.reservationId());
    }

    private void validateSameOwner(Reservation reservation, Member member) {
        if (!reservation.isSameOwner(member)) {
            throw new ReservationException("예약자가 아닙니다.");
        }
    }

    private Reservation getById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ReservationException("존재하지 않는 예약입니다"));
    }

    public Reservation myReservation(Long id) {
        return reservationRepository.findByMember_id(id)
                .orElseThrow(() -> new ReservationException("존재하지 않는 멤버의 예약입니다"));
    }

    public List<Reservation> allReservations() {
        return reservationRepository.findAll();
    }
}
