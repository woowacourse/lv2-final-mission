package finalmission.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public record HolidayResponse(
        @JacksonXmlProperty(localName = "locdate")
        String locdate,
        
        @JacksonXmlProperty(localName = "isHoliday")
        String isHoliday,
        
        @JacksonXmlProperty(localName = "dateName")
        String dateName
) {
}
