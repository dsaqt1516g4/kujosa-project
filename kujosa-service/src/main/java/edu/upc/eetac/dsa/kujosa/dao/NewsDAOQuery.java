package edu.upc.eetac.dsa.kujosa.dao;

/**
 * Created by Kushal on 2/12/15.
 */

public interface NewsDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_ARTICLE = "insert into articles (id, name, content, creator, Tag) values (UNHEX(?), ?, UNHEX(MD5(?)), ?, ?);";
    public final static String UPDATE_ARTICLE = "update articles set name=?, content=? where id=unhex(?)";
    public final static String GET_ARTICLE_BY_ID = "select hex(u.id) as id, u.articleid, u.name, u.content from users u where id=unhex(?)";
    public final static String GET_ARTICLE_BY_NAME = "select hex(u.id) as id, u.articleid, u.name, u.content from article u where u.articleid=?";
    public final static String GET_ARTICLE_BY_TAG = "select hex(u.id) as id, u.articleid, u.name, u.content from article u where u.articleid=?";
    public final static String DELETE_ARTICLE = "delete from articles where id=unhex(?)";
}
