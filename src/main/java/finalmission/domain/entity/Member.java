package finalmission.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int ptNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    protected Member() {

    }

    protected Member(Long id, String name, String email, String password, int ptNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.ptNumber = ptNumber;
    }

    public static Member createWithoutId(String name, String email, String password, int ptNumber) {
        return new Member(null, name, email, password, ptNumber);
    }

    public boolean matchPassword(String comparedPassword) {
        return this.password.equals(comparedPassword);
    }

    public boolean matchTrainer(Long trainerId) {
        return this.trainer != null && this.trainer.getId().equals(trainerId);
    }

    public boolean availableLesson() {
        return this.ptNumber > 0;
    }

    public void selectTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public boolean equalMember(Member member) {
        return this.getId().equals(member.getId());
    }

    public void minusPtNumber() {
        if (this.ptNumber > 0) {
            this.ptNumber--;
        }
    }

    public void refundPtNumber() {
        this.ptNumber++;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public int getPtNumber() {
        return ptNumber;
    }
}
