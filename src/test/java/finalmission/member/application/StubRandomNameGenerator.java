package finalmission.member.application;

public class StubRandomNameGenerator implements RandomNameGenerator {
    @Override
    public String generateName() {
        return "test";
    }
}
