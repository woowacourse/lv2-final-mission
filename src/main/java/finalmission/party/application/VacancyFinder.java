package finalmission.party.application;

import finalmission.party.domain.Party;
import finalmission.party.domain.PartyStatus;
import finalmission.party.infrastructure.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacancyFinder {

    private final PartyRepository partyRepository;

    public Long execute() {
        final Party oldestVacancy = partyRepository.findTop1ByPartyStatusOrderByCreatedAtAsc(PartyStatus.OPEN)
                .orElseThrow(() -> new NoVacancyException("파티 빈자리 없으니까 새로운 파티를 만들어야 해"));

        oldestVacancy.requireOpen();

        return oldestVacancy.getId();
    }
}
