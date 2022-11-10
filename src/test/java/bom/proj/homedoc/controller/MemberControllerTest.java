package bom.proj.homedoc.controller;

import bom.proj.homedoc.domain.JoinType;
import bom.proj.homedoc.domain.OauthType;
import bom.proj.homedoc.dto.request.DirectMemberCreateRequestDto;
import bom.proj.homedoc.dto.request.MemberUpdateRequestDto;
import bom.proj.homedoc.dto.request.SnsMemberCreateRequestDto;
import bom.proj.homedoc.dto.request.SnsUpdateRequestDto;
import bom.proj.homedoc.dto.response.MemberResponseDto;
import bom.proj.homedoc.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean //TODO: @Mock은 안되고 @MockBean은 됨...
    private MemberService memberService;

    
    @Test
    public void 회원_목록_조회() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(
                get("/v1/member"))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.message").value("SUCCESS"),
                jsonPath("$.data").value(new ArrayList<>()));
    }
    
    @Test
    public void 회원_조회() throws Exception {
        // given
        Long fakeMemberId = 11L;
        MemberResponseDto responseDto = getMemberResponseDto(fakeMemberId);

        given(memberService.getMemberById(any())).willReturn(responseDto);

        // when
        // then
        mockMvc.perform(
                get("/v1/member/" + fakeMemberId))
            .andExpectAll(
                status().isOk(),
                jsonPath("$.message").value("SUCCESS"),
                jsonPath("$.data.memberId").value(fakeMemberId),
                jsonPath("$.data.name").value("test"),
                jsonPath("$.data.email").value("test@gmail.com"),
                jsonPath("$.data.joinType").value("DIRECT"),
                jsonPath("$.data.oauthType").value(IsNull.nullValue()));
    }

    @Test
    public void 직접가입() throws Exception {
        // given
        Long fakeMemberId = 11L;
        given(memberService.join(any())).willReturn(fakeMemberId);
        DirectMemberCreateRequestDto dto = new DirectMemberCreateRequestDto();
        ReflectionTestUtils.setField(dto, "email", "test@test.com");

        // when
        // then
        mockMvc.perform(
                post("/v1/member/direct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.message").value("SUCCESS"),
                jsonPath("$.data.id").value(fakeMemberId));
    }
    
    @Test
    public void SNS가입() throws Exception {
        // given
        Long fakeMemberId = 11L;
        given(memberService.join(any())).willReturn(fakeMemberId);
        SnsMemberCreateRequestDto dto = new SnsMemberCreateRequestDto();
        ReflectionTestUtils.setField(dto, "oauthType", "NAVER");
        ReflectionTestUtils.setField(dto, "oauthId", "1234");
        
        // when
        // then
        mockMvc.perform(
                post("/v1/member/sns")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.message").value("SUCCESS"),
                jsonPath("$.data.id").value(fakeMemberId));
    }
    
    @Test
    public void 기본정보_수정() throws Exception {
        // given
        MemberUpdateRequestDto requestDto = new MemberUpdateRequestDto();
        ReflectionTestUtils.setField(requestDto, "email", "test@gmail.com");
        ReflectionTestUtils.setField(requestDto, "name", "test");

        Long fakeMemberId = 11L;
        MemberResponseDto responseDto = getMemberResponseDto(fakeMemberId);

        given(memberService.defaultInfoUpdate(any(), any())).willReturn(responseDto);

        // when
        // then
        mockMvc.perform(
                patch("/v1/member/" + fakeMemberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.message").value("SUCCESS"),
                jsonPath("$.data.memberId").value(fakeMemberId),
                jsonPath("$.data.name").value("test"),
                jsonPath("$.data.email").value("test@gmail.com"),
                jsonPath("$.data.joinType").value("DIRECT"),
                jsonPath("$.data.oauthType").value(IsNull.nullValue()));
    }

    @Test
    public void SNS정보_수정() throws Exception {
        // given
        SnsUpdateRequestDto requestDto = new SnsUpdateRequestDto();
        ReflectionTestUtils.setField(requestDto, "oauthType", "GOOGLE");
        ReflectionTestUtils.setField(requestDto, "oauthId", "test1234");

        Long fakeMemberId = 11L;
        MemberResponseDto responseDto = getMemberResponseDto(fakeMemberId);
        ReflectionTestUtils.setField(responseDto, "oauthType", OauthType.GOOGLE);

        given(memberService.defaultInfoUpdate(any(), any())).willReturn(responseDto);

        // when
        // then
        mockMvc.perform(
                patch("/v1/member/" + fakeMemberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(requestDto))
        ).andExpectAll(
                status().isOk(),
                jsonPath("$.message").value("SUCCESS"),
                jsonPath("$.data.memberId").value(fakeMemberId),
                jsonPath("$.data.oauthType").value("GOOGLE"));
    }
    
    @Test
    public void 회원_탈퇴() throws Exception {
        // given
        
        // when
        
        // then
    }

    private static MemberResponseDto getMemberResponseDto(Long fakeMemberId) {
        MemberResponseDto responseDto = new MemberResponseDto();
        ReflectionTestUtils.setField(responseDto, "memberId", fakeMemberId);
        ReflectionTestUtils.setField(responseDto, "name", "test");
        ReflectionTestUtils.setField(responseDto, "email", "test@gmail.com");
        ReflectionTestUtils.setField(responseDto, "joinType", JoinType.DIRECT);
        return responseDto;
    }

}