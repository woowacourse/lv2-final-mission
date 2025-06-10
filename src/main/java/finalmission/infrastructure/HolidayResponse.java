package finalmission.infrastructure;

import java.util.List;
import lombok.Getter;

@Getter
public class HolidayResponse {

    private List<Holiday> holidays;
    private int numOfRows;
    private int pageNo;
    private int totalCount;

    public HolidayResponse(final List<Holiday> holidays, final int numOfRows, final int pageNo, final int totalCount) {
        this.holidays = holidays;
        this.numOfRows = numOfRows;
        this.pageNo = pageNo;
        this.totalCount = totalCount;
    }
}
