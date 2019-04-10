package com.mdem.costms.service;

import com.mdem.costms.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getCategoriesByUserId(long userId);
}
