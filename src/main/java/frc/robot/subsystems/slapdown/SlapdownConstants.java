package frc.robot.subsystems.slapdown;

public class SlapdownConstants {
    // Motor IDs
    public static final int kMotorID = 0;

    // Current limits
    public static final int kCurrentLimit = 0;

    public static final boolean kInverted = false; 

    // Gear ratio
    public static final double kMotorToRotationsRatio = 100; // motor rotations to output rotations

    public static final double kPositionFactor = 2 * Math.PI;
    public static final double kVelocityFactor = kPositionFactor / 60;
    public static final double kCruiseVelocity = 0;
    public static final double kMaxAcceleration = 0;


    public static final double kMaxAngleRadians = 0; // confirm
    public static final double kMinAngleRadians = 0; // find
    public static final double kAngleToleranceRadians = 0; // ee

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
