package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.JoinType;
import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.OauthType;
import bom.proj.homedoc.dto.request.DirectMemberCreateRequestDto;
import bom.proj.homedoc.dto.request.MemberUpdateRequestDto;
import bom.proj.homedoc.dto.request.SnsMemberCreateRequestDto;
import bom.proj.homedoc.dto.request.SnsUpdateRequestDto;
import bom.proj.homedoc.dto.response.MemberResponseDto;
import bom.proj.homedoc.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Test
    public void 회원_목록조회() throws Exception {
        // given
        List<Member> memberList = new ArrayList<>();

        String email1 = "test1@email.com";
        Member member1 = createDirectMember(email1);
        memberList.add(member1);

        String email2 = "test2@email.com";
        Member member2 = createDirectMember(email2);
        memberList.add(member2);

        String email3 = "test3@email.com";
        Member member3 = createDirectMember(email3);
        memberList.add(member3);

        // mocking
        given(memberRepository.findAllByDeletedAtNull(any())).willReturn(new PageImpl<>(memberList));

        // when
        List<MemberResponseDto> foundMember = memberService.getMembers(0, 100);

        // then
        assertEquals(3, foundMember.size());
    }

    @Test
    public void 회원_개별조회() throws Exception {
        // given
        String email = "test@email.com";
        Member member = createDirectMember(email);

        Long fakeMemberId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeMemberId);

        // mocking
        given(memberRepository.findById(fakeMemberId)).willReturn(Optional.ofNullable(member));

        // when
        MemberResponseDto foundMember = memberService.getMemberById(fakeMemberId);

        // then
        assertEquals(member.getId(), foundMember.getMemberId());
        assertEquals(member.getName(), foundMember.getName());
        assertEquals(member.getEmail(), foundMember.getEmail());
        assertEquals(member.getOauthType(), foundMember.getOauthType());
    }

    @Test
    public void 회원_직접가입() throws Exception {
        // given
        String email = "test@email.com";
        Member member = createDirectMember(email);

        Long fakeMemberId = 11L;
        ReflectionTestUtils.setField(member, "id", fakeMemberId);

        // mocking
        given(memberRepository.save(any())).willReturn(member);
        given(memberRepository.findById(fakeMemberId)).willReturn(Optional.ofNullable(member));

        // when
        Long newMemberId = memberService.join(member);

        // then
        MemberResponseDto findMember = memberService.getMemberById(newMemberId);
        assertEquals(member.getId(), findMember.getMemberId());
        assertEquals(member.getEmail(), findMember.getEmail());
        assertEquals(member.getJoinType(), JoinType.DIRECT);
    }

    @Test
    public void 회원_SNS_가입() throws Exception {
        // given
        String oauthType = "NAVER";
        String oauthId = "uid1234";
        Member member = createSnsMember(oauthType, oauthId);

        Long fakeMemberId = 49L;
        ReflectionTestUtils.setField(member, "id", fakeMemberId);

        //mocking
        given(memberRepository.save(member)).willReturn(member);
        given(memberRepository.findById(fakeMemberId)).willReturn(Optional.ofNullable(member));

        // when
        Long findMemberId = memberService.join(member);

        // then
        MemberResponseDto findMember = memberService.getMemberById(findMemberId);
        assertEquals(member.getId(), findMember.getMemberId());
        assertEquals(member.getOauthType(), findMember.getOauthType());
        assertEquals(member.getJoinType(), JoinType.SNS);
    }
    
    @Test
    public void 중복_이메일_가입() throws Exception {
        // given
        String email = "test@email.com";
        Member member = createDirectMember(email);

        //mocking
        given(memberRepository.findByEmail(member.getEmail())).willReturn(Optional.ofNullable(member));

        //when
        // then
        assertThrows(DuplicateKeyException.class, () -> memberService.join(member));
    }
    
    @Test
    public void 중복_SNS_가입() throws Exception {
        // given
        String oauthType = "NAVER";
        String oauthId = "uid1234";
        Member member = createSnsMember(oauthType, oauthId);

        //mocking
        given(memberRepository.findByOauthTypeAndOauthId(member.getOauthType(), member.getOauthId())).willReturn(Optional.ofNullable(member));

        //when
        // then
        assertThrows(DuplicateKeyException.class, () -> memberService.join(member));
    }

    @Test
    public void 기본정보_수정() throws Exception {
        // given
        Member member = createDirectMember("test@email.com");

        String newName = "Lee";
        String newEmail = "test2@email.com";
        MemberUpdateRequestDto updateInfo = new MemberUpdateRequestDto();
        ReflectionTestUtils.setField(updateInfo, "name", newName);
        ReflectionTestUtils.setField(updateInfo, "email", newEmail);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));

        // when
        MemberResponseDto updateMember = memberService.defaultInfoUpdate(1L, updateInfo);

        // then
        assertEquals(newName, updateMember.getName());
        assertEquals(newEmail, updateMember.getEmail());
    }

    @Test
    public void SNS_수정() throws Exception {
        // given
        Member member = createDirectMember("test@email.com");
        String oauthType = "GOOGLE";
        String oauthId = "uid1111";
        SnsUpdateRequestDto updateInfo = new SnsUpdateRequestDto();
        ReflectionTestUtils.setField(updateInfo, "oauthType", oauthType);
        ReflectionTestUtils.setField(updateInfo, "oauthId", oauthId);

        //mocking
        given(memberRepository.findById(any())).willReturn(Optional.ofNullable(member));

        // when
        memberService.snsUpdate(1L, updateInfo);

        // then
        assertEquals(OauthType.valueOf(oauthType), member.getOauthType());
        assertEquals(oauthId, member.getOauthId());
    }

    @Test
    public void 회원_탈퇴() throws Exception {
        // given
        Member member = createDirectMember("test@email.com");

        Long fakeMemberId = 13L;
        ReflectionTestUtils.setField(member, "id", fakeMemberId);

        given(memberRepository.findById(fakeMemberId)).willReturn(Optional.ofNullable(member));

        // when
        memberService.resign(fakeMemberId);

        // then
        assertNotNull(member.getDeletedAt());
    }

    private Member createDirectMember(String email) {
        DirectMemberCreateRequestDto dto = new DirectMemberCreateRequestDto();
        ReflectionTestUtils.setField(dto, "email", email);
        return dto.toEntity();
    }

    private Member createSnsMember(String oauthType, String oauthId) {
        SnsMemberCreateRequestDto dto = new SnsMemberCreateRequestDto();
        ReflectionTestUtils.setField(dto, "oauthType", oauthType);
        ReflectionTestUtils.setField(dto, "oauthId", oauthId);
        return dto.toEntity();
    }
}