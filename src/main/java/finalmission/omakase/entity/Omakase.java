package finalmission.omakase.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Omakase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String storeName;

    @Enumerated
    private Rating rating;

    protected Omakase() {}

    public Omakase(String storeName, Rating rating) {
        this(null, storeName, rating);
    }

    public Omakase(Long id, String storeName, Rating rating) {
        this.id = id;
        this.storeName = storeName;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public String getStoreName() {
        return storeName;
    }

    public Rating getRating() {
        return rating;
    }
}
