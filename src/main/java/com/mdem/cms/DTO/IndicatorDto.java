package com.mdem.cms.DTO;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;

public class IndicatorDto {
    private Long id;

    @NotNull(message = "{indicator.current.notNull}")
    @Range(min = 1L, max = 9999999999L, message = "{indicator.current.range}")
    private Long current;

    @NotNull(message = "{indicator.date.notNull}")
    private java.sql.Date date;

    @NotNull(message = "{indicator.payment.notNull}")
    @DecimalMin(value = "0.0", message = "{indicator.payment.decimalMin}")
    @DecimalMax(value = "999999.99999", message = "{indicator.payment.decimalMax}")
    private BigDecimal payment;
    private String description;
    private Long previous;
    private Long previousId;
    @NotNull private Long meterId;

    @NotNull(message = "{indicator.tariffId.notNull}")
    private Long tariffId;
    private BigDecimal tariffRate;
    private String tariffCurrency;
    private String unitName;
    private BigDecimal price;

    public IndicatorDto() {
    }

    public IndicatorDto(Long id, Long current, Date date, BigDecimal payment, String description, Long previous, Long previousId, Long meterId, Long tariffId, BigDecimal tariffRate, String tariffCurrency, String unitName, BigDecimal price) {
        this.id = id;
        this.current = current;
        this.date = date;
        this.payment = payment;
        this.description = description;
        this.previous = previous;
        this.previousId = previousId;
        this.meterId = meterId;
        this.tariffId = tariffId;
        this.tariffRate = tariffRate;
        this.tariffCurrency = tariffCurrency;
        this.unitName = unitName;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrevious() {
        return previous;
    }

    public void setPrevious(Long previous) {
        this.previous = previous;
    }

    public Long getPreviousId() {
        return previousId;
    }

    public void setPreviousId(Long previousId) {
        this.previousId = previousId;
    }

    public Long getMeterId() {
        return meterId;
    }

    public void setMeterId(Long meterId) {
        this.meterId = meterId;
    }

    public Long getTariffId() {
        return tariffId;
    }

    public void setTariffId(Long tariffId) {
        this.tariffId = tariffId;
    }

    public BigDecimal getTariffRate() {
        return tariffRate;
    }

    public void setTariffRate(BigDecimal tariffRate) {
        this.tariffRate = tariffRate;
    }

    public String getTariffCurrency() {
        return tariffCurrency;
    }

    public void setTariffCurrency(String tariffCurrency) {
        this.tariffCurrency = tariffCurrency;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
