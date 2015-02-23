package edu.wpi.first.wpilibj;

import java.util.ArrayList;

public class SolenoidStore {
    static ArrayList<Solenoid> solenoids = new ArrayList<Solenoid>();

    static {
        while (solenoids.size() < 16) solenoids.add(null);
    }

    public static Solenoid getSolenoid(int channel) {
        return solenoids.get(channel);
    }

    public static boolean exists(int channel) {
        return getSolenoid(channel) != null;
    }

    public static void initSolenoid(int channel, Solenoid solenoid) {
        solenoids.set(channel, solenoid);
    }

}
