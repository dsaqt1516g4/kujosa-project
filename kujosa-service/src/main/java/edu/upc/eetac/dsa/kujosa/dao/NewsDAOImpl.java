package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.News;

import java.sql.SQLException;

/**
 * Created by twerky on 2/12/15.
 */
public class NewsDAOImpl implements NewsDAO {
    @Override
    public News createArticle(int articleid, String name, String content, String creator, String Tag) throws SQLException, NewsAlreadyExistsException {
        return null;
    }

    @Override
    public News updateArticle(int articleid, String name, String content) throws SQLException {
        return null;
    }

    @Override
    public News getArticleById(int articleid) throws SQLException {
        return null;
    }

    @Override
    public News getArticleByName(String name) throws SQLException {
        return null;
    }

    @Override
    public News getArticleByTag(String Tag) throws SQLException {
        return null;
    }

    @Override
    public boolean deleteArticle(int articleid) throws SQLException {
        return false;
    }
}
