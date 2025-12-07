package AmazonLLDs.AlexaDeviceBatterySystem;

import AmazonLLDs.AlexaDeviceBatterySystem.devices.AudioAlexaDevice;
import AmazonLLDs.AlexaDeviceBatterySystem.devices.AudioVideoAlexaDevice;
import AmazonLLDs.AlexaDeviceBatterySystem.devices.AlexaDevice;
import AmazonLLDs.AlexaDeviceBatterySystem.devices.ScreenAlexaDevice;

public class Main {
    public static void main(String[] args) {
        AudioAlexaDevice d1 = new AudioAlexaDevice(new Battery());
        AlexaDevice d2 = new ScreenAlexaDevice(new NoBattery());
        AlexaDevice d3 = new AudioVideoAlexaDevice(new Battery());
        AlexaDevice d4 = new AudioAlexaDevice(new NoBattery());

        d1.getBatteryInfo().setCharging(true);
        d2.getBatteryInfo().setCharging(true);
        d3.getBatteryInfo().setCharging(false);
        d4.getBatteryInfo().setCharging(false);

        d1.getBatteryInfo().setPercentage(80);
        d3.getBatteryInfo().setPercentage(55);

        // Show output
        d1.show();  // charging, with battery
        d2.show();  // charging, no battery
        d3.show();  // non-charging, battery
        d4.show();  // non-charging, no battery
    }
}
