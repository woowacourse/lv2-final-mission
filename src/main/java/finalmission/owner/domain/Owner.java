package finalmission.owner.domain;

import finalmission.shop.domain.Shop;
import finalmission.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    private String businessLicenseUrl;

    private String businessRegistrationNumber;

    @OneToOne(mappedBy = "owner")
    private Shop shop;

    public Owner(User user, String businessLicenseUrl, String businessRegistrationNumber) {
        this.user = user;
        this.businessLicenseUrl = businessLicenseUrl;
        this.businessRegistrationNumber = businessRegistrationNumber;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
