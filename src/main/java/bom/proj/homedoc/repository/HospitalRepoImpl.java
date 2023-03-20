package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.hospital.Department;
import bom.proj.homedoc.domain.hospital.Hospital;
import bom.proj.homedoc.domain.hospital.QHospital;
import bom.proj.homedoc.search.HospitalSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class HospitalRepoImpl implements HospitalRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QHospital qHospital = QHospital.hospital;

    @Override
    public List<Hospital> findAll(HospitalSearch hospitalSearch) {
        return jpaQueryFactory.selectFrom(qHospital)
                .where(
                        qHospital.name.like(hospitalSearch.getName()),
                        qHospital.department.eq(hospitalSearch.getDepartment()),
                        qHospital.address.city.like(hospitalSearch.getCity()),
                        qHospital.deletedAt.isNull())
                .fetch();
    }

    @Override
    public Page<Hospital> findAll(HospitalSearch hospitalSearch, Pageable pageable) {
        List<Hospital> hospitalList = jpaQueryFactory.selectFrom(qHospital)
                .where(
                        nameLike(hospitalSearch.getName()),
                        departmentEq(hospitalSearch.getDepartment()),
                        cityLike(hospitalSearch.getCity()),
                        qHospital.deletedAt.isNull())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
        return new PageImpl<>(hospitalList, pageable, hospitalList.size());
    }

    private BooleanExpression nameLike(String name) {
        return !name.isEmpty() ? qHospital.name.like(name) : null;
    }

    private BooleanExpression departmentEq(Department department) {
        return department != null ? qHospital.department.eq(department) : null;
    }

    private BooleanExpression cityLike(String city) {
        return !city.isEmpty() ? qHospital.address.city.like(city) : null;
    }
}
