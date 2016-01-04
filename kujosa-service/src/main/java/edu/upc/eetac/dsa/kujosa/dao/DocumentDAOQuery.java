package edu.upc.eetac.dsa.kujosa.dao;

/**
 * Created by juan on 16/12/15.
 */
public class DocumentDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_DOCUMENT = "insert into events (id, userid, name, description, path) values (?,?,?,?,?)";

/*    private String GET_NEWS_QUERY = "select * from document where creation_timestamp < ifnull(?, now()) desc limit ?";
    private String GET_NEWS_QUERY_FROM_LAST = "select * from document where creation_timestamp > ifnull(?, now()) desc limit ?";
    private String INSERT_NEWS_QUERY="insert into document (userid, name, description) values (?, ?, ?)";
    private String UPDATE_NEWS_QUERY = "update document set description = ifnull(?, description) where docid = ?";
    private String DELETE_NEWS_QUERY = "delete from document where docid = ?";*/
}
