package finalmission.util;

public interface PasswordTransformer {

    String encode(String value);

    String decode(String value);
}
