package com.mdem.cms.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CategoryDto {

    private Long id;

    @NotNull
    @Pattern(regexp = "[a-zA-Zа-яА-ЯіІїЇєЄ]+.*", message = "{category.name.pattern}")
    @Size(min = 2, max = 31, message = "{category.name.size}")
    private String name;
    private String description;
    @NotNull private Long userId;

    public CategoryDto() {
    }

    public CategoryDto(Long id, String name, String description, Long userId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
