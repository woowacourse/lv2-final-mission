package finalmission.music.album.repository;

import finalmission.music.album.domain.Album;
import org.springframework.data.repository.ListCrudRepository;

public interface AlbumRepository extends ListCrudRepository<Album, Long> {
}
