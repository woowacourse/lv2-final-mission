package finalmission.shopkeeper.application;

import finalmission.shopkeeper.application.in.dto.SignupShopkeeper;
import finalmission.shopkeeper.application.out.ShopkeeperRepository;
import finalmission.shopkeeper.domain.Shopkeeper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ShopkeeperService {

    public final ShopkeeperRepository shopkeeperRepository;

    @Transactional
    public void signUp(final SignupShopkeeper command) {
        shopkeeperRepository.save(
                Shopkeeper.create(command.name())
        );
    }
}
