package finalmission.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoterRepository extends JpaRepository<Voter, Id> {
    boolean existsByNameAndPassword(String name, String password);

    Optional<Voter> findByName(String name);
}
