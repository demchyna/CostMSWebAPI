package com.mdem.cms.DTO;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Date;

public class TariffDto {
    private Long id;

    @NotNull
    @Pattern(regexp = "[a-zA-Zа-яА-ЯіІїЇєЄ]+.*", message = "{tariff.name.pattern}")
    @Size(min = 2, max = 31, message = "{tariff.name.size}")
    private String name;

    @NotNull
    @Pattern(regexp = "[a-zA-Zа-яА-ЯіІїЇєЄ]+.*", message = "{tariff.currency.pattern}")
    @Size(min = 2, max = 31, message = "{tariff.currency.size}")
    private String currency;

    @NotNull(message = "{tariff.rate.notNull}")
    @DecimalMin(value = "0.1", message = "{tariff.rate.decimalMin}")
    @DecimalMax(value = "999999.99999", message = "{tariff.rate.decimalMax}")
    private java.math.BigDecimal rate;

    @NotNull(message = "{tariff.beginDate.notNull}")
    private java.sql.Date beginDate;
    private java.sql.Date endDate;
    private String description;
    @NotNull private Long categoryId;

    @NotNull(message = "{tariff.unit.notNull}")
    private Long unitId;
    private String unitName;

    public TariffDto() {
    }

    public TariffDto(Long id, String name, String currency, BigDecimal rate, Date begin_date, java.sql.Date endDate, String description, Long categoryId, Long unitId, String unitName) {
        this.id = id;
        this.name = name;
        this.currency = currency;
        this.rate = rate;
        this.beginDate = begin_date;
        this.endDate = endDate;
        this.description = description;
        this.categoryId = categoryId;
        this.unitId = unitId;
        this.unitName = unitName;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public java.sql.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.sql.Date endDate) {
        this.endDate = endDate;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
