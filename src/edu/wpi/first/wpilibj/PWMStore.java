package edu.wpi.first.wpilibj;

import java.util.ArrayList;

public class PWMStore {
    static ArrayList<PWM> pwms = new ArrayList<PWM>();

    static {
        while (pwms.size() < 10) pwms.add(null);
    }

    public static PWM getPWM(int channel) {
        return pwms.get(channel);
    }

    public static boolean exists(int channel) {
        return getPWM(channel) != null;
    }

    public static void initPWM(int channel, PWM pwm) {
        pwms.set(channel, pwm);
    }

}
