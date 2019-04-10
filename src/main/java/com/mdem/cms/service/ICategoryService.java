package com.mdem.cms.service;

import com.mdem.cms.model.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getCategoryByUserId(long userId);
}
