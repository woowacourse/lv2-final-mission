package finalmission.member.domain;

public class StubPasswordEncoder implements PasswordEncoder {

    public static final String ENCODED = "encoded_";

    @Override
    public String encode(String rawPassword) {
        return ENCODED + rawPassword;
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return encodedPassword.equals(ENCODED + rawPassword);
    }
}
