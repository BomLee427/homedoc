package bom.proj.homedoc.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class PressureSearch extends MeasureSearch {

    private Integer sysLowerLimit;
    private Integer sysUpperCriteria;
    private Integer diasLowerCriteria;
    private Integer diasUpperCriteria;
}
