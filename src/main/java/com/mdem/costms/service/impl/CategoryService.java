package com.mdem.costms.service.impl;

import com.mdem.costms.DAO.impl.CategoryDao;
import com.mdem.costms.DAO.impl.UserDao;
import com.mdem.costms.exception.DataNotFoundException;
import com.mdem.costms.model.Category;
import com.mdem.costms.model.User;
import com.mdem.costms.service.ICategoryService;
import com.mdem.costms.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CategoryService extends AbstractService<Category, Long> implements ICategoryService {

    private CategoryDao categoryDao;
    private UserDao userDao;

    @Autowired
    public void setCategoryDao(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public List<Category> getCategoriesByUserId(long userId) {
        User user = userDao.getById(userId);
        if (user == null) {
            throw new DataNotFoundException("Record with id " + userId  +" not found in database");
        }
        return categoryDao.getCategoriesByUserId(userId);
    }
}