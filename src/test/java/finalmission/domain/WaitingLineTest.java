package finalmission.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class WaitingLineTest {

    @Test
    void 대기에_멤버를_추가한다() {

        // given
        Member member = new Member(1L, "email@email.com", "owner1", MemberRole.MASTER);
        Store store = new Store("storeName", StoreStatus.CLOSED, "descr", 4.5, member);
        WaitingLine waitingLine = new WaitingLine(1L, new ArrayList<>(), store);

        // when
        waitingLine.addMember(new Member(1L, "email", "name", "password", MemberRole.CUSTOMER));

        // then
        assertThat(waitingLine.getWaitingMembers().size()).isEqualTo(1);
    }

    @Test
    void 대기에서_특정멤버가_있다면_true를_반환한다() {

        // given
        Member owner = new Member(1L, "email@email.com", "owner1", MemberRole.MASTER);
        Store store = new Store("storeName", StoreStatus.CLOSED, "descr", 4.5, owner);
        WaitingLine waitingLine = new WaitingLine(1L, new ArrayList<>(), store);
        Member member = new Member(1L, "email", "name", "password", MemberRole.CUSTOMER);

        // when
        waitingLine.addMember(member);

        // then
        assertThat(waitingLine.hasMember(member)).isTrue();
    }

    @Test
    void 대기에서_특정멤버가_없다면_false를_반환한다() {

        // given
        Member owner = new Member(1L, "email@email.com", "owner1", MemberRole.MASTER);
        Store store = new Store("storeName", StoreStatus.CLOSED, "descr", 4.5, owner);
        WaitingLine waitingLine = new WaitingLine(1L, new ArrayList<>(), store);
        Member member = new Member(1L, "email", "name", "password", MemberRole.CUSTOMER);
        Member anotherMember = new Member(2L, "email", "name", "password", MemberRole.CUSTOMER);

        // when
        waitingLine.addMember(member);

        // then
        assertThat(waitingLine.hasMember(anotherMember)).isFalse();
    }

    @Test
    void 특정멤버의_대기_순서를_찾는다() {

        // given
        Member member1 = new Member(1L, "email", "name", "password", MemberRole.CUSTOMER);
        Member member2 = new Member(2L, "email", "name", "password", MemberRole.CUSTOMER);
        Member member3 = new Member(3L, "email", "name", "password", MemberRole.CUSTOMER);
        Member member4 = new Member(4L, "email", "name", "password", MemberRole.CUSTOMER);
        Member member5 = new Member(5L, "email", "name", "password", MemberRole.CUSTOMER);

        Member member = new Member(1L, "email@email.com", "owner1", MemberRole.MASTER);
        Store store = new Store("storeName", StoreStatus.CLOSED, "descr", 4.5, member);
        WaitingLine waitingLine = new WaitingLine(1L, new ArrayList<>(), store);

        waitingLine.addMember(member1);
        waitingLine.addMember(member2);
        waitingLine.addMember(member3);
        waitingLine.addMember(member4);
        waitingLine.addMember(member5);

        // when
        int sequence = waitingLine.getSequenceByMember(member5);

        // then
        assertThat(sequence).isEqualTo(5);
    }

}
