package finalmission.restaurant.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.List;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private List<String> menus;

    public Restaurant() {
    }

    public Restaurant(final Long id, final String name, final String address, final List<String> menus) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.menus = menus;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getMenus() {
        return menus;
    }
}
