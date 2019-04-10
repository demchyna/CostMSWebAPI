package com.mdem.cms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdem.cms.model.common.IEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "unit")
public class Unit implements IEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Pattern(regexp = "[a-zA-Zа-яА-ЯіІїЇєЄ]+.*", message = "{unit.name.pattern}")
    @Size(min = 2, max = 31, message = "{unit.name.size}")
    private String name;
    private String description;

    @OneToMany(mappedBy = "unit")
    @JsonIgnore
    private List<Meter> meters;

    @OneToMany(mappedBy = "unit")
    @JsonIgnore
    private List<Tariff> tariffs;

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

    public List<Meter> getMeters() {
      return meters;
    }

    public void setMeters(List<Meter> meters) {
      this.meters = meters;
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }
}
