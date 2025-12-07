package AmazonLLDs.AlexaDeviceBatterySystem;

public interface BatteryInfo {
    boolean hasBattery();
    int getPercentage();
    void setPercentage(int p);
    boolean isCharging();
    void setCharging(boolean charging);
    String getBatteryStatus();
}