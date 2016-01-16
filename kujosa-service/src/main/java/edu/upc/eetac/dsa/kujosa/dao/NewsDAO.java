package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.entity.News;
import edu.upc.eetac.dsa.kujosa.entity.NewsCollection;

import java.sql.SQLException;

/**
 * Created by kushal on 16/12/15.
 */
public interface NewsDAO {
    public News createNews(int userid, String headline, String body) throws SQLException;
    public News getNewsbyheadline(String headline) throws SQLException;
    public News getNewsbyuser(int userid) throws SQLException;
    public NewsCollection getNews(long timestamp, boolean before) throws SQLException;
    public News updateNews(int userid, String headline, String body) throws SQLException;
    public boolean deleteNews(String headline) throws SQLException;
}
