package com.sirius.security.persistence.impl;

import com.sirius.security.model.Role;
import com.sirius.security.persistence.IRoleRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RoleRepository implements IRoleRepository {
    private Map<String, Role> roleMap;

    public RoleRepository() {
        roleMap = new ConcurrentHashMap();
    }

    @Override
    public Boolean containsRole(String roleName) {
        return roleMap.containsKey(roleName);
    }

    @Override
    public Role getRole(String roleName) {
        return roleMap.get(roleName);
    }

    @Override
    public void addRole(Role role) {
        roleMap.put(role.getName(), role);
    }

    @Override
    public void deleteRole(String roleName) {
        if (roleName == null) {
            return;
        }
        roleMap.remove(roleName);
    }
}
