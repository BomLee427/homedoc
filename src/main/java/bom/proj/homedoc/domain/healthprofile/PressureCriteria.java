package bom.proj.homedoc.domain.healthprofile;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
//MEMO: 미구현
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PressureCriteria {

    @Builder
    public PressureCriteria(Integer systolicGoeCriteria, Integer systolicLoeCriteria, Integer diastolicGoeCriteria, Integer diastolicLoeCriteria) {
        this.systolicGoeCriteria = systolicGoeCriteria;
        this.systolicLoeCriteria = systolicLoeCriteria;
        this.diastolicGoeCriteria = diastolicGoeCriteria;
        this.diastolicLoeCriteria = diastolicLoeCriteria;
    }

    private Integer systolicGoeCriteria;
    private Integer systolicLoeCriteria;
    private Integer diastolicGoeCriteria;
    private Integer diastolicLoeCriteria;

}
