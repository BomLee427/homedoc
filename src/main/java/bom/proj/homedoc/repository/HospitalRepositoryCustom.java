package bom.proj.homedoc.repository;

import bom.proj.homedoc.domain.hospital.Hospital;
import bom.proj.homedoc.search.HospitalSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HospitalRepositoryCustom {
    List<Hospital> findAll(HospitalSearch hospitalSearch);
    Page<Hospital> findAll(HospitalSearch hospitalSearch, Pageable pageable);
}
