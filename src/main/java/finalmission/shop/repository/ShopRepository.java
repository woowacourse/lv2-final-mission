package finalmission.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import finalmission.shop.domain.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    default Shop getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상점입니다."));
    }
}
