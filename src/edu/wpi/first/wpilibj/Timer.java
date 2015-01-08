package edu.wpi.first.wpilibj;

import java.util.Calendar;


public class Timer {

	static long offset = 0;
	
	static {
		offset = System.currentTimeMillis();
	}
	public static void SetImplementation(StaticInterface ti) {
	}

	/**
	 * Return the system clock time in seconds. Return the time from the
	 * FPGA hardware clock in seconds since the FPGA started.
	 *
	 * @return Robot running time in seconds.
	 */
	public static double getFPGATimestamp() {
		return (System.currentTimeMillis() - offset) / 1000.0;
	}

	/**
	 * Return the approximate match time
	 * The FMS does not currently send the official match time to the robots
	 * This returns the time since the enable signal sent from the Driver Station
	 * At the beginning of autonomous, the time is reset to 0.0 seconds
	 * At the beginning of teleop, the time is reset to +15.0 seconds
	 * If the robot is disabled, this returns 0.0 seconds
	 * Warning: This is not an official time (so it cannot be used to argue with referees)
	 * @return Match time in seconds since the beginning of autonomous
	 */
	public static double getMatchTime() {
		return 0;
	}

	/**
	 * Pause the thread for a specified time. Pause the execution of the
	 * thread for a specified period of time given in seconds. Motors will
	 * continue to run at their last assigned values, and sensors will continue
	 * to update. Only the task containing the wait will pause until the wait
	 * time is expired.
	 *
	 * @param seconds Length of time to pause
	 */
	public static void delay(final double seconds) {
		try {
			Thread.sleep((int)seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public interface StaticInterface {
		double getFPGATimestamp();
		double getMatchTime();
		void delay(final double seconds);
		Interface newTimer();
	}
	
	
	long lastViewed = 0;
	long elapsed = 0;
	boolean running = false;
	
	public Timer() {
		
	}

	/**
	 * Get the current time from the timer. If the clock is running it is derived from
	 * the current system clock the start time stored in the timer class. If the clock
	 * is not running, then return the time when it was last stopped.
	 *
	 * @return Current time value for this timer in seconds
	 */
	public double get() {
		elapsed = elapsed + (running ? System.currentTimeMillis() - lastViewed : 0);
		return (double) elapsed / 1000.0;
	}

	/**
	 * Reset the timer by setting the time to 0.
	 * Make the timer startTime the current time so new requests will be relative now
	 */
	public void reset() {
		elapsed = 0;
		lastViewed = System.currentTimeMillis();
	}

	/**
	 * Start the timer running.
	 * Just set the running flag to true indicating that all time requests should be
	 * relative to the system clock.
	 */
	public void start() {
		running = true;
	}

	/**
	 * Stop the timer.
	 * This computes the time as of now and clears the running flag, causing all
	 * subsequent time requests to be read from the accumulated time rather than
	 * looking at the system clock.
	 */
	public void stop() {
		elapsed = elapsed + (running ? System.currentTimeMillis() - lastViewed : 0); 
		running = false;
	}

	/**
	 * Check if the period specified has passed and if it has, advance the start
	 * time by that period. This is useful to decide if it's time to do periodic
	 * work without drifting later by the time it took to get around to checking.
	 *
	 * @param period The period to check for (in seconds).
	 * @return If the period has passed.
	 */
	public boolean hasPeriodPassed(double period)
	{
		return false;
	}

	public interface Interface {
		/**
		 * Get the current time from the timer. If the clock is running it is derived from
		 * the current system clock the start time stored in the timer class. If the clock
		 * is not running, then return the time when it was last stopped.
		 *
		 * @return Current time value for this timer in seconds
		 */
		public double get();

		/**
		 * Reset the timer by setting the time to 0.
		 * Make the timer startTime the current time so new requests will be relative now
		 */
		public void reset();

		/**
		 * Start the timer running.
		 * Just set the running flag to true indicating that all time requests should be
		 * relative to the system clock.
		 */
		public void start();

		/**
		 * Stop the timer.
		 * This computes the time as of now and clears the running flag, causing all
		 * subsequent time requests to be read from the accumulated time rather than
		 * looking at the system clock.
		 */
		public void stop();


		/**
		 * Check if the period specified has passed and if it has, advance the start
		 * time by that period. This is useful to decide if it's time to do periodic
		 * work without drifting later by the time it took to get around to checking.
		 *
		 * @param period The period to check for (in seconds).
		 * @return If the period has passed.
		 */
		public boolean hasPeriodPassed(double period);
	}
}
