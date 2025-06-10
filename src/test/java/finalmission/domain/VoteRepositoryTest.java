package finalmission.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private VoterRepository voterRepository;
    @Autowired
    private RoomRepository roomRepository;

    @ParameterizedTest
    @MethodSource("getDateTimeAndExpectedResults")
    void 중복_투표가_있는지_확인한다(
            List<LocalDateTime> compareDateTimes,
            boolean expectedResult
    ) {
        // given
        Room room = new Room(
                LocalDate.of(2025, 5, 17),
                LocalDate.of(2025, 5, 20),
                LocalTime.of(8, 0),
                LocalTime.of(22, 0),
                true
        );
        roomRepository.save(room);
        Voter voter = new Voter("dompoo", "1234", room);
        voterRepository.save(voter);
        voteRepository.saveAll(List.of(
                new Vote(LocalDateTime.of(2025, 5, 17, 10, 0), room, voter),
                new Vote(LocalDateTime.of(2025, 5, 18, 10, 0), room, voter),
                new Vote(LocalDateTime.of(2025, 5, 19, 10, 0), room, voter)
        ));

        // when
        boolean result = voteRepository.hasDuplicatedVote(room.getId().value(), voter, compareDateTimes);

        // then
        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> getDateTimeAndExpectedResults() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2025, 5, 17, 10, 0)
                        ),
                        true
                ),
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2025, 5, 17, 10, 0),
                                LocalDateTime.of(2025, 5, 18, 10, 0)
                        ),
                        true
                ),
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2025, 5, 17, 10, 0),
                                LocalDateTime.of(2025, 5, 18, 10, 0),
                                LocalDateTime.of(2025, 5, 19, 10, 0)
                        ),
                        true
                ),
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2025, 5, 17, 12, 0),
                                LocalDateTime.of(2025, 5, 18, 12, 0),
                                LocalDateTime.of(2025, 5, 19, 10, 0)
                        ),
                        true
                ),
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2025, 5, 17, 12, 0),
                                LocalDateTime.of(2025, 5, 18, 12, 0),
                                LocalDateTime.of(2025, 5, 19, 12, 0)
                        ),
                        false
                ),
                Arguments.of(
                        List.of(
                                LocalDateTime.of(2025, 5, 19, 12, 0)
                        ),
                        false
                )
        );
    }
}