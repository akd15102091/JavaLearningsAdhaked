package DesignFeatureFlagWithCaching;
import java.util.HashSet;
import java.util.Set;

public class FeatureFlag {
    private String name; // unique flag name
    private boolean enabled; // global status
    private String environment; // "dev", "prod", etc.
    private Set<String> targetedUsers; // per-user override

    public FeatureFlag(String name, boolean enabled, String environment, Set<String> targetedUsers){
        this.name = name;
        this.enabled = enabled;
        this.environment = environment;
        this.targetedUsers = targetedUsers != null ? targetedUsers : new HashSet<>();
    }

    public String getName() { return name; }
    public boolean isEnabled() { return enabled; }
    public String getEnvironment() { return environment; }
    public Set<String> getTargetedUsers() { return targetedUsers; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }
    public void setEnvironment(String environment) { this.environment = environment; }
    public void setTargetedUsers(Set<String> targetedUsers) {
        this.targetedUsers = targetedUsers != null ? targetedUsers : new HashSet<>();
    }

    // Composite key to differentiate same flag name in different environments
    public String getKey() {
        return name + ":" + environment;
    }
}
