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

    public void updateNickname(Nickname newNickname) {
        validateAlreadyConfirmed();
        if (nickname.equals(newNickname)) {
            throw new IllegalArgumentException("수정 전 닉네임과 동일합니다.");
        }
        nickname = newNickname;
    }

    public void confirm() {
        validateAlreadyConfirmed();
        status = ReservationStatus.CONFIRM;
    }

    private void validateAlreadyConfirmed() {
        if (isConfirmed()) {
            throw new IllegalArgumentException("이미 확정되었습니다.");
        }
    }

    public boolean isConfirmed() {
        return status == ReservationStatus.CONFIRM;
    }

    public boolean hasSameMemberId(long memberId) {
        return member.hasSameMemberId(memberId);
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
