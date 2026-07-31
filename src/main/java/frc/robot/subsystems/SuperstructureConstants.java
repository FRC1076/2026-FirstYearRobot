package frc.robot.subsystems;

public class SuperstructureConstants {

    public static final double kSlapdownDownSlappingAngleRadians = 3 * Math.PI / 2; // needs tuning
    public static final double kSlapdownUpSlappingAngleRadians = 0.0;

    // Intake roller voltages for intaking (TO BE TUNED)

    public static final double kIntakeRollerVoltage1 = 8.0;
    public static final double kIntakeRollerVoltage2 = 10.0;
    public static final double kIntakeRollerVoltage3 = 12.0;

    // Setpoint Drum velocities
    public static final double kInFrontOfClimberDrumVelocity = 320;
    public static final double kNeutralZonePassingDrumVelocity = 400;
    public static final double kOpposingZonePassingDrumVelocity = 700;
    
    public static final double kKickerVoltage = 12.0;

    // Autonomous constants
    public static final double kIntakeForAutoSeconds = 3.0;

    public static final int kPositionNumberForAuto = 2; // climber
    public static final double kSpinUpSecondsForAuto = 2.0;
    public static final double kShootSecondsForAuto = 7.0;

    // Operator subsystem reverse constants

    public static final double kBackwardsOperatorDrumVelocity = -150;
    public static final double kBackwardsOperatorKickerVoltage = -6.0;
    
    public static final double kBackwardsOperatorRollerVoltage = -8.0;

}
