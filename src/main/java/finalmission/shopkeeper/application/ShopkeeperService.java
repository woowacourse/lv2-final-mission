package finalmission.shopkeeper.application;

import finalmission.shopkeeper.application.in.dto.CreateShopkeeper;
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
    public void signUp(final CreateShopkeeper command) {
        shopkeeperRepository.save(
                Shopkeeper.create(command.name())
        );
    }
}
