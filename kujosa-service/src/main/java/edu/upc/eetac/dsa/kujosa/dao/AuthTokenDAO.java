package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.auth.UserInfo;
import edu.upc.eetac.dsa.kujosa.entity.AuthToken;

import java.sql.SQLException;

/**
 * Created by sergio on 7/09/15.
 */
public interface AuthTokenDAO {
    UserInfo getUserByAuthToken(String token) throws SQLException;
    AuthToken createAuthToken(String userid) throws SQLException;
    void deleteToken(String userid) throws  SQLException;
}