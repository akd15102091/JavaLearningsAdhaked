package AmazonLLDs.AlexaDeviceBatterySystem.devices;

import AmazonLLDs.AlexaDeviceBatterySystem.BatteryInfo;

// =====================================================
// Base Alexa Device (Abstract Class) 
// =====================================================
public abstract class AlexaDevice {
    protected final BatteryInfo batteryInfo;

    protected AlexaDevice(BatteryInfo batteryInfo) {
        this.batteryInfo = batteryInfo;
    }

    public void show() {
        System.out.println(batteryInfo.getBatteryStatus());
    }

    public BatteryInfo getBatteryInfo() {
        return this.batteryInfo;
    }
}
