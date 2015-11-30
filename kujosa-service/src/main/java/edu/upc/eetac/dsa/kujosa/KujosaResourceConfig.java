package edu.upc.eetac.dsa.kujosa;

import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * Created by sergio on 7/09/15.
 */
public class KujosaResourceConfig extends ResourceConfig {
    public KujosaResourceConfig() {
        packages("edu.upc.eetac.dsa.kujosa");
        packages("edu.upc.eetac.dsa.auth");
        packages("edu.upc.eetac.dsa.cors");
        register(RolesAllowedDynamicFeature.class);
        register(DeclarativeLinkingFeature.class);
    }
}
