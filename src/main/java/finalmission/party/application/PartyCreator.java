package finalmission.party.application;

import finalmission.party.domain.Party;
import finalmission.party.infrastructure.PartyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartyCreator {

    private final PartyRepository partyRepository;

    public Party execute() {
        return partyRepository.save(Party.init());
    }
}
