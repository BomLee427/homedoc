package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.measure.Glucose;
import bom.proj.homedoc.search.GlucoseSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlucoseRepositoryCustom {
    List<Glucose> findAll(Long memberId, GlucoseSearch glucoseSearch);
    Page<Glucose> findAll(Long memberId, GlucoseSearch glucoseSearch, Pageable pageable);
}
