package finalmission.weather.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class Weather {

    private final String sky;
    private final String pop;
}
