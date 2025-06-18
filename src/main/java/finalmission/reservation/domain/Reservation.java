package finalmission.reservation.domain;

import finalmission.member.domain.Member;
import finalmission.site.domain.Site;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Site site;

    public Reservation(Long id, LocalDate checkInDate, LocalDate checkOutDate, Member member, Site site) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.member = member;
        this.site = site;
    }

    public Reservation() {

    }

    public Long getId() {
        return id;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public Member getMember() {
        return member;
    }

    public Site getSite() {
        return site;
    }
}
