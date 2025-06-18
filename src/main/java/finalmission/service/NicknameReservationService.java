package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.Nickname;
import finalmission.domain.NicknameReservation;
import finalmission.repository.MemberRepository;
import finalmission.repository.NicknameReservationRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NicknameReservationService {

    private final NicknameReservationRepository nicknameReservationRepository;
    private final MemberRepository memberRepository;

    public NicknameReservationService(
            NicknameReservationRepository nicknameReservationRepository,
            MemberRepository memberRepository
    ) {
        this.nicknameReservationRepository = nicknameReservationRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public NicknameReservation reserve(String name, long memberId) {
        Nickname nickname = makeNickname(name);
        Member member = getMember(memberId);
        NicknameReservation reservation = makeReservation(member, nickname);
        return nicknameReservationRepository.save(reservation);
    }

    private NicknameReservation makeReservation(Member member, Nickname nickname) {
        validateReservationCount(member);
        validateAlreadyConfirmed(member);
        NicknameReservation reservation = new NicknameReservation(member, nickname);
        return reservation;
    }

    private void validateReservationCount(Member member) {
        List<NicknameReservation> nicknameReservations = nicknameReservationRepository.findAllByMember(member);
        if (nicknameReservations.size() > 1) {
            throw new IllegalArgumentException("닉네임 예약은 최대 2개까지만 가능합니다.");
        }
    }

    private void validateAlreadyConfirmed(Member member) {
        List<NicknameReservation> nicknameReservations = nicknameReservationRepository.findAllByMember(member);
        boolean alreadyConfirmed = nicknameReservations.stream()
                .anyMatch(NicknameReservation::isConfirmed);
        if (alreadyConfirmed) {
            throw new IllegalArgumentException("이미 확정된 닉네임이 있습니다.");
        }
    }

    @Transactional
    public void update(String newName, long reservationId, long memberId) {
        NicknameReservation reservation = getReservation(reservationId);
        checkSameMember(reservation, memberId);
        Nickname newNickname = makeNickname(newName);
        reservation.updateNickname(newNickname);
    }

    private Nickname makeNickname(String name) {
        Nickname nickname = new Nickname(name);
        validateDuplicateName(nickname);
        return nickname;
    }

    private void validateDuplicateName(Nickname nickname) {
        List<NicknameReservation> nicknameReservations = nicknameReservationRepository.findAll();
        for (NicknameReservation nicknameReservation : nicknameReservations) {
            if (nicknameReservation.getNickname().equals(nickname)) {
                throw new IllegalArgumentException("이미 예약 중인 닉네임입니다.");
            }
        }
    }

    @Transactional
    public void confirm(long reservationId, long memberId) {
        NicknameReservation reservation = getReservation(reservationId);
        checkSameMember(reservation, memberId);
        reservation.confirm();
        cancelAnotherReservations(reservationId, memberId);
    }

    private void cancelAnotherReservations(long exceptionReservationId, long memberId) {
        findAll().stream()
                .filter(reservation -> reservation.hasSameMemberId(memberId))
                .filter(reservation -> reservation.getId() != exceptionReservationId)
                .forEach(nicknameReservationRepository::delete);
    }

    @Transactional
    public void cancel(long reservationId, long memberId) {
        NicknameReservation reservation = getReservation(reservationId);
        if (reservation.isConfirmed()) {
            throw new IllegalArgumentException("확정된 예약은 취소할 수 없습니다.");
        }
        checkSameMember(reservation, memberId);
        nicknameReservationRepository.delete(reservation);
    }

    private void checkSameMember(NicknameReservation reservation, long memberId) {
        if (!reservation.hasSameMemberId(memberId)) {
            throw new IllegalArgumentException("해당 예약에 대한 권한이 없습니다.");
        }
    }

    private NicknameReservation getReservation(long reservationId) {
        return nicknameReservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약입니다."));
    }

    public List<NicknameReservation> findAll() {
        return nicknameReservationRepository.findAll();
    }

    public List<NicknameReservation> findMine(long memberId) {
        Member member = getMember(memberId);
        return nicknameReservationRepository.findAllByMember(member);
    }

    private Member getMember(long memberId) {
        return memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 멤버입니다."));
    }
}
