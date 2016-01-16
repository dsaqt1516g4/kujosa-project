package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.News;
import edu.upc.eetac.dsa.kujosa.entity.NewsCollection;

import java.sql.SQLException;

/**
 * Created by kushal on 16/12/15.
 */
public interface NewsDAO {
    public News createNews(String userid, String headline, String body) throws SQLException;
    News getNewsById(String id) throws SQLException;
    public NewsCollection getNews(long timestamp, boolean before) throws SQLException;
    public News updateNews(String id, String headline, String body) throws SQLException;
    public boolean deleteNews(String id) throws SQLException;
}
