package frc.robot.subsystems.belts;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

public class BeltConstants {
    public static final int kMotorID = 0; // ADD

    // Current limits
    public static final int kCurrentLimit = 40;

    public static final boolean kMotorInverted = false;

    // Gear ratio
    public static final double kMotorToRotationsRatio = 1; // FIND

    public static final double kPositionFactor = 2 * Math.PI / kMotorToRotationsRatio;
    public static final double kVelocityFactor = kPositionFactor / 60;
    public static final double kCruiseVelocity = 1000000; // FIND
    public static final double kMaxAcceleration = 1000000; // FIND
    public static final IdleMode kIdleModeSparkMax = IdleMode.kBrake;

    
}
