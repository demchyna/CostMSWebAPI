package com.mdem.cms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdem.cms.model.common.IEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "meter")
public class Meter implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL)
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
