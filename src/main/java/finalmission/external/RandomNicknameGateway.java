package finalmission.external;

import finalmission.external.dto.RandomNameResponse;

public interface RandomNicknameGateway {

    RandomNameResponse findRandomNickname(NameType nameType);
}
