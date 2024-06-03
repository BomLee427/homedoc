package bom.proj.homedoc.search;

import bom.proj.homedoc.domain.measure.Manual;
import bom.proj.homedoc.domain.measure.Normality;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@SuperBuilder
abstract public class MeasureSearch {

    private Manual manual;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
