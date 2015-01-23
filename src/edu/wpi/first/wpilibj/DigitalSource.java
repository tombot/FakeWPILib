package edu.wpi.first.wpilibj;

public abstract class DigitalSource {
	public abstract int getChannel();
	public abstract boolean getAnalogTriggerForRouting();
	protected int m_channel;
	
	private static DigitalSource[] sources = new DigitalSource[14];
	
	protected void initDigitalPort(int channel, boolean is_input) {
		if (sources[channel] != null) {
			throw new RuntimeException("Digital source already allocated on channel " + channel);
		} else {
			sources[channel] = this;
		}
	}

}
