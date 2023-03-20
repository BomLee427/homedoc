package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.measure.Manual;
import bom.proj.homedoc.domain.measure.Normality;
import bom.proj.homedoc.domain.measure.Pressure;
import bom.proj.homedoc.dto.request.PressureCreateRequestDto;
import bom.proj.homedoc.dto.request.PressureUpdateRequestDto;
import bom.proj.homedoc.dto.response.PressureResponseDto;
import bom.proj.homedoc.dto.response.PressureStatisticResponseDto;
import bom.proj.homedoc.repository.MemberRepository;
import bom.proj.homedoc.repository.PressureRepoImpl;
import bom.proj.homedoc.repository.PressureRepository;
import bom.proj.homedoc.search.PressureSearch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PressureServiceTest {

    @Mock
    private PressureRepository pressureRepository;

    @Mock
    private PressureRepoImpl pressureRepoImpl;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private PressureService pressureService;

    @Test
    public void 혈압_목록조회() throws Exception {
        // given
        Member member = Member.createMember("email@email.com", "12345678");
        List<Pressure> pressureList = new ArrayList<>();
        pressureList.add(getPressure(100, 120, member));
        pressureList.add(getPressure(100, 120, member));
        pressureList.add(getPressure(100, 120, member));

        PressureSearch pressureSearch = PressureSearch.builder().build();
        Pageable pageable = PageRequest.of(0, 100);

        given(pressureRepoImpl.findAll(anyLong(), any(), any())).willReturn(new PageImpl<>(pressureList));

        // when
        List<PressureResponseDto> foundPressure = pressureService.getPressureList(11L, pressureSearch, pageable);

        // then
        assertEquals(3, foundPressure.size());
    }

    @Test
    public void 혈압_개별조회() throws Exception {
        // given
        Member member = Member.createMember("email@email.com", "12345678");
        ReflectionTestUtils.setField(member, "id", 11L);

        Pressure pressure = getPressure(100, 120, member);
        ReflectionTestUtils.setField(pressure, "id", 18L);

        given(pressureRepository.findById(anyLong())).willReturn(Optional.ofNullable(pressure));

        // when
        PressureResponseDto foundPressure = pressureService.getPressure(11L, 18L);

        // then
        assertEquals(pressure.getId(), foundPressure.getMeasurementId());
        assertEquals(pressure.getMember().getId(), foundPressure.getMemberId());
        assertEquals(pressure.getDiastolic(), foundPressure.getDiastolic());
        assertEquals(pressure.getSystolic(), foundPressure.getSystolic());
        assertEquals(pressure.getManual(), foundPressure.getManual());
        assertEquals(pressure.getNormality(), foundPressure.getNormality());
    }

    @Test
    public void 혈압_등록() throws Exception {
        // given
        PressureCreateRequestDto dto = new PressureCreateRequestDto();
        ReflectionTestUtils.setField(dto, "manual", "MANUAL");
        ReflectionTestUtils.setField(dto, "normality", "NORMAL");
        ReflectionTestUtils.setField(dto, "diastolic", 120);
        ReflectionTestUtils.setField(dto, "systolic", 140);

        Long fakePressureId = 18L;
        Pressure pressure = getPressure(100, 120, null);
        ReflectionTestUtils.setField(pressure, "id", fakePressureId);

        given(memberRepository.findById(anyLong())).willReturn(Optional.of(Member.createMember("test@email.com", "12345678")));
        given(pressureRepository.save(any())).willReturn(pressure);

        // when
        Long createdId = pressureService.createPressure(11L, dto);

        // then
        assertEquals(createdId, fakePressureId);
    }

    @Test
    public void 혈압_수정() throws Exception {
        // given
        Pressure pressure = getPressure(110, 120, Member.createMember("test@email.com", "12345678"));
        Integer newDiastolic = 115;
        Integer newSystolic = 125;
        Long fakePressureId = 18L;
        Long fakeMemberId = 11L;

        PressureUpdateRequestDto dto = new PressureUpdateRequestDto();
        ReflectionTestUtils.setField(dto, "systolic", newSystolic);
        ReflectionTestUtils.setField(dto, "diastolic", newDiastolic);
        ReflectionTestUtils.setField(dto, "memo", "updated");

        given(pressureRepository.findById(anyLong())).willReturn(Optional.ofNullable(pressure));

        // when
        PressureResponseDto updatedPressure = pressureService.updatePressure(fakeMemberId, fakePressureId, dto);

        // then
        //TODO: Integer 클래스 그대로 쓰면 assert안되는이유알아보기...
        assertEquals(newDiastolic, updatedPressure.getDiastolic());
        assertEquals(newSystolic, updatedPressure.getSystolic());
        assertEquals("updated", updatedPressure.getMemo());
    }
    
    @Test
    public void 자동측정_값수정_불가() throws Exception {
        // given
        Integer oldDiastolic = 110;
        Pressure pressure = getPressure(oldDiastolic, 120, Member.createMember("test@email.com", "12345678"));
        Long fakePressureId = 18L;
        Long fakeMemberId = 11L;
        ReflectionTestUtils.setField(pressure, "manual", Manual.AUTOMATIC);

        PressureUpdateRequestDto dto = new PressureUpdateRequestDto();
        ReflectionTestUtils.setField(dto, "diastolic", 120);
        ReflectionTestUtils.setField(dto, "memo", "updated");

        given(pressureRepository.findById(anyLong())).willReturn(Optional.ofNullable(pressure));

        // when
        PressureResponseDto updatedPressure = pressureService.updatePressure(fakeMemberId, fakePressureId, dto);

        // then
        //TODO: Integer 클래스 그대로 쓰면 assert안되는이유알아보기...
        assertEquals(oldDiastolic, updatedPressure.getDiastolic());
        assertEquals("updated", updatedPressure.getMemo());
    }

    @Test
    public void 혈압_삭제() throws Exception {
        // given
        Pressure pressure = getPressure(100, 120, Member.createMember("email@email.com", "12345678"));
        ReflectionTestUtils.setField(pressure, "id", 10L);

        given(pressureRepository.findById(anyLong())).willReturn(Optional.ofNullable(pressure));

        // when
        pressureService.deletePressure(10L, 10L);

        // then
        assertNotNull(pressure.getDeletedAt());
    }

    @Test
    public void 혈압_통계() throws Exception {
        // given
        Member member = Member.createMember("test@email.com", "12345678");

        Pressure pressure1 = getPressure(100, 120, member);
        Pressure pressure2 = getPressure(110, 130, member);
        Pressure pressure3 = getPressure(120, 140, member);

        List<Pressure> pressureList = new ArrayList<>();
        pressureList.add(pressure1);
        pressureList.add(pressure2);
        pressureList.add(pressure3);

        PressureSearch pressureSearch = PressureSearch.builder().build();

        given(pressureRepoImpl.findAll(anyLong(), any())).willReturn(pressureList);

        // when
        PressureStatisticResponseDto dto = pressureService.getPressureStatistic(18L, pressureSearch);

        // then
        assertEquals(Double.valueOf(110), dto.getDiastolicAverage());
        assertEquals(Integer.valueOf(100), dto.getDiastolicMin());
        assertEquals(Integer.valueOf(120), dto.getDiastolicMax());

        assertEquals(Double.valueOf(130), dto.getSystolicAverage());
        assertEquals(Integer.valueOf(120), dto.getSystolicMin());
        assertEquals(Integer.valueOf(140), dto.getSystolicMax());
    }

    private static Pressure getPressure(int diastolic, int systolic, Member member) {
        return Pressure.builder()
                .member(member)
                .memo("test")
                .manual(Manual.MANUAL)
                .normality(Normality.NORMAL)
                .diastolic(diastolic)
                .systolic(systolic)
                .build();
    }
}
