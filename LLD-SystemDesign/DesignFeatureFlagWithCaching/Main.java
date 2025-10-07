package DesignFeatureFlagWithCaching;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        FeatureFlagService service = new FeatureFlagService(new InMemoryFeatureFlagRepository());

        FeatureFlag devFlag = new FeatureFlag("new-ui", false, "dev", Set.of("devUser"));
        FeatureFlag prodFlag = new FeatureFlag("new-ui", true, "prod", Set.of("prodUser"));

        service.createFeatureFlag(devFlag);
        service.createFeatureFlag(prodFlag);

        System.out.println(service.getFeatureFlag("new-ui", "devUser", "dev") != null);   // true (user override)
        System.out.println(service.getFeatureFlag("new-ui", "anyone", "dev") != null);    // false (globally disabled)
        System.out.println(service.getFeatureFlag("new-ui", "anyone", "prod") != null);   // true (globally enabled)
        System.out.println(service.getFeatureFlag("new-ui", "prodUser", "prod") != null); // true (user override)
    }
}

