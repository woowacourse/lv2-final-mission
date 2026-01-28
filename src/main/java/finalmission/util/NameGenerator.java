package finalmission.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import finalmission.util.client.NameGeneratorClient;

@Component
public class NameGenerator {

    private static final int DEFAULT_CACHE_AMOUNT = 500;

    private final NameGeneratorClient client;
    private final Set<String> namesCache = ConcurrentHashMap.newKeySet();

    public NameGenerator(NameGeneratorClient client) {
        this.client = client;
    }

    public String randomName() {
        if (namesCache.isEmpty()) {
            refill();
        }
        String name = namesCache.iterator().next();
        namesCache.remove(name);
        return name;
    }

    private void refill() {
        namesCache.addAll(client.getNames(DEFAULT_CACHE_AMOUNT));
    }
}
