package frc.robot.subsystems.slapdown;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class SlapdownConstants {
    // Motor IDs
    public static final int kMotorID = 11;

    // Current limits
    public static final int kCurrentLimit = 20;

    public static final boolean kInverted = true; 

    // Gear ratio
    public static final double kMotorToRotationsRatio = 100; 

    public static final double kPositionFactor = 2 * Math.PI / kMotorToRotationsRatio;
    public static final double kVelocityFactor = kPositionFactor / 60;
    public static final double kCruiseVelocity = 1000000; // TUNE
    public static final double kMaxAcceleration = 1000000; // TUNE


    public static final double kMaxAngleRadians = Math.PI*2;
    public static final double kMinAngleRadians = 0;
    public static final double kAngleToleranceRadians = 0.05;
    public static final double kAllowedProfileError = 0.05; 
    public static final IdleMode kIdleModeSparkMax = IdleMode.kBrake;

    // Closed-loop (TUNE ALL)
    public static final double kP = 0.4; 
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kS = 0;
    public static final double kV = 0; 
    public static final double kA = 0;
    public static final double kCos = 0;
    public static final double kCosRatio = 0;
    
}
