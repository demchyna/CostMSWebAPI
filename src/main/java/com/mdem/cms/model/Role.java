package com.mdem.cms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdem.cms.model.common.IEntity;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "role")
public class Role implements IEntity, GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Pattern(regexp = "[a-z]+", message = "{role.name.pattern}")
    @Size(min = 2, max = 31, message = "{role.name.size}")
    private String name;
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<User> users;

    public Role() {
    }

    public Role(long id, String name, String description) {
        this.name = name;
        this.description = description;
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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return "ROLE_" + name.toUpperCase();
    }
}