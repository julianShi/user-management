package com.sirius.security.persistence;

import com.sirius.security.model.Token;

public interface ITokenRepository {
    /**
     *
     * @param tokenKey
     * @return user of the token
     */
    Token getToken(String tokenKey);

    /**
     *
     * @param token
     * @param user
     */
    void addTokenToUser(Token token, String user);

    /**
     *
     * @param tokenKey
     * @return
     */
    String getUserNameByToken(String tokenKey);

    void deleteToken(String tokenKey);
}
