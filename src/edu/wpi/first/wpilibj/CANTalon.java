package edu.wpi.first.wpilibj;
import java.util.HashMap;

public class CANTalon {
	
	public static HashMap<Integer, CANTalon> store = new HashMap<Integer, CANTalon>();
	
	public enum TalonControlMode implements CANSpeedController.ControlMode {
		PercentVbus(0), Position(1), Speed(2), Current(3), Voltage(4), Follower(
				5), MotionProfile(6), Disabled(15);

		public final int value;

		public static TalonControlMode valueOf(int value) {
			for (TalonControlMode mode : values()) {
				if (mode.value == value) {
					return mode;
				}
			}

			return null;
		}

		private TalonControlMode(int value) {
			this.value = value;
		}

		@Override
		public boolean isPID() {
			return this == Current || this == Speed || this == Position;
		}

		@Override
		public int getValue() {
			return value;
		}
	}

	public CANTalon(int id) {
		store.put(id, this);
	}

	public void changeControlMode(TalonControlMode controlMode) {
		//TODO: Make this actually do something
	}
}
