package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.News;
import edu.upc.eetac.dsa.kujosa.entity.NewsCollection;

import java.sql.SQLException;

/**
 * Created by kushal on 16/12/15.
 */
public interface NewsDAO {
    News createNews(String userid, String headline, String body) throws SQLException;
    News getNewsById(String id) throws SQLException;
    NewsCollection getNews(long timestamp, boolean before) throws SQLException;
    News updateNews(String id, String headline, String body) throws SQLException;
    boolean deleteNews(String id) throws SQLException;
    //public News getNewsByUser(String id) throws SQLException;
    }
