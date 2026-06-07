package frc.robot.subsystems.belts;

public class BeltConstants {
    public static final int kMotorID = 0;

    // Current limits
    public static final int kCurrentLimit = 20;

    public static final boolean kMotorInverted = false;

    // Gear ratio
    public static final double kGearRatio = 0; //find

    public static final double kPositionFactor = 2 * Math.PI;
    public static final double kVelocityFactor = kPositionFactor / 60;
    public static final double kCruiseVelocity = 0;
    public static final double kMaxAcceleration = 0;
    
}
