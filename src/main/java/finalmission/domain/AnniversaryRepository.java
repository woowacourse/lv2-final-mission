package finalmission.domain;

import java.time.LocalDate;

public interface AnniversaryRepository {

    boolean isAnniversary(final LocalDate date);
}
