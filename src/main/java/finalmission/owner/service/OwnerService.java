package finalmission.owner.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import finalmission.owner.domain.Owner;
import finalmission.owner.dto.OwnerRequest;
import finalmission.owner.repository.OwnerRepository;
import finalmission.shop.domain.OperatingHour;
import finalmission.shop.domain.Shop;
import finalmission.shop.dto.ShopResponse;
import finalmission.shop.repository.ShopRepository;
import finalmission.user.domain.User;
import finalmission.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OwnerService {

    private final UserRepository userRepository;
    private final OwnerRepository ownerRepository;
    private final ShopRepository shopRepository;

    @Transactional
    public void register(Long userId, OwnerRequest.Register request) {
        User user = userRepository.getById(userId);
        ownerRepository.save(new Owner(user, request.businessLicenseUrl(), request.businessRegistrationNumber()));
    }

    @Transactional
    public ShopResponse.Detail registerShop(Long userId, OwnerRequest.Shop request) {
        User user = userRepository.getById(userId);
        Owner owner = ownerRepository.getByUser(user);
        Shop shop = generateShop(request, owner);

        shopRepository.save(shop);
        return new ShopResponse.Detail(shop);
    }

    private Shop generateShop(OwnerRequest.Shop request, Owner owner) {
        Shop shop = new Shop(request.name(), request.type(), request.detail(), owner);
        List<OperatingHour> operatingHours = request.operatingHours().stream()
                .map(operatingHour -> new OperatingHour(shop, operatingHour.dayOfWeek(), operatingHour.time()))
                .toList();
        shop.setOperatingHours(operatingHours);
        return shop;
    }

    @Transactional
    public ShopResponse.Detail updateShop(Long userId, Long shopId, OwnerRequest.Shop request) {
        User user = userRepository.getById(userId);
        Owner owner = ownerRepository.getByUser(user);
        Shop shop = generateShop(request, owner);
        shop.setId(shopId);

        shopRepository.save(shop);
        return new ShopResponse.Detail(shop);
    }
}
