package AmazonLLDs.AlexaDeviceBatterySystem;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Battery implements BatteryInfo{
    private final AtomicInteger percentage = new AtomicInteger(100);
    private final AtomicBoolean charging = new AtomicBoolean(false);

    @Override
    public boolean hasBattery() {
        return true;
    }

    @Override
    public int getPercentage() {
        return percentage.get();
    }

    @Override
    public void setPercentage(int p) {
        if (p < 0 || p > 100) throw new IllegalArgumentException("Battery % invalid");
        percentage.set(p);
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
            return "Device is charging, battery at " + getPercentage() + "%";
        }
        return "Battery at " + getPercentage() + "%";
    }
}
