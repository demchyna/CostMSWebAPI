package com.mdem.cms.DAO;

import com.mdem.cms.model.Category;

import java.util.List;

public interface ICategoryDao {
    List<Category> getCategoryByUserId(long userId);

}
