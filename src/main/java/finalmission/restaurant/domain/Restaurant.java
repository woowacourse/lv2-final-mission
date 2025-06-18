package finalmission.restaurant.domain;

import jakarta.persistence.*;

@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "address")
    private String address;
    @Column(name = "zipcode")
    private int zipcode;
    @Column(name = "table_count")
    private int tableCount;

    protected Restaurant() {}

    private Restaurant(
            final String name,
            final String description,
            final String address,
            final int zipcode,
            final int tableCount
    ) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.zipcode = zipcode;
        this.tableCount = tableCount;
    }

    public static Restaurant beforeSave(
            final String name,
            final String description,
            final String address,
            final int zipcode,
            final int tableCount
    ) {
        return new Restaurant(name,description,address,zipcode,tableCount);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public int getZipcode() {
        return zipcode;
    }

    public int getTableCount() {
        return tableCount;
    }
}
