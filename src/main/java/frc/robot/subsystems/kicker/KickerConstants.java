package frc.robot.subsystems.kicker;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class KickerConstants {
    public static final int kMotorID = 1; // ADD

    // Current limits
    public static final int kCurrentLimit = 40;

    public static final boolean kMotorInverted = false; // SWITCH?

    // Gear ratio
    public static final double kMotorToRotationsRatio = 1.0; // FIND

    // public static final double kPositionFactor = 2 * Math.PI / kMotorToRotationsRatio;
    // public static final double kVelocityFactor = kPositionFactor / 60;
    public static final double kMaxAcceleration = 1000000; // FIND
    public static final double kAllowedProfileError = 0.05;
    public static final NeutralMode kNeutralModeTalonSRX = NeutralMode.Brake;
    public static final IdleMode kIdleModeSparkMax = IdleMode.kBrake;


    // Closed-loop (TUNE ALL)
    // public static final int kPIDSlot = 0;
    // public static final double kP = 1; 
    // public static final double kI = 0;
    // public static final double kD = 0;
    // public static final double kS = 0;
    // public static final double kV = 0; 
    // public static final double kA = 0;
    
}
