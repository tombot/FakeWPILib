package edu.wpi.first.wpilibj;

public class PWMObserver {
	private int channel;
	public PWMObserver(int channel) {
		this.channel = channel;
	}
	
	public double get() {
		double ret = 0;
		
		PWM temp = PWMStore.getPWM(channel);
		
		if (temp != null) {
			ret = temp.getSpeed();
		}
		
		return ret;
		
	}
	
	public boolean exists() {
		return PWMStore.exists(channel);
	}
}
