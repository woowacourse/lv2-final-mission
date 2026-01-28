package finalmission.stall.entity;

import finalmission.stallstatus.entity.StallStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Stall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "stall")
    private List<StallStatus> stallStatuses = new ArrayList<>();

    protected Stall() {
    }

    public Stall(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<StallStatus> getStallStatuses() {
        return stallStatuses;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Stall stall)) return false;
        return Objects.equals(id, stall.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
