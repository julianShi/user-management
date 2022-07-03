package com.sirius.security.persistence;

import com.sirius.security.model.User;

public interface IUserRepository {
    Boolean containsUser(String username);
    User getUser(String username);
    void addUser(User user);
    void removeUser(String username);
}
