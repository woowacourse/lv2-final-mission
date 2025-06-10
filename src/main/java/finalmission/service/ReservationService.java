package finalmission.service;

import finalmission.domain.Member;
import finalmission.domain.WaitingLine;
import finalmission.dto.request.AddWaitingRequest;
import finalmission.dto.response.AddWaitingResponse;
import finalmission.dto.response.MemberResponse;
import finalmission.dto.response.StoreResponse;
import finalmission.infra.auth.LoginMember;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final MemberService memberService;
    private final StoreService storeService;

    public ReservationService(final MemberService memberService, final StoreService storeService) {
        this.memberService = memberService;
        this.storeService = storeService;
    }

    @Transactional
    public AddWaitingResponse addWaiting(AddWaitingRequest addWaitingRequest, LoginMember loginMember) {
        MemberResponse memberResponse = memberService.findById(loginMember.id());

        Member member = new Member(memberResponse.id(), memberResponse.email(), memberResponse.name(),
                memberResponse.memberRole());
        StoreResponse storeResponse = storeService.findById(addWaitingRequest.id());

        WaitingLine waitingLine = new WaitingLine(storeResponse.waitingLineResponse().id(),
                storeResponse.waitingLineResponse().waiting());

        waitingLine.addMember(member);

        int rank = waitingLine.getSequenceByMember(member);
        return new AddWaitingResponse(rank);
    }
}
