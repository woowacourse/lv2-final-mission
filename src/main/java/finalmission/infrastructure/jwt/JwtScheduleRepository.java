package finalmission.infrastructure.jwt;

import finalmission.domain.Room;
import finalmission.domain.Schedule;
import finalmission.domain.ScheduleRepository;
import finalmission.domain.Ticket;
import finalmission.infrastructure.jwt.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class JwtScheduleRepository implements ScheduleRepository {

    private static final String ROOM = "room";
    private static final String DATE = "date";
    private static final String TIME = "time";

    private final SecretKey secretKey;

    public JwtScheduleRepository(final JwtProperties jwtProperties) {
        this.secretKey = new SecretKeySpec(
            jwtProperties.getSecretKey()
                .getBytes(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key()
                .build()
                .getAlgorithm()
        );
    }

    public Ticket save(final Schedule schedule) {
        final String value = Jwts.builder()
            .claim(ROOM, schedule.room().getTitle())
            .claim(DATE, schedule.date().toString())
            .claim(TIME, schedule.time().toString())
            .signWith(secretKey)
            .compact();

        return new Ticket(value);
    }

    public Optional<Schedule> findById(final Ticket ticket) {
        try {
            final Claims claims = parseTicket(ticket.identifier());

            final String roomTitle = claims.get(ROOM, String.class);
            final Room room = Room.findByTitle(roomTitle).orElseThrow(
                () -> {
                    log.error("JWT 내 roomTitle은 '{}'이나, 해당 회의실이 존재하지 않음", roomTitle);
                    return new RuntimeException("회의실을 찾을 수 없습니다.");
                });

            return Optional.of(new Schedule(
                room,
                LocalDate.parse(claims.get(DATE, String.class)),
                LocalTime.parse(claims.get(TIME, String.class))
            ));
        } catch (final Exception e) {
            return Optional.empty();
        }
    }

    private Claims parseTicket(final String ticket) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(ticket)
            .getPayload();
    }
}
