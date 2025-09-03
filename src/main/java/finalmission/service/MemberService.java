package finalmission.service;

import finalmission.domain.Member;
import finalmission.dto.request.MemberCreateRequest;
import finalmission.dto.response.MemberResponse;
import finalmission.exception.InvalidRequestException;
import finalmission.exception.NotFoundException;
import finalmission.repository.MemberRepository;
import finalmission.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

    public MemberService(final MemberRepository memberRepository, final ReservationRepository reservationRepository) {
        this.memberRepository = memberRepository;
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public MemberResponse create(final MemberCreateRequest request) {
        final Member savedMember = memberRepository.save(request.toDomain());
        return MemberResponse.from(savedMember);
    }

    public void removeMember(final Long id) {
        if (!memberRepository.existsById(id)) {
            throw new NotFoundException();
        }

        if (reservationRepository.findByMemberId(id)) {
            throw new InvalidRequestException("예약이 이미 존재하고 있어 삭제할 수 없습니다.");
        }

        memberRepository.deleteById(id);
    }

    public Member find(final Long id) {
        return memberRepository.findById(id)
                .orElseThrow(NotFoundException::new);
    }
}
