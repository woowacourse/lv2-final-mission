package finalmission.shop.domain;

import java.util.ArrayList;
import java.util.List;

import finalmission.owner.domain.Owner;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ShopType type;

    private String detail;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OperatingHour> operatingHours = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    private Owner owner;

    public Shop(String name, ShopType type, String detail, Owner owner) {
        this.name = name;
        this.type = type;
        this.detail = detail;
        this.owner = owner;
        owner.setShop(this);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOperatingHours(List<OperatingHour> operatingHours) {
        this.operatingHours = operatingHours;
    }
}
