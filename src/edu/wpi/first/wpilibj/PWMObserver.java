package edu.wpi.first.wpilibj;

public class PWMObserver {
    private int channel;
    private boolean inverted = false;

    public PWMObserver(int channel) {
        this.channel = channel;
    }

    public double get() {
        double ret = 0;

        if (!DriverStation.getInstance().isEnabled()) {
            return 0;
        }

        PWM temp = PWMStore.getPWM(channel);

        if (temp != null) {
            ret = temp.getSpeed() * (inverted ? -1.0 : 1.0);
        }

        return ret;

    }

    public void setInvertedMotor(boolean inverted) {
        this.inverted = inverted;
    }

    public boolean exists() {
        return PWMStore.exists(channel);
    }
}
