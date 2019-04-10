package com.mdem.costms.DTO;

import java.math.BigDecimal;
import java.sql.Date;

public class OutlayDto {
    private Long id;
    private java.sql.Date date;
    private String source;
    private BigDecimal value;
    private String currency;
    private String description;
    private Long userId;

    public OutlayDto(Long id, Date date, String source, BigDecimal value, String currency, String description, Long userId) {
        this.id = id;
        this.date = date;
        this.source = source;
        this.value = value;
        this.currency = currency;
        this.description = description;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
