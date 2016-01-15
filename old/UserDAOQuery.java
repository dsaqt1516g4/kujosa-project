package edu.upc.eetac.dsa.kujosa.dao;

/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 *     DONE:
 *     -createUser
 *     -getUserByUsername
 *     -updateUser
 *     -deleteUser
 */
public interface UserDAOQuery {
<<<<<<< HEAD
    String UUID = "select REPLACE(UUID(),'-','')";
    String CREATE_USER = "insert into users (userid, username, password, fullname,email , image) values (UNHEX(?), ?, UNHEX(MD5(?)), ?, ?,?)";
    String UPDATE_USER = "update users set email=?, name=? , image=? where username=?";
    String ASSIGN_ROLE_REGISTERED = "insert into user_roles (userid, role) values (UNHEX(?), 'registered')";
    String GET_USER_BY_ID = "select hex(u.userid) as userid, u.loginid, u.email, u.fullname from users u where id=unhex(?)";
    String GET_USER_BY_USERNAME = "select hex(u.id) as id, u.username, u.email, u.name, u.image from users u where u.username=?";
    String DELETE_USER = "delete from users where id= hex(?)";
    String GET_PASSWORD =  "select hex(password) as password from users where username=?";
=======
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_USER = "insert into users (userid, username, userpass, name ,email , image) values (UNHEX(?), ?, UNHEX(MD5(?)), ?, ?,?)";
    public final static String UPDATE_USER = "update users set email=?, name=? , image=? where username=?";
    public final static String ASSIGN_ROLE_REGISTERED = "insert into user_roles (userid, role) values (UNHEX(?), 'registered')";
    public final static String GET_USER_BY_ID = "select hex(u.userid) as userid, u.loginid, u.email, u.name from users u where id=unhex(?)";
    public final static String GET_USER_BY_USERNAME = "select hex(u.userid) as id, u.username, u.email, u.name, u.image from users u where u.username=?";
    public final static String DELETE_USER = "delete from users where id= hex(?)";
    public final static String GET_PASSWORD =  "select hex(password) as password from users where username=?";
>>>>>>> 7f7ac7c9a77122dd2179cf694985ce5dc716d29d
}
