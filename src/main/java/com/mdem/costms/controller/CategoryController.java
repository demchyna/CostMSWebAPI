package com.mdem.costms.controller;

import com.mdem.costms.DTO.CategoryDto;
import com.mdem.costms.model.Category;
import com.mdem.costms.model.User;
import com.mdem.costms.service.impl.CategoryService;
import com.mdem.costms.service.impl.UserService;
import com.mdem.costms.transformer.CategoryTransformer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/category")
@Api(tags = {"Category"}, description = "Operations for work with categories of services")
public class CategoryController {

    private CategoryService categoryService;
    private UserService userService;

    @Autowired
    public CategoryController(CategoryService categoryService, UserService userService) {
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #categoryDto.userId == authentication.details.id)")
    @ApiOperation(value = "Add a new category")
    public void createCategory(@Validated @RequestBody CategoryDto categoryDto) {
        User user = userService.getById(categoryDto.getUserId());
        Category category = CategoryTransformer.toCategoryEntity(categoryDto, user);
        categoryService.create(category);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and returnObject.userId == authentication.details.id)")
    @ApiOperation(value = "Search a category with an ID", response = Category.class)
    public CategoryDto getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getById(id);
        return CategoryTransformer.toCategoryDto(category);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #categoryDto.userId == authentication.details.id)")
    @ApiOperation(value = "Update an existing category")
    public void updateCategory(@Validated @RequestBody CategoryDto categoryDto) {
        User user = userService.getById(categoryDto.getUserId());
        Category category = CategoryTransformer.toCategoryEntity(categoryDto, user);
        categoryService.update(category);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and #categoryDto.userId == authentication.details.id)")
    @ApiOperation(value = "Delete an existing category")
    public void deleteCategory(@Validated @RequestBody CategoryDto categoryDto) {
        User user = userService.getById(categoryDto.getUserId());
        Category category = CategoryTransformer.toCategoryEntity(categoryDto, user);
        categoryService.delete(category);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "View a list of available categories", response = Iterable.class)
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryService.getAll();
        List<CategoryDto> categoriesDto = new ArrayList<>();
        for (Category category : categories) {
            categoriesDto.add(CategoryTransformer.toCategoryDto(category));
        }
        return categoriesDto;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    @PostAuthorize("hasRole('ADMIN') or (hasRole('USER') and #userId == authentication.details.id)")
    @ApiOperation(value = "View a list of available categories for selected user", response = Iterable.class)
    public List<CategoryDto> getCategoriesByUserId(@PathVariable Long userId) {
        List<Category> categories =  categoryService.getCategoriesByUserId(userId);
        List<CategoryDto> categoriesDto = new ArrayList<>();
        for (Category category : categories) {
            categoriesDto.add(CategoryTransformer.toCategoryDto(category));
        }
        return categoriesDto;
    }
}