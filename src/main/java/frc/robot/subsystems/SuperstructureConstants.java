package frc.robot.subsystems;

public class SuperstructureConstants {

    public static final double kSlapdownDownSlappingAngleRadians = 3 * Math.PI / 2; // needs tuning
    public static final double kSlapdownUpSlappingAngleRadians = 0.0;

    // Intake roller voltages for intaking (TO BE TUNED)

    public static final double kIntakeRollerVoltage1 = 8.0;
    public static final double kIntakeRollerVoltage2 = 10.0;
    public static final double kIntakeRollerVoltage3 = 12.0;

    // Drum velocities for shooting (TO BE TUNED)

    // Climber setpoint Drum velocity (tuning set one)
    public static final double kInFrontOfClimberDrumVelocity1 = 300;
    public static final double kInFrontOfClimberDrumVelocity2 = 325;
    public static final double kInFrontOfClimberDrumVelocity3 = 350;
    public static final double kInFrontOfClimberDrumVelocity4 = 375;

    public static final double kInFrontOfClimberKickerVoltage = 6.0;

    // Climber setpoint kicker voltage (tuning set two)
    // public static final double kInFrontOfClimberDrumVelocity = 

    public static final double kInFrontOfClimberKickerVoltage1 = 4.0;
    public static final double kInFrontOfClimberKickerVoltage2 = 8.0;
    public static final double kInFrontOfClimberKickerVoltage3 = 10.0;

    // Passing Drum velocity (tuning set three)
    public static final double kPassingDrumVelocity1 = 500;
    public static final double kPassingDrumVelocity2 = 600;
    public static final double kPassingDrumVelocity3 = 700;

    public static final double kPassingKickerVoltage = 6.0;

    // Passing Kicker voltage (tuning set four)
    // public static final double kPassingDrumVelocity = 

    public static final double kPassingKickerVoltage1 = 8.0;
    public static final double kPassingKickerVoltage2 = 10.0;
    public static final double kPassingKickerVoltage3 = 12.0;

    // Autonomous constants
    public static final double kIntakeForAutoSeconds = 3.0;

    public static final int kPositionNumberForAuto = 2; // climber
    public static final double kSpinUpSecondsForAuto = 2.0;
    public static final double kShootSecondsForAuto = 7.0;

}
