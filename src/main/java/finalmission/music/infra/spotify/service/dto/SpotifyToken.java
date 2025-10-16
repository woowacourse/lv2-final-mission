package finalmission.music.infra.spotify.service.dto;

public record SpotifyToken(String access_token, String token_type, int expires_in) {

}
