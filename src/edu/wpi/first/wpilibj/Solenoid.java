/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 * Solenoid class for running high voltage Digital Output.
 * <p/>
 * The Solenoid class is typically used for pneumatics solenoids, but could be used
 * for any device within the current spec of the PCM.
 */
public class Solenoid extends SolenoidBase implements LiveWindowSendable {

    private int m_channel; ///< The channel to control.
    boolean m_value = false;


    /**
     * Common function to implement constructor behavior.
     */
    private synchronized void initSolenoid(final int channel) {
        //checkSolenoidChannel(channel);
        System.out.println("trying to init solenoid " + channel);
        if (SolenoidStore.exists(channel)) {
            throw new RuntimeException("There is already a solenoid assigned on channel " + channel);
        } else {
            SolenoidStore.initSolenoid(channel, this);
        }
    }

    /**
     * Constructor using the default PCM ID (0)
     *
     * @param channel The channel on the PCM to control.
     */
    public Solenoid(final int channel) {
        m_channel = channel;
        initSolenoid(channel);
    }

    /**
     * Constructor.
     *
     * @param moduleNumber The CAN ID of the PCM the solenoid is attached to.
     * @param channel      The channel on the PCM to control (0..7).
     */
    public Solenoid(final int moduleNumber, final int channel) {
        m_channel = channel;
        initSolenoid((moduleNumber * 7) + channel);
    }

    /**
     * Destructor.
     */
    public synchronized void free() {
        //      m_allocated.free((m_moduleNumber - 1) * kSolenoidChannels + m_channel - 1);
    }

    /**
     * Set the value of a solenoid.
     *
     * @param on Turn the solenoid output off or on.
     */
    public void set(boolean on) {
        m_value = on;
    }

    /**
     * Read the current value of the solenoid.
     *
     * @return The current value of the solenoid.
     */
    public boolean get() {
        return m_value;
    }

    /**
     * Check if solenoid is blacklisted.
     * If a solenoid is shorted, it is added to the blacklist and
     * disabled until power cycle, or until faults are cleared.
     *
     * @return If solenoid is disabled due to short.
     * @see clearAllPCMStickyFaults()
     */
    public boolean isBlackListed() {
        assert (false);  // not implemented
        return false;
    }

    /*
     * Live Window code, only does anything if live window is activated.
     */
    public String getSmartDashboardType() {
        return "Solenoid";
    }

    private ITable m_table;
    private ITableListener m_table_listener;

    /**
     * {@inheritDoc}
     */
    public void initTable(ITable subtable) {
        m_table = subtable;
        updateTable();
    }

    /**
     * {@inheritDoc}
     */
    public ITable getTable() {
        return m_table;
    }

    /**
     * {@inheritDoc}
     */
    public void updateTable() {
        if (m_table != null) {
            m_table.putBoolean("Value", get());
        }
    }


    /**
     * {@inheritDoc}
     */
    public void startLiveWindowMode() {
        set(false); // Stop for safety
        m_table_listener = new ITableListener() {
            public void valueChanged(ITable itable, String key, Object value, boolean bln) {
                set(((Boolean) value).booleanValue());
            }
        };
        m_table.addTableListener("Value", m_table_listener, true);
    }

    /**
     * {@inheritDoc}
     */
    public void stopLiveWindowMode() {
        set(false); // Stop for safety
        // TODO: Broken, should only remove the listener from "Value" only.
        m_table.removeTableListener(m_table_listener);
    }
}
