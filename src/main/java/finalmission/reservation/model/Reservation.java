package finalmission.reservation.model;

import finalmission.medical.model.TreatmentType;
import finalmission.member.model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TreatmentType treatmentType;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Time time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Member member;

    @Column(nullable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    protected Reservation() {}

    public Reservation(TreatmentType treatmentType, LocalDate date, Time time, Member member) {
        this.id = null;
        this.treatmentType = treatmentType;
        this.date = date;
        this.time = time;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public TreatmentType getTreatmentType() {
        return treatmentType;
    }

    public LocalDate getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Member getMember() {
        return member;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
