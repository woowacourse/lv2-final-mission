package finalmission.util;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class Base64PasswordTransformer implements PasswordTransformer{

    @Override
    public String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String decode(String value) {
        return Arrays.toString(Base64.getDecoder().decode(value.getBytes(StandardCharsets.UTF_8)));
    }
}
