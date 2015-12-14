package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Article;

import java.sql.SQLException;

/**
 * Created by twerky on 2/12/15.
 */
public class ArticleDAOImpl implements ArticleDAO {
    @Override
    public Article createArticle(int articleid, String name, String content, String creator, String Tag) throws SQLException, ArticleAlreadyExistsException {
        return null;
    }

    @Override
    public Article updateArticle(int articleid, String name, String content) throws SQLException {
        return null;
    }

    @Override
    public Article getArticleById(int articleid) throws SQLException {
        return null;
    }

    @Override
    public Article getArticleByName(String name) throws SQLException {
        return null;
    }

    @Override
    public Article getArticleByTag(String Tag) throws SQLException {
        return null;
    }

    @Override
    public boolean deleteArticle(int articleid) throws SQLException {
        return false;
    }
}
