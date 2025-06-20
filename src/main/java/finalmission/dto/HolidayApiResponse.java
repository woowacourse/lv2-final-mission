package finalmission.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import java.util.List;

@JacksonXmlRootElement(localName = "response")
public record HolidayApiResponse(
        @JacksonXmlProperty(localName = "header")
        Header header,
        @JacksonXmlProperty(localName = "body")
        Body body
) {

    public record Header(
            @JacksonXmlProperty(localName = "resultCode")
            String resultCode,
            @JacksonXmlProperty(localName = "resultMsg")
            String resultMsg
    ) {}

    public record Body(
            @JacksonXmlProperty(localName = "items")
            Items items
    ) {}
    
    public record Items(
            @JacksonXmlElementWrapper(useWrapping = false)
            @JacksonXmlProperty(localName = "item")
            List<HolidayResponse> item
    ) {}
}
