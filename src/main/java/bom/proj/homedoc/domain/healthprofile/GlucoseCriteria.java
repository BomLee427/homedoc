package bom.proj.homedoc.domain.healthprofile;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

//MEMO: 미구현
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GlucoseCriteria {

    @Builder
    public GlucoseCriteria(Integer afterMealGoeCriteria, Integer fastedGoeCriteria) {
        this.afterMealGoeCriteria = afterMealGoeCriteria;
        this.fastedGoeCriteria = fastedGoeCriteria;
    }

    private Integer afterMealGoeCriteria;
    private Integer fastedGoeCriteria;

}
