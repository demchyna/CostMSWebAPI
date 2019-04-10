package com.mdem.costms.service.impl;

import com.mdem.costms.DAO.impl.RoleDao;
import com.mdem.costms.exception.DataNotFoundException;
import com.mdem.costms.model.Role;
import com.mdem.costms.service.IRoleService;
import com.mdem.costms.service.common.AbstractService;
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
