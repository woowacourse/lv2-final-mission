package finalmission.presentation;

import finalmission.application.BookingService;
import finalmission.domain.AuthenticationException;
import finalmission.domain.MemberTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final MemberTokenProvider memberTokenProvider;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void book(HttpServletRequest httpServletRequest, @Valid @RequestBody BookingRequest request) {
        var userId = getUserIdFromCookies(httpServletRequest);
        bookingService.book(
            userId,
            UUID.fromString(request.gymId()),
            request.date()
        );
    }

    private String getUserIdFromCookies(final HttpServletRequest httpServletRequest) {
        var cookies = httpServletRequest.getCookies();
        if (cookies == null) {
            throw new AuthenticationException("로그인이 필요합니다.");
        }

        return Arrays.stream(cookies)
            .filter(c -> c.getName().equals("token"))
            .map(Cookie::getValue)
            .map(memberTokenProvider::extractId)
            .findAny()
            .orElseThrow(() -> new AuthenticationException("로그인이 필요합니다."));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Void> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}


