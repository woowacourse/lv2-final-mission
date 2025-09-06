package finalmission.cake.repository;

import finalmission.cake.model.Size;
import java.util.List;
import java.util.Optional;

public interface SizeRepository {
    List<Size> findAll();

    Optional<Size> findById(Long id);
}
