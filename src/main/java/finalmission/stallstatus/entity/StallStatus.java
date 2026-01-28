package finalmission.stallstatus.entity;

import finalmission.member.entity.Member;
import finalmission.stall.entity.Stall;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class StallStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createAt;

    private String randomNickname;

    private Status status;

    @ManyToOne
    private Member member;

    @ManyToOne
    @JoinColumn(name = "stall_id")
    private Stall stall;

    protected StallStatus() {
    }

    public StallStatus(String randomNickname, Member member, Stall stall) {
        this.createAt = LocalDateTime.now();
        this.randomNickname = randomNickname;
        this.member = member;
        this.stall = stall;
        this.status = Status.USING;
    }

    public void changeStallStatus() {
        if (this.status == Status.PENDING) {
            this.status = Status.USING;
        } else {
            this.status = Status.PENDING;
        }
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public String getRandomNickname() {
        return randomNickname;
    }

    public Member getMember() {
        return member;
    }

    public Stall getStall() {
        return stall;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StallStatus that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
