package edu.upc.eetac.dsa.kujosa;

/**
 * Created by sergio on 8/09/15.
 */
public interface KujosaMediaType {
    String KUJOSA_AUTH_TOKEN = "application/vnd.dsa.kujosa.auth-token+json";
    String KUJOSA_USER = "application/vnd.dsa.kujosa.user+json";
    String KUJOSA_STING = "application/vnd.dsa.kujosa.sting+json";
    String KUJOSA_STING_COLLECTION = "application/vnd.dsa.kujosa.sting.collection+json";
    String KUJOSA_ROOT = "application/vnd.dsa.kujosa.root+json";
    String KUJOSA_ERROR = "application/vnd.dsa.kujosa.error+json";
    String KUJOSA_API_COMMENT = "application/vnd.dsa.kujosa.comment+json";
    String KUJOSA_API_COMMENT_COLLECTION = "application/vnd.dsa.kujosa.comment.collection+json";
    String KUJOSA_API_EVENT = "application/vnd.dsa.kujosa.event+json";
    String KUJOSA_API_EVENT_COLLECTION = "application/vnd.dsa.kujosa.event.collection+json";
}
