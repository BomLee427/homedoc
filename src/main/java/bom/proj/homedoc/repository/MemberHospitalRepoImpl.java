package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.hospital.MemberHospital;
import bom.proj.homedoc.domain.hospital.QHospital;
import bom.proj.homedoc.domain.hospital.QMemberHospital;
import bom.proj.homedoc.dto.query.MemberHospitalQueryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberHospitalRepoImpl implements MemberHospitalRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final QMemberHospital qMemberHospital = QMemberHospital.memberHospital;
    private final QHospital qHospital = QHospital.hospital;

    @Override
    public Optional<MemberHospital> findByMemberIdAndHospitalHashcode(Long memberId, String hashCode) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(qMemberHospital)
                .where(
                        qMemberHospital.member.id.eq(memberId),
                        qMemberHospital.hospital.hashCode.eq(hashCode),
                        qMemberHospital.deletedAt.isNull()
                ).fetchOne());
    }

    @Override
    public Page<MemberHospitalQueryDto> findAllMemberHospital(Long memberId, Pageable pageable) {
        List<MemberHospitalQueryDto> memberHospitalList = jpaQueryFactory.select(
                Projections.constructor(
                        MemberHospitalQueryDto.class,
                        qMemberHospital.id,
                        qHospital.id.as("hospitalId"),
                        qHospital.hashCode,
                        qHospital.name,
                        qHospital.department,
                        qHospital.address,
                        qHospital.phoneNumber
                ))
                .from(qMemberHospital)
                .join(qHospital)
                .on(qMemberHospital.hospital.id.eq(qHospital.id))
                .where(qMemberHospital.member.id.eq(memberId),
                        qMemberHospital.deletedAt.isNull())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
        return new PageImpl<>(memberHospitalList, pageable, memberHospitalList.size());
    }
}
