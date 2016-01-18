package edu.upc.eetac.dsa.kujosa;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**    +-------------------------------------+
 *     |           KUJOSA PROJECT            |
 *     +-------------------------------------+
 *
 * READY FOR TEST
 */
public class KujosaResourceConfig extends ResourceConfig {
    public KujosaResourceConfig() {
        packages("edu.upc.eetac.dsa.kujosa");
        packages("edu.upc.eetac.dsa.auth");
        packages("edu.upc.eetac.dsa.cors");
        register(RolesAllowedDynamicFeature.class);
        register(DeclarativeLinkingFeature.class);
        register(JacksonFeature.class);
    }
}
