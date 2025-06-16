package finalmission.application.stunt;

import finalmission.domain.AnniversaryRepository;
import java.time.LocalDate;
import java.util.List;

public class StubAnniversaryRepository implements AnniversaryRepository {

    private final List<LocalDate> anniversaries;

    public StubAnniversaryRepository(final List<LocalDate> anniversaries) {
        this.anniversaries = anniversaries;
    }

    @Override
    public boolean isAnniversary(final LocalDate date) {
        return anniversaries.contains(date);
    }
}
