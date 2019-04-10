package com.mdem.cms.service.impl;

import com.mdem.cms.DAO.impl.RoleDao;
import com.mdem.cms.exception.DataNotFoundException;
import com.mdem.cms.model.Role;
import com.mdem.cms.service.IRoleService;
import com.mdem.cms.service.common.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoleService extends AbstractService<Role, Long> implements IRoleService {

   private RoleDao roleDao;

    @Autowired
    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional
    public Role getRoleByName(String name) {
        Role role = roleDao.getRoleByName(name);
        if (role != null) {
            return role;
        } else {
            throw new DataNotFoundException("Role with name " + name  + " not found in database");
        }
    }
}
