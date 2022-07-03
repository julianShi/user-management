package com.sirius.security.service.impl;

import com.sirius.security.model.Role;
import com.sirius.security.model.Token;
import com.sirius.security.model.User;
import com.sirius.security.persistence.IRoleRepository;
import com.sirius.security.persistence.ITokenRepository;
import com.sirius.security.persistence.IUserRepository;
import com.sirius.security.persistence.impl.RoleRepository;
import com.sirius.security.persistence.impl.TokenRepository;
import com.sirius.security.persistence.impl.UserRepository;
import com.sirius.security.service.IUserManagementService;
import com.sirius.security.util.SecurityUtil;
import lombok.NonNull;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public class UserManagementService implements IUserManagementService {
    private IRoleRepository iRoleRepository;
    private IUserRepository iUserRepository;
    private ITokenRepository iTokenRepository;
    private Integer tokenExpirationHours;

    /**
     * Spring Framework is not allowed in this project. So I inject dependencies using constructor.
     * @param iRoleRepository
     */
    public UserManagementService(IRoleRepository iRoleRepository, IUserRepository iUserRepository,
                                 ITokenRepository iTokenRepository, Integer tokenExpirationHours) {
        this.iRoleRepository = iRoleRepository;
        this.iUserRepository = iUserRepository;
        this.iTokenRepository = iTokenRepository;
        this.tokenExpirationHours = tokenExpirationHours;
    }

    /**
     * The constructor of the built-in dependencies
     */
    public UserManagementService() {
        this.iRoleRepository = new RoleRepository();
        this.iUserRepository = new UserRepository();
        this.iTokenRepository = new TokenRepository();
        this.tokenExpirationHours = 2;
    }

    @Override
    public void createUser(String name, String password) {
        if (iUserRepository.containsUser(name)) {
            throw new IllegalArgumentException("User already exists. Please create the account with a different name");
        }
        User user = new User();
        user.setName(name);
        user.setPasswordEncrypted(SecurityUtil.encryptPassword(password));
        iUserRepository.addUser(user);
    }

    @Override
    public void deleteUser(String name, String password) {
        login(name, password);
        iUserRepository.removeUser(name);
    }

    @Override
    public void createRole(@NonNull String name) {
        if (iRoleRepository.containsRole(name)) {
            throw new IllegalArgumentException("Existing role. Please use a different name");
        }
        Role role = new Role();
        role.setName(name);
        iRoleRepository.addRole(role);
    }

    @Override
    public void deleteRole(@NonNull String name) {
        if (!iRoleRepository.containsRole(name)) {
            throw new IllegalArgumentException("None-existing role.");
        }
        iRoleRepository.deleteRole(name);
    }

    @Override
    public void addRoleToUser(@NonNull String userName, @NonNull String roleName) {
        User user = iUserRepository.getUser(userName);
        Role role = iRoleRepository.getRole(roleName);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user name");
        }
        if (role == null) {
            throw new IllegalArgumentException("Invalid role name");
        }
        user.getRoles().add(roleName);
    }

    @Override
    public void deleteRoleFromUser(String userName, String roleName) {
        User user = iUserRepository.getUser(userName);
        Role role = iRoleRepository.getRole(roleName);
        if (user == null) {
            throw new IllegalArgumentException("Invalid user name");
        }
        if (role == null) {
            throw new IllegalArgumentException("Invalid role name");
        }
        user.getRoles().remove(roleName);
    }

    @Override
    public String authenticate(@NonNull String username, @NonNull String password) {
        User user = login(username, password);

        // delete old token
        iTokenRepository.deleteToken(user.getTokenKey());

        // create new token
        String tokenKey = UUID.randomUUID().toString();
        Token token = new Token();
        token.setName(tokenKey);
        token.setTokenExpirationDate(OffsetDateTime.now().plusHours(tokenExpirationHours));
        iTokenRepository.addTokenToUser(token, username);
        user.setTokenKey(tokenKey);

        return tokenKey;
    }

    /**
     * authenticate
     * @param name
     * @param password
     */
    public User login(@NonNull String name, @NonNull String password) {
        User user = iUserRepository.getUser(name);
        if (null == user) {
            throw new IllegalArgumentException("Invalid username");
        }
        if (!user.getPasswordEncrypted().equals(SecurityUtil.encryptPassword(password))) {
            throw new IllegalArgumentException("Invalid password");
        }
        return user;
    }

    public User loginWithToken(@NonNull String tokenKey) {
        Token token = iTokenRepository.getToken(tokenKey);
        if (token == null) {
            throw new IllegalArgumentException("Invalid token");
        }
        if (OffsetDateTime.now().isAfter(token.getTokenExpirationDate())) {
            throw new IllegalArgumentException("Expired token");
        }
        String username = iTokenRepository.getUserNameByToken(tokenKey);
        return iUserRepository.getUser(username);
    }

    @Override
    public void invalidte(@NonNull String tokenKey) {
        iTokenRepository.deleteToken(tokenKey);
    }

    @Override
    public Boolean checkRole(@NonNull String token, @NonNull String roleName) {
        User user = loginWithToken(token);
        return user.getRoles().contains(roleName);
    }

    @Override
    public Set<String> getAllRoles(@NonNull String token) {
        User user = loginWithToken(token);
        return user.getRoles();
    }
}


