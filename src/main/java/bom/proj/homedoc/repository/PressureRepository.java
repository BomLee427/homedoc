package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.measure.Pressure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PressureRepository extends JpaRepository<Pressure, Long> {

    List<Pressure> findAllByMemberIdAndDeletedAtNull(Long memberId);
    Page<Pressure> findAllByMemberIdAndDeletedAtNull(Long memberId, PageRequest pageRequest);

    List<Pressure> findAllByMemberIdAndDeletedAtNullAndMeasuredAtAfter(Long memberId, LocalDateTime startDate);
    Page<Pressure> findAllByMemberIdAndDeletedAtNullAndMeasuredAtAfter(Long memberId, PageRequest pageRequest, LocalDateTime startDate);

    List<Pressure> findAllByMemberIdAndDeletedAtNullAndMeasuredAtBefore(Long memberId, LocalDateTime endDate);
    Page<Pressure> findAllByMemberIdAndDeletedAtNullAndMeasuredAtBefore(Long memberId, PageRequest pageRequest, LocalDateTime endDate);

    List<Pressure> findAllByMemberIdAndDeletedAtNullAndMeasuredAtBetween(Long memberId, LocalDateTime startDate, LocalDateTime endDate);
    Page<Pressure> findAllByMemberIdAndDeletedAtNullAndMeasuredAtBetween(Long memberId, PageRequest pageRequest, LocalDateTime startDate, LocalDateTime endDate);
}
