package frc.robot.subsystems.drum;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class DrumConstants {
    public static final int kLeadMotorID = 31;
    public static final int kFollowMotorID = 32;

    // Current limits
    public static final int kCurrentLimit = 20;

    public static final boolean kLeadMotorInverted = true; 
    public static final boolean kFollowMotorInverted = false; // switch?

    // Gear ratio
    public static final double kMotorToRotationsRatio = 1.0; // FIND

    public static final double kPositionFactor = 2 * Math.PI / kMotorToRotationsRatio;
    public static final double kVelocityFactor = kPositionFactor / 60;
    public static final double kMaxAcceleration = 1000000; // FIND
    public static final double kAllowedProfileError = 0.05;
    public static final IdleMode kIdleModeSparkMax = IdleMode.kBrake;


    // Closed-loop (TUNE ALL)
    public static final double kP = 1; 
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kS = 0;
    public static final double kV = 0; 
    public static final double kA = 0;
}
