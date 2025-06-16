package finalmission.presentation.converter;

import finalmission.domain.Ticket;
import org.springframework.core.convert.converter.Converter;

public class StringToTicketConverter implements Converter<String, Ticket> {

    @Override
    public Ticket convert(final String source) {
        return new Ticket(source.trim());
    }
}
