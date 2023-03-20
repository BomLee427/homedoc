package bom.proj.homedoc.domain.measure;

import bom.proj.homedoc.domain.BaseAuditingEntity;
import bom.proj.homedoc.domain.Member;
import bom.proj.homedoc.exception.NoResourceFoundException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속관계 매핑
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Measurement extends BaseAuditingEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "measurement_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(64) default 'MANUAL'")
    private Manual manual; //MANUAL, AUTOMATIC

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "varchar(64) default 'NORMAL'")
    private Normality normality; //NORMAL, ABNORMAL

    private String memo;
    private LocalDateTime measuredAt;

    protected void setMeasurement(Member member, Manual manual, Normality normality, String memo) {
        this.member = member;
        this.manual = manual;
        this.normality = normality;
        this.memo = memo;
        this.measuredAt = LocalDateTime.now();
    }

    public void updateToManual() {
        this.manual = Manual.MANUAL;
    }

    public void updateNormality(Normality normality) {
        this.normality = normality;
    }

    public void updateMemo(String memo) {
        this.memo = memo;
    }

    public void deleteMeasurement() {
        super.delete();
    }

    public boolean isValidAuthor(Long memberId) throws NoResourceFoundException {
        return (this.getMember() != null && (this.getMember().getId() == memberId));
    }
}
