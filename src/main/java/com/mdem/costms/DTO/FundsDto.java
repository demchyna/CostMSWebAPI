package com.mdem.costms.DTO;

import java.math.BigDecimal;
import java.sql.Date;

public class FundsDto {

    private Long id;
    private java.sql.Date date;
    private String source;
    private BigDecimal value;
    private String currency;
    private String type;
    private String description;
    private Long userId;

    public FundsDto() {
    }

    public FundsDto(Long id, Date date, String source, BigDecimal value, String currency, String type, String description, Long userId) {
        this.id = id;
        this.date = date;
        this.source = source;
        this.value = value;
        this.currency = currency;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
