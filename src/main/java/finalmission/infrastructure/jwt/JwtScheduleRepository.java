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
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties(JwtProperties.class)
public class JwtScheduleRepository implements ScheduleRepository {

    public static final String ROOM = "room";
    public static final String DATE = "date";
    public static final String TIME = "time";

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

    public Schedule findByTicket(final Ticket ticket) {
        final Claims claims = parseTicket(ticket.identifier());
        final Room room = Room.findByTitle((String) claims.get(ROOM)).orElseThrow(
            () -> new RuntimeException("Room not found"));

        return new Schedule(
            room,
            LocalDate.parse(claims.get(DATE, String.class)),
            LocalTime.parse(claims.get(TIME, String.class))
        );
    }

    private Claims parseTicket(final String ticket) {
        return Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(ticket)
            .getPayload();
    }
}
