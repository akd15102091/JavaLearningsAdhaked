package DesignNotificationService;

import java.util.Set;

public class UserPreferences {
    private final Set<NotificationChannel> enabledChannels;

    public UserPreferences(Set<NotificationChannel> enabledChannels) {
        this.enabledChannels = enabledChannels;
    }

    public boolean isChannelEnabled(NotificationChannel channel) {
        return enabledChannels.contains(channel);
    }
}
