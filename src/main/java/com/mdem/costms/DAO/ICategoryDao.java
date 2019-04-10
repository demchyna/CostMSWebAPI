package com.mdem.costms.DAO;

import com.mdem.costms.model.Category;

import java.util.List;

public interface ICategoryDao {
    List<Category> getCategoriesByUserId(long userId);

}
