package finalmission.site.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Site(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Site() {

    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
