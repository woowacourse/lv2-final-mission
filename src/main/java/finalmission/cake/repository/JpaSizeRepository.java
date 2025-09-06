package finalmission.cake.repository;

import finalmission.cake.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSizeRepository extends SizeRepository, JpaRepository<Size, Long> {
}
