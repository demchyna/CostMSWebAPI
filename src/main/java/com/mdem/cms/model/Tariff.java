package com.mdem.cms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdem.cms.model.common.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "tariff")
public class Tariff implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String currency;
    private BigDecimal rate;

    @Column(name = "begin_date")
    private java.sql.Date beginDate;

    @Column(name = "end_date")
    private java.sql.Date endDate;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToMany(mappedBy = "tariff", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Indicator> indicators;

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

    public java.sql.Date getBeginDate() {
      return beginDate;
    }

    public void setBeginDate(java.sql.Date beginDate) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public List<Indicator> getIndicators() {
        return indicators;
    }

    public void setIndicators(List<Indicator> indicators) {
        this.indicators = indicators;
    }
}
