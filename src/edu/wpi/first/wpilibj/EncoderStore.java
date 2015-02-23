package edu.wpi.first.wpilibj;

import java.util.ArrayList;

public class EncoderStore {
    static ArrayList<Encoder> encoders = new ArrayList<Encoder>();

    static {
        while (encoders.size() < 20) encoders.add(null);
    }

    public static Encoder getEncoder(int a, int b) {
        return encoders.get(a);
    }

    public static boolean exists(int a, int b) {
        return getEncoder(a, b) != null;
    }

    public static void initEncoder(int a, int b, Encoder encoder) {
        encoders.set(a, encoder);
    }

}
