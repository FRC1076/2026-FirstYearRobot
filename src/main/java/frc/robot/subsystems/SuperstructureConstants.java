package frc.robot.subsystems;

import frc.robot.subsystems.slapdown.SlapdownConstants;

public class SuperstructureConstants {

    public static final double kSlapdownDownSlappingAngleRadians = Math.PI / 2;
    public static final double kSlapdownUpSlappingAngleRadians = 0.0;

    // Drum velocities for shooting (untuned)
    public static final double kInFrontOfBumpDrumVelocity = 200;
    public static final double kInFrontOfBumpKickerVoltage = 12.0; //old: 9

    public static final double kInFrontOfClimberDrumVelocity = 250;
    public static final double kInFrontOfClimberKickerVoltage = 12.0; //old 10

    public static final double kCornerDrumVelocity = 300;
    public static final double kCornerKickerVoltage = 12.0;// old 11

    public static final double kPassingDrumVelocity = 350;
    public static final double kPassingKickerVoltage = 12.0;

}
