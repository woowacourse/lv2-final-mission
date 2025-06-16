package finalmission;

import finalmission.application.service.NameClient;

public class StubRandomNameClient implements NameClient {
    @Override
    public String approve() {
        return "temp";
    }
}
