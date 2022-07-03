package com.sirius.security.persistence.impl;

import com.sirius.security.model.Token;
import com.sirius.security.persistence.ITokenRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In MySQL, there should be a table with three columns: user id, token, and expiration date
 */
public class TokenRepository implements ITokenRepository {

    private Map<String, Token> tokenMap;
    private Map<String, String> tokenUserMap;

    public TokenRepository() {
        tokenMap = new ConcurrentHashMap<>();
        tokenUserMap = new ConcurrentHashMap<>();
    }

    @Override
    public Token getToken(String tokenKey) {
        return tokenMap.get(tokenKey);
    }

    @Override
    public void addTokenToUser(Token token, String username) {
        tokenMap.put(token.getName(), token);
        tokenUserMap.put(token.getName(), username);
    }

    @Override
    public void deleteToken(String tokenKey) {
        if (tokenKey == null) {
            return;
        }
        if (tokenMap.containsKey(tokenKey)) {
            tokenMap.remove(tokenKey);
        }
        if (tokenUserMap.containsKey(tokenKey)) {
            tokenUserMap.remove(tokenKey);
        }
    }

    @Override
    public String getUserNameByToken(String tokenKey) {
        return tokenUserMap.get(tokenKey);
    }

}
