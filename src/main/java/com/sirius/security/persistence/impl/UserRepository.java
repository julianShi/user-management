package com.sirius.security.persistence.impl;

import com.sirius.security.model.User;
import com.sirius.security.persistence.IUserRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository implements IUserRepository {
    private Map<String, User> userMap;

    public UserRepository() {
        userMap = new ConcurrentHashMap<>();
    }

    @Override
    public Boolean containsUser(String username) {
        return userMap.containsKey(username);
    }

    @Override
    public User getUser(String username) {
        return userMap.get(username);
    }

    @Override
    public void addUser(User user) {
        userMap.put(user.getName(), user);
    }

    @Override
    public void removeUser(String username) {
        if (username == null) {
            return;
        }
        userMap.remove(username);
    }
}
