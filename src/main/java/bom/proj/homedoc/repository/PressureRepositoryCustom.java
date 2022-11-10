package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.measure.Pressure;
import bom.proj.homedoc.search.PressureSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PressureRepositoryCustom {
    List<Pressure> findAll(Long memberId, PressureSearch pressureSearch);
    Page<Pressure> findAll(Long memberId, PressureSearch pressureSearch, Pageable pageable);
}
