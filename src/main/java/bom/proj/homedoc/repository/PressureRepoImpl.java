package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.measure.Normality;
import bom.proj.homedoc.domain.measure.Pressure;
import bom.proj.homedoc.domain.measure.QPressure;
import bom.proj.homedoc.search.PressureSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PressureRepoImpl implements PressureRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QPressure qPressure = QPressure.pressure;

    @Override
    public List<Pressure> findAll(Long memberId, PressureSearch pressureSearch) {
        List<Pressure> pressureList = jpaQueryFactory.selectFrom(qPressure)
                .where(
                        qPressure.member.id.eq(memberId),
                        qPressure.deletedAt.isNull(),
                        systolicGreaterOrEqual(pressureSearch.getSysGoeCriteria()),
                        systolicLessOrEqual(pressureSearch.getSysLoeCriteria()),
                        diastolicGreaterOrEqual(pressureSearch.getDiasGoeCriteria()),
                        diastolicLessOrEqual(pressureSearch.getDiasLoeCriteria()),
                        diastolicLessOrEqual(pressureSearch.getDiasLoeCriteria()),
                        isAbnormal(pressureSearch.getNormality()),
                        measuredAfter(pressureSearch.getStartDate()),
                        measuredBefore(pressureSearch.getEndDate())
                ).fetch();
        return pressureList;
    }

    @Override
    public Page<Pressure> findAll(Long memberId, PressureSearch pressureSearch, Pageable pageable) {
        List<Pressure> pressureList = jpaQueryFactory.selectFrom(qPressure)
                .where(
                        qPressure.member.id.eq(memberId),
                        qPressure.deletedAt.isNull(),
                        systolicGreaterOrEqual(pressureSearch.getSysGoeCriteria()),
                        systolicLessOrEqual(pressureSearch.getSysLoeCriteria()),
                        diastolicGreaterOrEqual(pressureSearch.getDiasGoeCriteria()),
                        diastolicLessOrEqual(pressureSearch.getDiasLoeCriteria()),
                        diastolicLessOrEqual(pressureSearch.getDiasLoeCriteria()),
                        isAbnormal(pressureSearch.getNormality()),
                        measuredAfter(pressureSearch.getStartDate()),
                        measuredBefore(pressureSearch.getEndDate())
                ).limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
        return new PageImpl<>(pressureList, pageable, pressureList.size());
    }

    private BooleanExpression systolicGreaterOrEqual(Integer sysGoeCriteria) {
        return sysGoeCriteria != null ? qPressure.systolic.goe(sysGoeCriteria) : null;
    }

    private BooleanExpression systolicLessOrEqual(Integer sysLoeCriteria) {
        return sysLoeCriteria != null ? qPressure.systolic.loe(sysLoeCriteria) : null;
    }

    private BooleanExpression diastolicGreaterOrEqual(Integer diasGoeCriteria) {
        return diasGoeCriteria != null ? qPressure.diastolic.goe(diasGoeCriteria) : null;
    }

    private BooleanExpression diastolicLessOrEqual(Integer diasLoeCriteria) {
        return diasLoeCriteria != null ? qPressure.diastolic.loe(diasLoeCriteria) : null;
    }

    private BooleanExpression isAbnormal(Normality normality) {
        return normality != null ? qPressure.normality.eq(Normality.ABNORMAL) : null;
    }

    private BooleanExpression measuredAfter(LocalDateTime startDate) {
        return startDate != null ? qPressure.measuredAt.after(startDate) : null;
    }

    private BooleanExpression measuredBefore(LocalDateTime endDate) {
        return endDate != null ? qPressure.measuredAt.before(endDate) : null;
    }
}
