package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.Article;

import java.sql.SQLException;

/**
 * Created by Kushal on 2/12/15.
 */
public  interface ArticleDAO {
    public Article createArticle(int articleid, String name, String content, String creator, String Tag) throws SQLException, ArticleAlreadyExistsException;

    public Article updateArticle(int articleid, String name, String content) throws SQLException;

    public Article getArticleById(int articleid) throws SQLException;

    public Article getArticleByName(String name) throws SQLException;
    public Article getArticleByTag(String Tag) throws SQLException;


    public boolean deleteArticle(int articleid) throws SQLException;

}