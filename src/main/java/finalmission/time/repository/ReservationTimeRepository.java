package finalmission.time.repository;

import finalmission.time.domain.ReservationTime;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeRepository {

    private final SimpleJdbcInsert simpleJdbcInsert;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ReservationTimeRepository(DataSource dataSource) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return namedParameterJdbcTemplate.query(sql, (resultSet, rowNum) -> createReservationTime(resultSet));
    }

    public Optional<ReservationTime> findById(final Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = :id";
        Map<String, Object> parameter = Map.of("id", id);

        try {
            return Optional.of(namedParameterJdbcTemplate.queryForObject(sql, parameter,
                    (resultSet, rowNum) -> createReservationTime(resultSet)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private ReservationTime createReservationTime(final ResultSet resultSet) throws SQLException {
        return new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
    }
}
