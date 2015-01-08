/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.				   */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.															   */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Manifest;


/**
 * Implement a Robot Program framework.
 * The RobotBase class is intended to be subclassed by a user creating a robot program.
 * Overridden autonomous() and operatorControl() methods are called at the appropriate time
 * as the match proceeds. In the current implementation, the Autonomous code will run to
 * completion before the OperatorControl code could start. In the future the Autonomous code
 * might be spawned as a task, then killed at the end of the Autonomous period.
 */
public abstract class RobotBase {
	/**
	 * The VxWorks priority that robot code should work at (so Java code should run at)
	 */
	public static final int ROBOT_TASK_PRIORITY = 101;

	protected final DriverStation m_ds;


	/**
	 * Constructor for a generic robot program.
	 * User code should be placed in the constructor that runs before the Autonomous or Operator
	 * Control period starts. The constructor will run to completion before Autonomous is entered.
	 *
	 * This must be used to ensure that the communications code starts. In the future it would be
	 * nice to put this code into it's own task that loads on boot so ensure that it runs.
	 */
	protected RobotBase() {
		m_ds = DriverStation.getInstance();		
	}

	/**
	 * Free the resources for a RobotBase class.
	 */
	public void free() {
	}

	/**
	 * @return If the robot is running in simulation.
	 */
	public static boolean isSimulation() {
		return false;
	}

	/**
	 * @return If the robot is running in the real world.
	 */
	public static boolean isReal() {
		return true;
	}

	/**
	 * Determine if the Robot is currently disabled.
	 * @return True if the Robot is currently disabled by the field controls.
	 */
	public boolean isDisabled() {
		return true;
	}

	/**
	 * Determine if the Robot is currently enabled.
	 * @return True if the Robot is currently enabled by the field controls.
	 */
	public boolean isEnabled() {
		return false;
	}

	/**
	 * Determine if the robot is currently in Autonomous mode.
	 * @return True if the robot is currently operating Autonomously as determined by the field controls.
	 */
	public boolean isAutonomous() {
		return false;
	}

	/**
	 * Determine if the robot is currently in Test mode
	 * @return True if the robot is currently operating in Test mode as determined by the driver station.
	 */
	public boolean isTest() {
		return false;
	}

	/**
	 * Determine if the robot is currently in Operator Control mode.
	 * @return True if the robot is currently operating in Tele-Op mode as determined by the field controls.
	 */
	public boolean isOperatorControl() {
		return false;
	}

	/**
	 * Indicates if new data is available from the driver station.
	 * @return Has new data arrived over the network since the last time this function was called?
	 */
	public boolean isNewDataAvailable() {
		return false;
	}

	/**
	 * Provide an alternate "main loop" via startCompetition().
	 */
	public abstract void startCompetition();

	/**
	 * This hook is called right before startCompetition(). By default, tell the
	 * DS that the robot is now ready to be enabled. If you don't want for the
	 * robot to be enabled yet, you can override this method to do nothing.
	 * If you do so, you will need to call 
	 * FRCNetworkCommunicationsLibrary.FRCNetworkCommunicationOvserveUserProgramStarting() from
	 * your code when you are ready for the robot to be enabled.
	 */
	protected void prestart() {
	}

	public static boolean getBooleanProperty(String name, boolean defaultValue) {
		String propVal = System.getProperty(name);
		if (propVal == null) {
			return defaultValue;
		}
		if (propVal.equalsIgnoreCase("false")) {
			return false;
		} else if (propVal.equalsIgnoreCase("true")) {
			return true;
		} else {
			throw new IllegalStateException(propVal);
		}
	}

	/**
	 * Common initialization for all robot programs.
	 */
	public static void initializeHardwareConfiguration(){
	
	}

	/**
	 * Starting point for the applications.
	 */
	public static void main(String args[]) {
		initializeHardwareConfiguration();

		String robotName = "";
		Enumeration<URL> resources = null;
		try {
			resources = RobotBase.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
		} catch (IOException e) {e.printStackTrace();}
		while (resources != null && resources.hasMoreElements()) {
			try {
				Manifest manifest = new Manifest(resources.nextElement().openStream());
				robotName = manifest.getMainAttributes().getValue("Robot-Class");
			} catch (IOException e) {e.printStackTrace();}
		}

		RobotBase robot;
		System.out.println("Robot name: " + robotName);
		try {
			robot = (RobotBase) Class.forName(robotName).newInstance();
			robot.prestart();
		} catch (Throwable t) {
			t.printStackTrace();
			System.err.println("WARNING: Robots don't quit!");
			System.err.println("ERROR: Could not instantiate robot " + robotName + "!");
			System.exit(1);
			return;
		}
		
		System.out.println(robot);

		boolean errorOnExit = false;
		try {
			robot.startCompetition();
		} catch (Throwable t) {
			System.out.println("oops");
			t.printStackTrace();
			errorOnExit = true;
		} finally {
			// startCompetition never returns unless exception occurs....
			System.err.println("WARNING: Robots don't quit!");
			if (errorOnExit) {
				System.err.println("---> The startCompetition() method (or methods called by it) should have handled the exception above.");
			} else {
				System.err.println("---> Unexpected return from startCompetition() method.");
			}
		}
		System.exit(1);
	}
}
