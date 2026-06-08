package frc.robot.subsystems.kicker;

public class KickerConstants {
    public static final int kMotorID = 0;

    // Current limits
    public static final int kCurrentLimit = 20;

    public static final boolean kMotorInverted = false; //switch?

    // Gear ratio
    public static final double kMotorToRotationsRatio = 1.0; //find

    public static final double kPositionFactor = 2 * Math.PI / kMotorToRotationsRatio;
    public static final double kVelocityFactor = kPositionFactor / 60;
    public static final double kMaxAcceleration = 0;


    // Closed-loop
    public static final int kPIDSlot = 0;
    public static final double kP = 1; 
    public static final double kI = 0;
    public static final double kD = 0;
    public static final double kS = 0;
    public static final double kV = 0; 
    public static final double kA = 0;
    
}
