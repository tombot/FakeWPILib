package com.team254.lib.util.gyro;

/**
 * Fake implementation of the gyro for the simulation shim
 * The class file of this version overwrites the class file of the real implementation when
 * copied over to the simulation binary directory.
 */
public class GyroThread {
    public void start() {}

    public boolean hasData() {
        return true;
    }

    public double getAngle() {
        return 0;
    }

    public double getRate() {
        return 0;
    }

    public void rezero() {}
    
    public void reset() {}

}
