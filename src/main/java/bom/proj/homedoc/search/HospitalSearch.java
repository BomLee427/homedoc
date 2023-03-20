package bom.proj.homedoc.search;

import bom.proj.homedoc.domain.hospital.Department;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HospitalSearch {
    private String name;
    private Department department;
    private String city;
}
