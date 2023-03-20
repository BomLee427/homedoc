package bom.proj.homedoc.service;

import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.domain.hospital.Hospital;
import bom.proj.homedoc.domain.hospital.MemberHospital;
import bom.proj.homedoc.dto.response.MemberHospitalResponseDto;
import bom.proj.homedoc.exception.NoResourceFoundException;
import bom.proj.homedoc.repository.MemberHospitalRepoImpl;
import bom.proj.homedoc.repository.HospitalRepository;
import bom.proj.homedoc.repository.MemberHospitalRepository;
import bom.proj.homedoc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberHospitalService {

    private final MemberRepository memberRepository;
    private final HospitalRepository hospitalRepository;
    private final MemberHospitalRepoImpl memberHospitalRepoImpl;
    private final MemberHospitalRepository memberHospitalRepository;

    @Transactional(readOnly = true)
    public List<MemberHospitalResponseDto> getHospitalJoinList(Long memberId, Pageable pageable) {
        return memberHospitalRepoImpl.findAllMemberHospital(memberId, pageable).stream().map(MemberHospitalResponseDto::fromQueryDto).collect(Collectors.toList());
    }

    public Long joinHospital(Long memberId, String hashCode) {
        checkRegisted(memberId, hashCode); //MEMO: 중복이 존재할 경우 쿼리 한번으로 종료되도록 순서 변경
        Member member = memberRepository.findByIdAndDeletedAtNull(memberId)
                .orElseThrow(NoResourceFoundException::new);
        Hospital hospital = hospitalRepository.findByHashCodeAndDeletedAtNull(hashCode)
                .orElseThrow(NoResourceFoundException::new);
        MemberHospital memberHospital = MemberHospital.joinHospital(member, hospital);
        memberHospitalRepository.save(memberHospital);
        return memberHospital.getId();
    }

    private void checkRegisted(Long memberId, String hashCode) throws DuplicateKeyException {
        memberHospitalRepoImpl.findByMemberIdAndHospitalHashcode(memberId, hashCode).ifPresent(m -> {throw new DuplicateKeyException("HOSPITAL_REGISTED");});
    }

    public Long unJoinHospital(Long memberId, Long memberHospitalId) {
        MemberHospital memberHospital = memberHospitalRepository.findByIdAndMemberIdAndDeletedAtNull(memberHospitalId, memberId)
                .orElseThrow(NoResourceFoundException::new);
        memberHospital.unJoin();
        return memberHospital.getId();
    }
}
