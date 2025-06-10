package finalmission.shopkeeper.application;

import finalmission.shopkeeper.domain.Shopkeeper;
import finalmission.shopkeeper.dto.CreateShopkeeperIn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShopkeeperService {

    public final ShopkeeperRepository shopkeeperRepository;

    public void signUp(final CreateShopkeeperIn command) {
        shopkeeperRepository.save(
                Shopkeeper.create(command.name())
        );
    }
}
