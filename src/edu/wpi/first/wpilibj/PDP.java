package edu.wpi.first.wpilibj;

public class PDP {
	public static PDP m_instance = new PDP();
	
	public static PDP getInstance() {
		return m_instance;
	}
	
	double m_voltage = 12.0;
	double m_temperature = 20.0;
	double[] m_current = new double[16];
	
	public double getVoltage() {
		return m_voltage;
	}
	
	public void setVoltage(double voltage) {
		m_voltage = voltage;
	}
	
	public double getTemperature() {
		return m_temperature;
	}
	
	public void setTemperture(double temperature) {
		m_temperature = temperature;
	}
	
	public double getCurrent(int channel) {
		return m_current[channel];
	}
	
	public void setCurrent(int channel, double current) {
		m_current[channel] = current;
	}
	
	public double getTotalCurrent() {
		double total = 0.0;
		for (double channel : m_current) {
			total += channel;
		}
		return total;
	}
 }
