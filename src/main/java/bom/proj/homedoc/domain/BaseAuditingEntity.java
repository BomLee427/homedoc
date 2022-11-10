package bom.proj.homedoc.domain;

import bom.proj.homedoc.exception.NoResourceFoundException;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditingEntity {
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt = null;

    protected void delete() {
        deletedAt = LocalDateTime.now();
    }

    public boolean isSoftDeleted() {
        return deletedAt != null;
    }

//    //TODO: 지네릭 알맞게 썼는지 복습하기
//    public <T extends BaseAuditingEntity> List<T> removeSoftDeleted(List<T> list) {
//        return list.stream().filter(i -> i.getDeletedAt() == null).collect(Collectors.toList());
//    }

    /**
     * Soft Deleted 검증
     */
    public static <T extends BaseAuditingEntity> T filterSoftDeleted(T t) throws NoResourceFoundException {
        if (t == null || t.getDeletedAt() != null) {
            throw new NoResourceFoundException();
        }
        return t;
    }
}
