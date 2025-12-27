package library.book.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookItem(
        @JsonProperty("title") String title,
        @JsonProperty("author") String author,
        @JsonProperty("isbn") String isbn,
        @JsonProperty("description") String description
) {
}