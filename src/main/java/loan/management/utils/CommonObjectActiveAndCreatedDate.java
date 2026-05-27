package loan.management.utils;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


@MappedSuperclass
public class CommonObjectActiveAndCreatedDate extends PanacheEntityBase {
    @Column(
            name = "active_datetime",
            nullable = false
    )
    public @NotNull(
            message = "Please provide a active datetime"
    ) LocalDateTime activeDatetime;
    @Column(
            name = "inactive_datetime",
            nullable = true
    )
    public LocalDateTime inactiveDatetime;
    @Column(
            name = "isactive",
            nullable = false
    )
    public @NotNull(
            message = "Please provide a isactive"
    ) boolean isactive;
    @Column(
            name = "version",
            nullable = false
    )
    public @NotNull(
            message = "Please provide a version"
    ) Long version;
    @Column(
            name = "created_by",
            nullable = false
    )
    public @NotNull(
            message = "Please provide a created by"
    ) Long createdBy;
    @Column(
            name = "created_datetime",
            nullable = false
    )
    public @NotNull(
            message = "Please provide a created datetime"
    ) LocalDateTime createdDatetime;
    @Column(
            name = "updated_by",
            nullable = false
    )
    public @NotNull(
            message = "Please provide a updated by"
    ) Long updatedBy;
    @Column(
            name = "updated_datetime",
            nullable = false
    )
    public @NotNull(
            message = "Please provide a updated datetime"
    ) LocalDateTime updatedDatetime;

    public CommonObjectActiveAndCreatedDate() {
    }

    public LocalDateTime getActiveDatetime() {
        return this.activeDatetime;
    }

    public void setActiveDatetime(LocalDateTime activeDatetime) {
        this.activeDatetime = activeDatetime;
    }

    public LocalDateTime getInactiveDatetime() {
        return this.inactiveDatetime;
    }

    public void setInactiveDatetime(LocalDateTime inactiveDatetime) {
        this.inactiveDatetime = inactiveDatetime;
    }

    public boolean isIsactive() {
        return this.isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public Long getVersion() {
        return this.version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDatetime() {
        return this.createdDatetime;
    }

    public void setCreatedDatetime(LocalDateTime createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public Long getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedDatetime() {
        return this.updatedDatetime;
    }

    public void setUpdatedDatetime(LocalDateTime updatedDatetime) {
        this.updatedDatetime = updatedDatetime;
    }
}
