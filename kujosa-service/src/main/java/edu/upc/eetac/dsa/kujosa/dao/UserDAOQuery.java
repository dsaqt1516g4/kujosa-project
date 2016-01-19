package edu.upc.eetac.dsa.kujosa.dao;

/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 *     DONE:
 *     -createUser
 *     -getUserByLoginid
 *     -updateUser
 *     -deleteUser
 */
public interface UserDAOQuery {
    String UUID = "select REPLACE(UUID(),'-','')";
    String CREATE_USER = "insert into users (id, loginid, password, email,fullname, image) values (UNHEX(?), ?, UNHEX(MD5(?)), ?, ?, ?)";
    String UPDATE_USER = "update users set email=?, fullname=? , image=? where id=UNHEX(?)";
    String ASSIGN_ROLE_REGISTERED = "insert into user_roles (userid, role) values (UNHEX(?), 'registered')";
    String GET_USER_BY_ID = "select hex(u.id) as id, u.loginid, u.email, u.fullname from users u where id=unhex(?)";
    String GET_USER_BY_USERNAME = "select hex(u.id) as id, u.loginid, u.email, u.fullname, u.image from users u where u.loginid=?";
    String DELETE_USER = "delete from users where id= UNHEX(?)";
    String GET_PASSWORD =  "select hex(password) as password from users where id=unhex(?)";
    String IS_ADMIN = "select role from users_role where id= UNHEX(?)";

}
