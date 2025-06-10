package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.Nickname;
import finalmission.domain.NicknameReservation;
import finalmission.repository.MemberRepository;
import finalmission.repository.NicknameReservationRepository;
import java.util.List;
import org.springframework.stereotype.Service;

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

    public NicknameReservation reserve(String name, long memberId) {
        Nickname nickname = new Nickname(name);
        validateDuplicateName(nickname);
        Member member = getMember(memberId);
        validateReservationCount(member);
        NicknameReservation reservation = new NicknameReservation(member, nickname);
        return nicknameReservationRepository.save(reservation);
    }

    /**
     * TODO
     * 리팩터링
     */
    private void validateDuplicateName(Nickname nickname) {
        List<NicknameReservation> nicknameReservations = nicknameReservationRepository.findAll();
        for (NicknameReservation nicknameReservation : nicknameReservations) {
            if (nicknameReservation.getNickname().equals(nickname)) {
                throw new IllegalArgumentException("이미 예약 중인 닉네임입니다.");
            }
        }
    }

    private void validateReservationCount(Member member) {
        List<NicknameReservation> nicknameReservations = nicknameReservationRepository.findAllByMember(member);
        if (nicknameReservations.size() > 1) {
            throw new IllegalArgumentException("닉네임 예약은 최대 2개까지만 가능합니다.");
        }
    }

    /**
     * TODO
     * 리팩터링
     */
    public void cancel(long reservationId, long memberId) {
        NicknameReservation reservation = getReservation(reservationId);
        if (!reservation.hasSameMemberId(memberId)) {
            throw new IllegalArgumentException("해당 예약에 대한 삭제 권한이 없습니다.");
        }
        nicknameReservationRepository.delete(reservation);
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
