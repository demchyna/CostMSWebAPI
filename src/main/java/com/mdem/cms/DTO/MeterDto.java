package com.mdem.cms.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MeterDto {

    private Long id;

    @NotNull
    @Pattern(regexp = "[a-zA-Zа-яА-ЯіІїЇєЄ]+.*", message = "{meter.name.pattern}")
    @Size(min = 2, max = 31, message = "{meter.name.size}")
    private String name;
    private String description;
    @NotNull private Long categoryId;

    @NotNull(message = "{meter.unit.notNull}")
    private Long unitId;

    public MeterDto() {
    }

    public MeterDto(Long id, String name, String description, Long categoryId, Long unitId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.unitId = unitId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }
}
