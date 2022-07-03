package com.sirius.security.service;

import java.util.Set;

public interface IUserManagementService {
    /**
     * should fail if the user name is taken
     * @param name
     * @param password
     */
    void createUser(String name, String password);

    /**
     * should fail if the user does not exist
     * @param name
     * @param password
     */
    void deleteUser(String name, String password);

    /**
     * should fail if the role name is taken
     * @param name
     */
    void createRole(String name);

    /**
     * should fail if the role does not exist
     * @param name
     */
    void deleteRole(String name);

    /**
     * Assign a role to a user. This method is idempotent.
     * @param userName
     * @param roleName
     */
    void addRoleToUser(String userName, String roleName);

    /**
     * Assign a role to a user. This method is idempotent.
     * @param userName
     * @param roleName
     */
    void deleteRoleFromUser(String userName, String roleName);

    /**
     * return token for successful logins
     * @param name
     * @param password
     * @return token
     */
    String authenticate(String name, String password);

    /**
     * invalidate token
     * @param token
     * @return
     */
    void invalidte(String token);


    /**
     *
     * @param token
     * @param role
     * @return true if the user belongs to the role.
     */
    Boolean checkRole(String token, String role);

    /**
     *
     * @param token
     * @return all roles for the user
     */
    Set<String> getAllRoles(String token);
}
