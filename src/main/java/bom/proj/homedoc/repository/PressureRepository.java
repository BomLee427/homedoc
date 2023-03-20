package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.measure.Pressure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PressureRepository extends JpaRepository<Pressure, Long> {
    Optional<Pressure> findByIdAndDeletedAtNull(Long measurementId);
}
