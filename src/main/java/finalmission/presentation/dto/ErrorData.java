package finalmission.presentation.dto;

import org.springframework.http.HttpStatus;

public record ErrorData(HttpStatus status, String message) {
}
