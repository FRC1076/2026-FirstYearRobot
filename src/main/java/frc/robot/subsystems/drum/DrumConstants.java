package frc.robot.subsystems.drum;

public class DrumConstants {
    public static final int kMotorID = 0; // ADD

    // Current limits
    public static final int kCurrentLimit = 20;

    public static final boolean kMotorInverted = false; // SWITCH?

    // Gear ratio
    public static final double kMotorToRotationsRatio = 1.0; // FIND

    public static final double kPositionFactor = 2 * Math.PI / kMotorToRotationsRatio;
    public static final double kVelocityFactor = kPositionFactor / 60;
    public static final double kMaxAcceleration = 0; // FIND


    // Closed-loop (TUNE ALL)
    public static final double kP = 1; 
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kS = 0;
    public static final double kV = 0; 
    public static final double kA = 0;
}
