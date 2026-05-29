package frc.robot.subsystems.shooter;

public interface ShooterConstants {
    public static final int kTopMotorID = 0;
    public static final int kBottomMotorID = 0;

    // Current limits
    public static final int kCurrentLimit = 0;

    public static final boolean kTopMotorInverted = false; 
    public static final boolean kBottomMotorInverted = false; // switch?

    // Gear ratio
    public static final double kGearRatio = 0; //find

    public static final double kPositionFactor = 2 * Math.PI;
    public static final double kVelocityFactor = kPositionFactor / 60;
    public static final double kCruiseVelocity = 0;
    public static final double kMaxAcceleration = 0;


    // Closed-loop
    public static final double kP = 1; 
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kS = 0;
    public static final double kV = 0; 
    public static final double kA = 0;
    public static final double kCos = 0;
    public static final double kCosRatio = 0;
}