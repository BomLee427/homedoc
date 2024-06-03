package bom.proj.homedoc.search;

import bom.proj.homedoc.domain.measure.Fasted;
import bom.proj.homedoc.domain.measure.Meal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
public class GlucoseSearch extends MeasureSearch {
    private Fasted fasted;
    private Meal meal;
    private Double gluUpperCriteria;
    private Double gluLowerCriteria;
}
