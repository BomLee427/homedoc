package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.hospital.Hospital;
import bom.proj.homedoc.domain.hospital.MemberHospital;
import bom.proj.homedoc.dto.query.MemberHospitalQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberHospitalRepositoryCustom {

    Page<MemberHospitalQueryDto> findAllMemberHospital(Long memberId, Pageable pageable);

    Optional<MemberHospital> findByMemberIdAndHospitalHashcode(Long memberId, String hashCode);
}
