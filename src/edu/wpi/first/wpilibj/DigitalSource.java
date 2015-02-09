package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.util.AllocationException;

public abstract class DigitalSource {
	public abstract int getChannel();
	public abstract boolean getAnalogTriggerForRouting();
	protected int m_channel;
	protected boolean m_value = false;
	InterruptHandlerFunction<?> m_isr;
	private boolean m_rising_edge = true;
	private boolean m_falling_edge = false;

	public boolean get() {
		return m_value;
	}
	
	public void set(boolean v) {
			if (m_isr != null &&
				((v && !m_value) && m_rising_edge) ||
				((!v && m_value) && m_falling_edge)) {
				m_isr.function.apply(0, m_isr.overridableParamater());
			}
		m_value = v;
	}
	
	protected static DigitalSource[] sources = new DigitalSource[14];
	
	private static DigitalSource getSimSource(int channel) {
		return sources[channel];
	}
	
	protected void initDigitalPort(int channel, boolean is_input) {
		if (sources[channel] != null) {
			throw new RuntimeException("Digital source already allocated on channel " + channel);
		} else {
			sources[channel] = this;
		}
	}

	
	/**
	 * Request one of the 8 interrupts asynchronously on this digital input.
	 *
	 * @param handler
	 *            The {@link InterruptHandlerFunction} that contains the method
	 *            {@link InterruptHandlerFunction#interruptFired(int, Object)} that
	 *            will be called whenever there is an interrupt on this device.
	 *            Request interrupts in synchronous mode where the user program
	 *            interrupt handler will be called when an interrupt occurs. The
	 *            default is interrupt on rising edges only.
	 */
	public void requestInterrupts(InterruptHandlerFunction<?> handler) {
		if(m_isr != null){
			throw new AllocationException("The interrupt has already been allocated");
		}

		m_isr = handler;
	}

	
	public void setUpSourceEdge(boolean risingEdge, boolean fallingEdge) {
		if (m_isr != null) {
			m_rising_edge = risingEdge;
			m_falling_edge = fallingEdge;
		} else {
			throw new IllegalArgumentException(
					"You must call RequestInterrupts before setUpSourceEdge");
		}
	}

}
