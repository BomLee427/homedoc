package bom.proj.homedoc.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class PressureSearch extends MeasureSearch {

    private Integer sysGoeCriteria;
    private Integer sysLoeCriteria;
    private Integer diasGoeCriteria;
    private Integer diasLoeCriteria;
}
