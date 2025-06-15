package shh.alias.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shh.alias.application.AliasClient;
import shh.alias.application.AliasService;
import shh.alias.domain.Alias;

@Service
@RequiredArgsConstructor
public class RandommerAliasService implements AliasService {

    private final AliasClient aliasClient;

    @Override
    public Alias generateAlias(final Integer quantity) {
        return new Alias(aliasClient.requestAlias(quantity));
    }
}
