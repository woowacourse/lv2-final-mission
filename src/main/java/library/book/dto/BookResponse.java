package library.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record BookResponse(

    @JsonProperty("items") List<BookItem> items
) {

}
