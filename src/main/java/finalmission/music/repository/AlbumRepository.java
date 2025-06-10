package finalmission.music.repository;

import finalmission.music.domain.Album;
import org.springframework.data.repository.ListCrudRepository;

public interface AlbumRepository extends ListCrudRepository<Album, Long> {
}
