package AmazonLLDs.AlexaDeviceBatterySystem;

import java.util.concurrent.atomic.AtomicBoolean;

public class NoBattery implements BatteryInfo{
    private final AtomicBoolean charging = new AtomicBoolean(false);

    @Override
    public boolean hasBattery() {
        return false;
    }

    @Override
    public int getPercentage() {
        return -1; // not applicable
    }

    @Override
    public void setPercentage(int p) {
        // No-op
    }

    @Override
    public boolean isCharging() {
        return charging.get();
    }

    @Override
    public void setCharging(boolean c) {
        charging.set(c);
    }

    @Override
    public String getBatteryStatus() {
        if (isCharging()) {
            return "Device is charging but has no battery";
        }
        return "Battery not available";
    }
}
