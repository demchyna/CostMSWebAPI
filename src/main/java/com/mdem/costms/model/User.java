package com.mdem.costms.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mdem.costms.model.common.IEntity;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User implements IEntity, UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Pattern(regexp = "[A-ZА-ЯІЇЄ][a-zа-яіїє]+", message = "{user.firstName.pattern}")
    @Size(min = 2, max = 31, message = "{user.firstName.size}")
    @Column(name = "first_name")
    private String firstName;

    @Pattern(regexp = "[A-ZА-ЯІЇЄ][a-zа-яіїє]+", message = "{user.lastName.pattern}")
    @Size(min = 2, max = 31, message = "{user.lastName.size}")
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Pattern(regexp = "[a-zA-Z0-9_\\-]+", message = "{user.username.pattern}")
    @Size(min = 2, max = 31, message = "{user.username.size}")
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "{user.email.pattern}")
    private String email;

    @NotNull
    @Column(name="create_date", nullable = false)
    private java.sql.Timestamp createDate = new Timestamp(System.currentTimeMillis());

    private String description;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Role.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH })
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> authorities;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Category> categories;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public java.sql.Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(java.sql.Timestamp createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Collection<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<Role> authorities) {
        this.authorities = authorities;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return false;
    }
}
