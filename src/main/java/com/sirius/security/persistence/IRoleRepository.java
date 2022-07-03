package com.sirius.security.persistence;

import com.sirius.security.model.Role;

public interface IRoleRepository {
    /**
     *
     * @param roleName
     * @return
     */
    Boolean containsRole(String roleName);

    /**
     *
     * @param roleName
     * @return
     */
    Role getRole(String roleName);

    /**
     *
     * @param role
     */
    void addRole(Role role);

    /**
     *
     * @param role
     */
    void deleteRole(String role);
}
