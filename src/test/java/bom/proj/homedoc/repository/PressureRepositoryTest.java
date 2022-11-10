package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.measure.Manual;
import bom.proj.homedoc.domain.measure.Pressure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

@DataJpaTest
public class PressureRepositoryTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PressureRepository pressureRepository;

    @Test
    public void save() throws Exception {
        // given
        Pressure pressure = getPressure(getMember());

        // when
        Pressure findPressure = pressureRepository.save(pressure);

        // then
        assertEquals(pressure, findPressure);
    }

    @Test
    public void findAllByDeletedAtNull() throws Exception {
        // given
        Member member = getMember();
        Pressure pre1 = getPressure(member);
        pressureRepository.save(pre1);
        Pressure pre2 = getPressure(member);
        pressureRepository.save(pre2);
        Pressure pre3 = getPressure(member);
        pressureRepository.save(pre3).deleteMeasurement();
        
        // when
        Page<Pressure> pressureList = pressureRepository.findAllByMemberIdAndDeletedAtNull(member.getId(), PageRequest.of(0, 100));
        
        // then
        assertEquals(2, pressureList.getTotalElements());
    }
    
    @Test
    public void findAllBetween() throws Exception {
        // given
        Member member = getMember();

        Pressure pre1 = getPressure(member);
        ReflectionTestUtils.setField(pre1, "measuredAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        pressureRepository.save(pre1);

        Pressure pre2 = getPressure(member);
        ReflectionTestUtils.setField(pre2, "measuredAt", LocalDateTime.of(2010, 1, 1, 0, 0, 0));
        pressureRepository.save(pre2);

        Pressure pre3 = getPressure(member);
        pressureRepository.save(pre3);

        // when
        Page<Pressure> pressureList = pressureRepository.findAllByMemberIdAndDeletedAtNullAndMeasuredAtBetween(
                member.getId(),
                PageRequest.of(0, 100),
                LocalDateTime.of(2009, 1, 1, 0, 0, 0),
                LocalDateTime.of(2011, 1, 1, 0, 0, 0));

        // then
        assertEquals(1, pressureList.getTotalElements());
    }
    
    @Test
    public void findAllBefore() throws Exception {
        // given
        Member member = getMember();

        Pressure pre1 = getPressure(member);
        ReflectionTestUtils.setField(pre1, "measuredAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        pressureRepository.save(pre1);

        Pressure pre2 = getPressure(member);
        ReflectionTestUtils.setField(pre2, "measuredAt", LocalDateTime.of(2010, 1, 1, 0, 0, 0));
        pressureRepository.save(pre2);

        Pressure pre3 = getPressure(member);
        pressureRepository.save(pre3);

        // when
        Page<Pressure> pressureList = pressureRepository.findAllByMemberIdAndDeletedAtNullAndMeasuredAtBefore(
                member.getId(),
                PageRequest.of(0, 100),
                LocalDateTime.of(2011, 1, 1, 0, 0, 0));

        // then
        assertEquals(2, pressureList.getTotalElements());
    }

    @Test
    public void findAllAfter() throws Exception {
        // given
        Member member = getMember();

        Pressure pre1 = getPressure(member);
        ReflectionTestUtils.setField(pre1, "measuredAt", LocalDateTime.of(2000, 1, 1, 0, 0, 0));
        pressureRepository.save(pre1);

        Pressure pre2 = getPressure(member);
        ReflectionTestUtils.setField(pre2, "measuredAt", LocalDateTime.of(2010, 1, 1, 0, 0, 0));
        pressureRepository.save(pre2);

        Pressure pre3 = getPressure(member);
        pressureRepository.save(pre3);

        // when
        Page<Pressure> pressureList = pressureRepository.findAllByMemberIdAndDeletedAtNullAndMeasuredAtAfter(
                member.getId(),
                PageRequest.of(0, 100),
                LocalDateTime.of(2009, 1, 1, 0, 0, 0));

        // then
        assertEquals(2, pressureList.getTotalElements());
    }

    private Member getMember() {
        Member member = Member.createDirectMember(Timestamp.from(Instant.now()) + "@test.com");
        em.persist(member);
        em.flush();

        return member;
    }

    private Pressure getPressure(Member member) {
        Pressure pressure = Pressure.builder()
                .systolic(100)
                .diastolic(120)
                .manual(Manual.AUTOMATIC)
                .member(member)
                .memo("test")
                .build();
        return pressure;
    }

}