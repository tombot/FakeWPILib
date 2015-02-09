package edu.wpi.first.wpilibj;

public class DigitalInputSetter {
	private int channel;
	public DigitalInputSetter(int channel) {
		this.channel = channel;
	}
	
	public void set(boolean v) {		
		DigitalInput temp = (DigitalInput) DigitalSource.sources[channel];
		if (temp != null) {
			temp.set(v);
		}
	}

	public DigitalInput getDigitalInput() {
		return (DigitalInput) DigitalSource.sources[channel];
	}
	
	public boolean exists() {
		return DigitalSource.sources[channel] != null;
	}
}
