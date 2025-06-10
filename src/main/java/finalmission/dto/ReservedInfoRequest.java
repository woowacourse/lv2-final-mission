package finalmission.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservedInfoRequest(String roomName, LocalDate date, LocalTime localTime,String userEmail) {

}
