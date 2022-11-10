package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.JoinType;
import bom.proj.homedoc.domain.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        Member savedMember = memberRepository.save(Member.createDirectMember(email));

        // then
        assertEquals(email, savedMember.getEmail());
        assertEquals(JoinType.DIRECT, savedMember.getJoinType());
    }

    @Test
    public void findAllByDeletedAtNull() throws Exception {
        // given
        memberRepository.save(Member.createDirectMember("test1@gmail.com"));
        memberRepository.save(Member.createDirectMember("test2@gmail.com"));
        memberRepository.save(Member.createDirectMember("test3@gmail.com")).resign();

        // when
        Page <Member> memberList = memberRepository.findAllByDeletedAtNull(PageRequest.of(0, 100));

        // then
        assertEquals(2, memberList.getTotalElements());
    }

    @Test
    public void findById() throws Exception {
        // given
        Member savedMember = memberRepository.save(Member.createDirectMember("test1@gmail.com"));

        // when
        Member foundMember = memberRepository.findById(savedMember.getId()).orElse(null);

        // then
        assertEquals(savedMember, foundMember);
        assertEquals(savedMember.getId(), foundMember.getId());
        assertEquals(savedMember.getEmail(), foundMember.getEmail());
    }


}