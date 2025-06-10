package finalmission.music.service;

import finalmission.music.controller.dto.AlbumRequest;
import finalmission.music.controller.dto.AlbumResponse;
import finalmission.music.domain.Album;
import finalmission.music.repository.AlbumRepository;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class AlbumServiceTest {

    @Autowired
    private AlbumService sut;

    @Autowired
    private AlbumRepository albumRepository;

    @Test
    @Transactional
    void 앨범_등록을_할_수_있다() {
        AlbumRequest albumRequest = new AlbumRequest("test", "test", 0, LocalDate.of(2020, 5, 24), "test-spotify");

        AlbumResponse saveAlbum = sut.save(albumRequest);

        Assertions.assertThat(saveAlbum.name()).isEqualTo(albumRequest.name());
        Assertions.assertThat(saveAlbum.artistName()).isEqualTo(albumRequest.artistName());
        Assertions.assertThat(saveAlbum.releaseDate()).isEqualTo(albumRequest.releaseDate());
        Assertions.assertThat(saveAlbum.totalTracks()).isEqualTo(albumRequest.totalTracks());
    }

    @Test
    @Transactional
    void 앨범_조회가_가능하다() {
        Album album = albumRepository.save(new Album("test", "test", 0, LocalDate.of(2020, 5, 24), "test-spotify"));

        AlbumResponse response = sut.getAlbum(album.getId());

        Assertions.assertThat(response.name()).isEqualTo(album.getName());
        Assertions.assertThat(response.artistName()).isEqualTo(album.getArtistName());
        Assertions.assertThat(response.releaseDate()).isEqualTo(album.getReleaseDate());
        Assertions.assertThat(response.totalTracks()).isEqualTo(album.getTotalTracks());
    }

    @Test
    @Transactional
    void 앨범_삭제가_가능하다() {
        Album album = albumRepository.save(new Album("test", "test", 0, LocalDate.of(2020, 5, 24), "test-spotify"));

        sut.delete(album.getId());

        Assertions.assertThat(albumRepository.existsById(album.getId())).isFalse();
    }
}
