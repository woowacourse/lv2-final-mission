package finalmission.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class NicknameReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @Embedded
    Nickname nickname;

    @Enumerated(value = EnumType.STRING)
    ReservationStatus status;

    public NicknameReservation() {
    }

    public NicknameReservation(Member member, Nickname nickname) {
        this.id = null;
        this.member = member;
        this.nickname = nickname;
        this.status = ReservationStatus.RESERVE;
    }

    public boolean hasSameMemberId(long memberId) {
        return member.hasSameMemberId(memberId);
    }

    public void confirm() {
        if (isConfirmed()) {
            throw new IllegalArgumentException("이미 확정되었습니다.");
        }
        status = ReservationStatus.CONFIRM;
    }

    public boolean isConfirmed() {
        return status == ReservationStatus.CONFIRM;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public Nickname getNickname() {
        return nickname;
    }

    public ReservationStatus getStatus() {
        return status;
    }
}
