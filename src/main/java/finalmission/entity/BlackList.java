package finalmission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class BlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "black_list_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String reason;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime bannedSince;

    protected BlackList() {
    }

    public BlackList(final Long id, final Member member, final String reason) {
        this.id = id;
        this.member = member;
        this.reason = reason;
    }

    public BlackList(final Member member, final String reason) {
        this(null, member, reason);
    }

    public Long getId() {
        return id;
    }

    public String getReason() {
        return reason;
    }

    public Member getMember() {
        return member;
    }

    public LocalDateTime getBannedSince() {
        return bannedSince;
    }
}
