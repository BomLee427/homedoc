package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.measure.Pressure;
import bom.proj.homedoc.search.PressureSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PressureRepositoryCustom {
    List<Pressure> findAll(Long memberId, PressureSearch pressureSearch);
    Page<Pressure> findAll(Long memberId, PressureSearch pressureSearch, Pageable pageable);
}
