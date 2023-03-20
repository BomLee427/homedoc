package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.hospital.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Optional<Hospital> findByHashCodeAndDeletedAtNull(String hashCode);

    Optional<Hospital> findByIdAndDeletedAtNull(Long id);
}
