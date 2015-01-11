package edu.wpi.first.wpilibj;

public class EncoderSetter {
	private int channel;
	public EncoderSetter(int a, int b) {
		this.channel = a;
	}
	
	public void set(int raw) {		
		Encoder temp = EncoderStore.getEncoder(channel, 0);
		if (temp != null) {
			temp.setRaw(raw);
		}
	}

	public Encoder getEncoder() {
		return EncoderStore.getEncoder(channel, 0);
	}
	
	public boolean exists() {
		return EncoderStore.exists(channel, 0);
	}
}
