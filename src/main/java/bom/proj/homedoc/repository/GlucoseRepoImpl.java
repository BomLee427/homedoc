package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.measure.*;
import bom.proj.homedoc.search.GlucoseSearch;
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
public class GlucoseRepoImpl implements GlucoseRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QGlucose qGlucose = QGlucose.glucose;

    @Override
    public List<Glucose> findAll(Long memberId, GlucoseSearch glucoseSearch) {
        return jpaQueryFactory.selectFrom(qGlucose)
                .where(
                        qGlucose.member.id.eq(memberId),
                        qGlucose.deletedAt.isNull(),
                        mealEq(glucoseSearch.getMeal()),
                        fastedEq(glucoseSearch.getFasted()),
                        glucoseLessOrEqual(glucoseSearch.getGluUpperCriteria()),
                        glucoseGraterOrEqual(glucoseSearch.getGluLowerCriteria()),
                        manual(glucoseSearch.getManual()),
                        measuredAfter(glucoseSearch.getStartDate()),
                        measuredBefore(glucoseSearch.getEndDate())
                ).fetch();
    }

    @Override
    public Page<Glucose> findAll(Long memberId, GlucoseSearch glucoseSearch, Pageable pageable) {
        List<Glucose> glucoseList = jpaQueryFactory.selectFrom(qGlucose)
                .where(
                        qGlucose.member.id.eq(memberId),
                        qGlucose.deletedAt.isNull(),
                        mealEq(glucoseSearch.getMeal()),
                        fastedEq(glucoseSearch.getFasted()),
                        glucoseLessOrEqual(glucoseSearch.getGluUpperCriteria()),
                        glucoseGraterOrEqual(glucoseSearch.getGluLowerCriteria()),
                        manual(glucoseSearch.getManual()),
                        measuredAfter(glucoseSearch.getStartDate()),
                        measuredBefore(glucoseSearch.getEndDate())
                ).limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(qGlucose.measuredAt.desc())
                .fetch();
        return new PageImpl<>(glucoseList, pageable, glucoseList.size());
    }

    private BooleanExpression mealEq(Meal meal) {
        return meal != null ? qGlucose.meal.eq(meal) : null;
    }

    private BooleanExpression fastedEq(Fasted fasted) {
        return fasted != null ? qGlucose.fasted.eq(fasted) : null;
    }

    private BooleanExpression glucoseLessOrEqual(Double value) {
        return value != null ? qGlucose.value.loe(value) : null;
    }

    private BooleanExpression glucoseGraterOrEqual(Double value) {
        return value != null ? qGlucose.value.goe(value) : null;
    }

    private BooleanExpression manual(Manual manual) {
        return manual != null ? qGlucose.manual.eq(manual) : null;
    }

    private BooleanExpression isAbnormal(Normality normality) {
        return normality != null ? qGlucose.normality.eq(Normality.ABNORMAL) : null;
    }

    private BooleanExpression measuredAfter(LocalDateTime startDate) {
        return startDate != null ? qGlucose.measuredAt.after(startDate) : null;
    }

    private BooleanExpression measuredBefore(LocalDateTime endDate) {
        return endDate != null ? qGlucose.measuredAt.before(endDate) : null;
    }
}
