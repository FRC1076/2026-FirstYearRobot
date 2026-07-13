package frc.robot.subsystems.drive;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.util.Units;

public class DriveConstants {
    public static final double maxTranslationSpeedMPS = Units.feetToMeters(10);
    public static final double maxRotationSpeedRadPerSec = 4; // originally set to 2 // Maximum acceptable value appears to be 12

    public static final boolean useSpeedScaling = true;

    public static final double singleClutchTranslationFactor = 0.6;
    public static final double singleClutchRotationFactor = 0.6;
    public static final double doubleClutchTranslationFactor = 0.35;
    public static final double doubleClutchRotationFactor = 0.35;

    public static final int odometryFrequencyHz = 50;
    public static final double wheelBase = Units.inchesToMeters(27.5);
    public static final double trackWidth = Units.inchesToMeters(27.5);
    //public static final double wheelRadius = 0.0508; //Meters

    public static final Translation2d[] moduleTranslations = new Translation2d[] {
        new Translation2d(trackWidth / 2.0, wheelBase / 2.0),
        new Translation2d(trackWidth / 2.0, -wheelBase / 2.0),
        new Translation2d(-trackWidth / 2.0, wheelBase / 2.0),
        new Translation2d(-trackWidth / 2.0, -wheelBase / 2.0)
    };

    // public static final InterpolatingDoubleTreeMap elevatorAccelerationTable = new InterpolatingDoubleTreeMap(); // A table that maps elevator heights to slew rate limits
    // static {
    //     elevatorAccelerationTable.put(0.0,100000.0);
    //     elevatorAccelerationTable.put(1.0,100000.0); // Deadzone with no acceleration limiting between 0.0 and 1.348 (THE END OF THIS DEADZONE *MUST* BE SLIGHTLY LOWER THAN THE POINT WHERE WE ACTUALLY WANT ELEVATOR ACCELERATION LIMITING TO BEGIN)
    //     // elevatorAccelerationTable.put(0.0, 12.66793578);
    //     // elevatorAccelerationTable.put(0.253, 100000.0);
    //     // elevatorAccelerationTable.put(0.254, 10.15773958 / 5);
    //     // elevatorAccelerationTable.put(0.508, 8.477828029 / 5);
    //     // elevatorAccelerationTable.put(0.762, 7.274717623 / 5);
    //     elevatorAccelerationTable.put(1.016, 6.370643237 / 5);
    //     elevatorAccelerationTable.put(1.27, 5.666439564 / 6);
    //     elevatorAccelerationTable.put(1.524, 5.102204373 / 7);
    //     elevatorAccelerationTable.put(1.778, 4.640342002 / 8);
    //     elevatorAccelerationTable.put(1.8288, 4.557930098 / 8);
    // }

    public static class GyroConstants {
        public static final int kGyroPort = 9; // ONLY used if Gyro is a Pigeon
        public static final double kGyroZero = 0; // Angle to zero the gyro at in degrees
        public static final double kGyroMountYawOffset = 0; // Angle to configure the offset of the gyro yaw to in degrees (mountYaw)
    }

    public static class ModuleConstants {
        public static class Common {
            public static class Drive {
                public static final int CurrentLimit = 60;
                public static final double gearRatio = 6.75;
                public static final double VoltageCompensation = 12;
                public static final double MaxModuleSpeed = Units.feetToMeters(15.1); // Maximum attainable module speed, from the SDS website
                public static final double WheelDiameter = Units.inchesToMeters(4); // Standard SDS wheel
                public static final double WheelCOF = 1.0; // Coefficient of friction
                public static final double PositionConversionFactor = WheelDiameter * Math.PI / gearRatio; // Converts from rotations to meters, calculates to be 0.04729
                public static final double VelocityConversionFactor = PositionConversionFactor / 60; // Converts from RPM to meters per second, calculates to be 0.0007881

                // PID constants
                public static final double kP = 0.035;
                public static final double kI = 0.000;
                public static final double kD = 0.0012;

                // Feedforward constants
                public static final double kV = 2.78;
                public static final double kS = 0.0;
                public static final double kA = 0.0;
            }

            public static class Turn {
                public static final int CurrentLimit = 60;
                public static final double VoltageCompensation = 12;
                public static final double gearRatio = 12.8;
                // TODO: check that radians for conversion factors don't break anything
                public static final double RelativePositionConversionFactor =  (1 / gearRatio) * 2 * Math.PI; // Converts from rotations to radians, calculates out to be 0.4909
                public static final double AbsolutePositionConversionFactor = 2*Math.PI;
                public static final double VelocityConversionFactor = RelativePositionConversionFactor / 60; // Converts from RPM to radians/second

                // PID constants
                public static final double kP = 3;
                public static final double kI = 0.0;
                public static final double kD = 0.05;

                // Feedforward constant
                public static final double kS = 0.012009; // May be better just to leave this as zero
            }
        }
        public static enum ModuleConfig {
            FrontLeft(3,4,21, -0.026123046875),
            FrontRight(1,2,22,-0.426513671875),
            RearRight(5,6,23,0.0712890625),
            RearLeft(7,8,24,0.0927734375);

            public final int DrivePort;
            public final int TurnPort;
            public final int EncoderPort;
            public final double EncoderOffsetRots;

            private ModuleConfig(int DrivePort, int TurnPort,int EncoderPort,double EncoderOffsetRots) {
                this.DrivePort = DrivePort;
                this.TurnPort = TurnPort;
                this.EncoderPort = EncoderPort;
                this.EncoderOffsetRots = EncoderOffsetRots;
            }
        }
    }

    public static class SimConstants {
        public static double kDriveKP = 0.005;
        public static double kDriveKD = 0;

        public static double kTurnKP = 8;
        public static double kTurnKD = 0;
    }
}
