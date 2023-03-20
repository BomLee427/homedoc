package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.hospital.Hospital;
import bom.proj.homedoc.domain.hospital.MemberHospital;
import bom.proj.homedoc.dto.query.MemberHospitalQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberHospitalRepositoryCustom {
    List<MemberHospital> findByMemberAndHospital(Member member, Hospital hospital);
    List<MemberHospital> findByMemberIdAndHospitalHashcode(Long memberId, String hashCode);

    public Page<MemberHospitalQueryDto> findAllMemberHospital(Long memberId, Pageable pageable);
}
