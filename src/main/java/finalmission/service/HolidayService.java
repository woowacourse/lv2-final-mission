package finalmission.service;

import finalmission.util.HolidayClient;
import java.time.LocalDate;
import org.springframework.stereotype.Service;

@Service
public class HolidayService {

    HolidayClient holidayClient;

    public HolidayService(HolidayClient holidayClient) {
        this.holidayClient = holidayClient;
    }

    public boolean checkHolyDay(LocalDate localDate) {
        String str = holidayClient.getHolidays(localDate);
        System.out.println("given!!!:"+str);
        String findWords =  String.format("\"dateKind\":\"%02d\"",localDate.getDayOfMonth());
        System.out.println("finding:"+findWords);
        return str.contains(findWords);
    }
}
