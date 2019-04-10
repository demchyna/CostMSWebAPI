package com.mdem.cms.DAO.impl;

import com.mdem.cms.DAO.IRoleDao;
import com.mdem.cms.DAO.common.AbstractDao;
import com.mdem.cms.model.Role;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class RoleDao extends AbstractDao<Role, Long> implements IRoleDao {
    @Override
    public Role getRoleByName(String name) {
        Query query = getSession().createQuery("FROM " + Role.class.getName() + " where name = :name");
        query.setParameter("name", name);
        return (Role) query.uniqueResult();
    }
}
