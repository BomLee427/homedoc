package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.Assert.*;

@DataJpaTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void save() throws Exception {
        // given
        String email = "test@test.com";

        // when
        Member savedMember = memberRepository.save(Member.createMember(email, "12345678"));

        // then
        assertEquals(email, savedMember.getEmail());
        assertEquals(JoinType.DIRECT, savedMember.getJoinType());
    }

    @Test
    public void findAllByDeletedAtNull() throws Exception {
        // given
        memberRepository.save(Member.createMember("test1@gmail.com", "12345678"));
        memberRepository.save(Member.createMember("test2@gmail.com", "12345678"));
        memberRepository.save(Member.createMember("test3@gmail.com", "12345678")).resign();

        // when
        Page <Member> memberList = memberRepository.findAllByDeletedAtNull(PageRequest.of(0, 100));

        // then
        assertEquals(2, memberList.getTotalElements());
    }

    @Test
    public void findById() throws Exception {
        // given
        Member savedMember = memberRepository.save(Member.createMember("test1@gmail.com", "12345678"));

        // when
        Member foundMember = memberRepository.findById(savedMember.getId()).orElse(null);

        // then
        assertEquals(savedMember, foundMember);
        assertEquals(savedMember.getId(), foundMember.getId());
        assertEquals(savedMember.getEmail(), foundMember.getEmail());
    }


}