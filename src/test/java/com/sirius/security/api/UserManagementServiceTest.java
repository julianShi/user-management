package com.sirius.security.api;

import com.sirius.security.service.IUserManagementService;
import com.sirius.security.service.impl.UserManagementService;
import org.junit.Assert;
import org.junit.Test;

public class UserManagementServiceTest {

    final static String USER_NAME_1 = "USER_NAME_1";
    final static String USER_NAME_2 = "USER_NAME_2";

    final static String PASS_WORD_1 = "PASS_WORD_1";
    final static String PASS_WORD_2 = "PASS_WORD_2";

    final static String ROLE_1 = "VIEWER";
    final static String ROLE_2 = "OPERATOR";

    @Test
    public void testUserManagement() {
        IUserManagementService service = new UserManagementService();

        // test create user
        service.createUser(USER_NAME_1, PASS_WORD_1);

        // it should fail to create another user with the same name
        Assert.assertThrows(IllegalArgumentException.class, () -> service.createUser(USER_NAME_1, PASS_WORD_1));

        // test create user
        service.createUser(USER_NAME_2, PASS_WORD_2);

        // test delete user
        service.deleteUser(USER_NAME_1, PASS_WORD_1);

        // It should fail to delete an empty user
        Assert.assertThrows(IllegalArgumentException.class, () -> service.deleteUser(USER_NAME_1, PASS_WORD_1));
    }

    @Test
    public void testRoleManagement() {
        IUserManagementService service = new UserManagementService();

        // test create role
        service.createRole(ROLE_1);

        // it should fail to create another role of the same name
        Assert.assertThrows(IllegalArgumentException.class, () -> service.createRole(ROLE_1));

        // test create role
        service.createRole(ROLE_2);

        // test delete role
        service.deleteRole(ROLE_1);

        // It should fail to delete an empty role
        Assert.assertThrows(IllegalArgumentException.class, () -> service.deleteRole(ROLE_1));
    }

    @Test
    public void testToken() {
        IUserManagementService service = new UserManagementService();

        // test create user
        service.createUser(USER_NAME_1, PASS_WORD_1);

        String tokenKey1 = service.authenticate(USER_NAME_1, PASS_WORD_1);

        String tokenKey2 = service.authenticate(USER_NAME_1, PASS_WORD_1);

        Assert.assertNotEquals(tokenKey1, tokenKey2);

        Assert.assertThrows(IllegalArgumentException.class, () -> service.getAllRoles(tokenKey1));

        service.invalidte(tokenKey2);

        Assert.assertThrows(IllegalArgumentException.class, () -> service.getAllRoles(tokenKey2));
    }

    @Test
    public void testAddRoleToUser() {
        IUserManagementService service = new UserManagementService();

        // test create user
        service.createUser(USER_NAME_1, PASS_WORD_1);

        // test create role
        service.createRole(ROLE_1);

        // test create role
        service.addRoleToUser(USER_NAME_1, ROLE_1);

        // test can add twice
        service.addRoleToUser(USER_NAME_1, ROLE_1);

        String tokenKey = service.authenticate(USER_NAME_1, PASS_WORD_1);

        Assert.assertTrue(service.checkRole(tokenKey, ROLE_1));

        Assert.assertFalse(service.checkRole(tokenKey, ROLE_2));

        Assert.assertEquals(1, service.getAllRoles(tokenKey).size());
        Assert.assertTrue(service.getAllRoles(tokenKey).contains(ROLE_1));
    }
}