package edu.wpi.first.wpilibj;

import java.util.ArrayList;

public class RelayStore {
    static ArrayList<Relay> relays = new ArrayList<Relay>();

    static {
        while (relays.size() < 10) relays.add(null);
    }

    public static Relay getRelay(int channel) {
        return relays.get(channel);
    }

    public static boolean exists(int channel) {
        return getRelay(channel) != null;
    }

    public static void initRelay(int channel, Relay relay) {
        relays.set(channel, relay);
    }

}
