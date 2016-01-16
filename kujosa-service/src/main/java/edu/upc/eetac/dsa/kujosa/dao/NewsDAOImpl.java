package edu.upc.eetac.dsa.kujosa.dao;

import edu.upc.eetac.dsa.kujosa.db.Database;
import edu.upc.eetac.dsa.kujosa.entity.News;
import edu.upc.eetac.dsa.kujosa.entity.NewsCollection;

import java.sql.*;

/**
 * Created by twerky on 16/12/15.
 */
public class NewsDAOImpl implements NewsDAO{
    @Override
    public News createNews(String userid, String headline, String body) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id  = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(NewsDAOQuery.CREATE_NEWS);
            stmt.setString(1, id);
            stmt.setString(2, headline);
            stmt.setString(3, body);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getNewsById(userid);
    }

    @Override
    public News getNewsById(String id) throws SQLException {
        News news = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(NewsDAOQuery.GET_NEWS_BY_USER);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                news = new News();
                news.setId(rs.getString("id"));
                news.setUserid(rs.getString("userid"));
                news.setHeadline(rs.getString("headline"));
                news.setBody(rs.getString("body"));
                news.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                news.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return news;
    }

    @Override
    public NewsCollection getNews(long timestamp, boolean before) throws SQLException {
        NewsCollection newsCollection = new NewsCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if (before)
                stmt = connection.prepareStatement(NewsDAOQuery.GET_NEWS);
            else
                stmt = connection.prepareStatement(NewsDAOQuery.GET_NEWS_AFTER);
            stmt.setTimestamp(1, new Timestamp(timestamp));

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                News news = new News();
                news.setId(rs.getString("id"));
                news.setUserid(rs.getString("userid"));
                news.setHeadline(rs.getString("headline"));
                news.setBody(rs.getString("body"));
                news.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                news.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    newsCollection.setNewestTimestamp(news.getLastModified());
                    first = false;
                }
                newsCollection.setOldestTimestamp(news.getLastModified());
                newsCollection.getNews().add(news);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return newsCollection;
    }

    @Override
    public News updateNews(String id, String headline, String body) throws SQLException {
        News news = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(NewsDAOQuery.UPDATE_NEWS);
            stmt.setString(1, id);
            stmt.setString(2, headline);
            stmt.setString(3, body);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                news = getNewsById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return news;
    }

    @Override
    public boolean deleteNews(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(NewsDAOQuery.DELETE_NEWS);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}
