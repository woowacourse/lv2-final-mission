package finalmission.facade.application;

import finalmission.party.application.NoVacancyException;
import finalmission.party.application.PartyCreator;
import finalmission.party.application.VacancyFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacancyGetter {

    private final VacancyFinder vacancyFinder;
    private final PartyCreator partyCreator;

    public Long execute() {
        try {
            return vacancyFinder.execute();
        } catch (final NoVacancyException e) {
            return partyCreator.execute().getId();
        }
    }
}
