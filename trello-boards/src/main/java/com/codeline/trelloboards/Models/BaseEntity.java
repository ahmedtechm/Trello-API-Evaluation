package com.codeline.trelloboards.Models;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@Data
public class BaseEntity {
    Date createdDate;
    Date updatedDate;
    Boolean isActive;

    public BaseEntity() {
        this.createdDate = new Date();
        this.updatedDate = new Date();
        this.isActive = true;
    }
}
