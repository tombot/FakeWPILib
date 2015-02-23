package com.team254.fakewpilib;

public abstract class SimRobotBase {
    public abstract void initSimRobot();

    public abstract void startRobotSim();

    public void prestart() {
        initSimRobot();
    }

    ;
}
