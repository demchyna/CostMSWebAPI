package com.mdem.costms.DTO;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.sql.Date;

public class FundsDto {

    private Long id;

    @NotNull(message = "{funds.date.notNull}")
    private java.sql.Date date;

    @NotNull
    @Pattern(regexp = "[a-zA-Zа-яА-ЯіІїЇєЄ]+.*", message = "{funds.source.pattern}")
    @Size(min = 2, max = 200, message = "{funds.source.size}")
    private String source;

    @NotNull(message = "{funds.value.notNull}")
    @DecimalMin(value = "0.0", message = "{funds.value.decimalMin}")
    @DecimalMax(value = "999999.99999", message = "{funds.value.decimalMax}")
    private BigDecimal value;

    @NotNull
    @Pattern(regexp = "[a-zA-Zа-яА-ЯіІїЇєЄ]+.*", message = "{funds.currency.pattern}")
    @Size(min = 2, max = 31, message = "{funds.currency.size}")
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
