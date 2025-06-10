package finalmission.reservation.repository;

import finalmission.member.domain.Member;
import finalmission.member.domain.Role;
import finalmission.reservation.domain.Reservation;
import finalmission.time.domain.ReservationTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepository {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReservationRepository(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Reservation> findAll() {
        String sql = """
                SELECT r.id AS reservation_id, r.name, r.date, 
                       m.id AS member_id, m.name AS member_name,
                       m.email, m.password, m.role, 
                       t.id AS time_id, t.start_at AS time_value 
                FROM reservation AS r 
                INNER JOIN reservation_time AS t 
                ON r.time_id = t.id 
                INNER JOIN member AS m
                ON r.member_id = m.id
                """;

        return namedParameterJdbcTemplate.query(sql, (resultSet, rowNum) -> createReservation(resultSet));
    }

    public Reservation add(final Reservation reservation) {
        Map<String, Object> parameters = new HashMap<>(5);
        parameters.put("name", reservation.getMember().getName());
        parameters.put("date", reservation.getDate());
        parameters.put("time_id", reservation.getTime().getId());
        parameters.put("member_id", reservation.getMember().getId());
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();

        return new Reservation(
                id,
                reservation.getMember(),
                reservation.getDate(),
                reservation.getTime()
        );
    }

    public void deleteById(final Long id) {
        String sql = "DELETE FROM reservation WHERE id = :id";
        Map<String, Object> parameter = Map.of("id", id);

        namedParameterJdbcTemplate.update(sql, parameter);
    }

    private Reservation createReservation(final ResultSet resultSet) throws SQLException {
        return new Reservation(
                resultSet.getLong("reservation_id"),
                new Member(
                        resultSet.getLong("member_id"),
                        resultSet.getString("member_name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        Role.from(resultSet.getString("role"))
                ),
                resultSet.getDate("date").toLocalDate(),
                new ReservationTime(
                        resultSet.getLong("time_id"),
                        resultSet.getTime("time_value").toLocalTime()
                ));
    }
}
