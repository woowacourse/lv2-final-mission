package finalmission.music.lottery.repository;

import finalmission.music.lottery.domain.Lottery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotteryRepository extends JpaRepository<Lottery, Long> {
}
