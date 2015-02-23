/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.						*/
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.															   */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.communication.FRCNetworkCommunicationsLibrary.tResourceType;
import edu.wpi.first.wpilibj.communication.UsageReporting;

/**
 * Analog channel class.
 * <p/>
 * Each analog channel is read from hardware as a 12-bit number representing
 * 0V to 5V.
 * <p/>
 * Connected to each analog channel is an averaging and oversampling engine.
 * This engine accumulates the specified ( by setAverageBits() and
 * setOversampleBits() ) number of samples before returning a new value. This is
 * not a sliding window average. The only difference between the oversampled
 * samples and the averaged samples is that the oversampled samples are simply
 * accumulated effectively increasing the resolution, while the averaged samples
 * are divided by the number of samples to retain the resolution, but get more
 * stable values.
 */
public class AnalogInput extends SensorBase {

    private static final int kAccumulatorSlot = 1;
    private static Resource channels = new Resource(kAnalogInputChannels);
    private int m_channel;
    private static final int[] kAccumulatorChannels = {0, 1};
    private long m_accumulatorOffset;
    private double m_voltage;

    /**
     * Construct an analog channel.
     *
     * @param channel The channel number to represent. 0-3 are on-board 4-7 are on the MXP port.
     */
    public AnalogInput(final int channel) {
        m_channel = channel;
        UsageReporting.report(tResourceType.kResourceType_AnalogChannel, channel);
    }

    /**
     * Channel destructor.
     */
    public void free() {
        channels.free(m_channel);
        m_channel = 0;
        m_accumulatorOffset = 0;
    }

    /**
     * Get a sample straight from this channel. The sample is a 12-bit value
     * representing the 0V to 5V range of the A/D converter. The units are in
     * A/D converter codes. Use GetVoltage() to get the analog value in
     * calibrated units.
     *
     * @return A sample straight from this channel.
     */
    public int getValue() {
        return (int) ((getVoltage() / 5.0) * 4096.0);
    }

    /**
     * Get a sample from the output of the oversample and average engine for
     * this channel. The sample is 12-bit + the bits configured in
     * SetOversampleBits(). The value configured in setAverageBits() will cause
     * this value to be averaged 2^bits number of samples. This is not a
     * sliding window. The sample will not change until 2^(OversampleBits +
     * AverageBits) samples have been acquired from this channel. Use
     * getAverageVoltage() to get the analog value in calibrated units.
     *
     * @return A sample from the oversample and average engine for this channel.
     */
    public int getAverageValue() {
        return 0;
    }

    /**
     * Get a scaled sample straight from this channel. The value is scaled to
     * units of Volts using the calibrated scaling data from getLSBWeight() and
     * getOffset().
     *
     * @return A scaled sample straight from this channel.
     */
    public double getVoltage() {
        return m_voltage;
    }

    public void setVoltage(double voltage) {
        m_voltage = voltage;
    }

    /**
     * Get a scaled sample from the output of the oversample and average engine
     * for this channel. The value is scaled to units of Volts using the
     * calibrated scaling data from getLSBWeight() and getOffset(). Using
     * oversampling will cause this value to be higher resolution, but it will
     * update more slowly. Using averaging will cause this value to be more
     * stable, but it will update more slowly.
     *
     * @return A scaled sample from the output of the oversample and average
     * engine for this channel.
     */
    public double getAverageVoltage() {
        return 0;
    }

    /**
     * Get the factory scaling least significant bit weight constant. The least
     * significant bit weight constant for the channel that was calibrated in
     * manufacturing and stored in an eeprom.
     * <p/>
     * Volts = ((LSB_Weight * 1e-9) * raw) - (Offset * 1e-9)
     *
     * @return Least significant bit weight.
     */
    public long getLSBWeight() {
        return 0;
    }

    /**
     * Get the factory scaling offset constant. The offset constant for the
     * channel that was calibrated in manufacturing and stored in an eeprom.
     * <p/>
     * Volts = ((LSB_Weight * 1e-9) * raw) - (Offset * 1e-9)
     *
     * @return Offset constant.
     */
    public int getOffset() {
        return 0;
    }

    /**
     * Get the channel number.
     *
     * @return The channel number.
     */
    public int getChannel() {
        return m_channel;
    }

}
