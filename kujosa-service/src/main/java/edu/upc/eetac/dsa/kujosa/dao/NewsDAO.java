package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.News;

import java.sql.SQLException;

/**
 * Created by Kushal on 2/12/15.
 */
public  interface NewsDAO {
    public News createArticle(int articleid, String name, String content, String creator, String Tag) throws SQLException, NewsAlreadyExistsException;

    public News updateArticle(int articleid, String name, String content) throws SQLException;

    public News getArticleById(int articleid) throws SQLException;

    public News getArticleByName(String name) throws SQLException;
    public News getArticleByTag(String Tag) throws SQLException;


    public boolean deleteArticle(int articleid) throws SQLException;

}