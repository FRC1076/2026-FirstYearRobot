package frc.robot.subsystems;


import edu.wpi.first.units.measure.Voltage;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.belts.BeltSubsystem;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.drum.DrumSubsystem;
import frc.robot.subsystems.kicker.KickerSubsystem;
import frc.robot.subsystems.roller.RollerSubsystem;
import frc.robot.subsystems.slapdown.SlapdownSubsystem;

public class Superstructure {

    private final DriveSubsystem m_drive;
    private final DrumSubsystem m_drum;
    private final KickerSubsystem m_kicker;
    private final RollerSubsystem m_roller;
    private final SlapdownSubsystem m_slapdown;
    // private final BeltSubsystem m_belts;
    // private final ClimberSubsystem m_climber;

    private double kickerVoltage = 0.0;
    private double targetDrumVelocity = 0.0;

    public Superstructure(DriveSubsystem drive, DrumSubsystem drum, KickerSubsystem kicker, RollerSubsystem roller, SlapdownSubsystem slapdown) {
        this.m_drive = drive;
        this.m_drum = drum;
        this.m_kicker = kicker;
        this.m_roller = roller;
        this.m_slapdown = slapdown;
    }
    
    public Command intake(double volts) {
        if (volts == 0) {
            return m_roller.stop();
        }
        else {
            return m_roller.setVoltage(volts);
        }
    }

    public Command stopIntake () {
        return m_roller.stop();
    }

    public Command slapdownDown() {
        return m_slapdown.runVoltage(() -> SuperstructureConstants.kSlapdownDownVoltage);
    }

    public Command slapdownUp() {
        return m_slapdown.runVoltage(() -> SuperstructureConstants.kSlapdownUpVoltage);
    }

    public Command intakeForAuto(double volts) {
        return Commands.parallel(
            m_roller.runVoltage(volts),
            m_slapdown.applyPosition(SuperstructureConstants.kSlapdownDownSlappingAngleRadians))
        .withTimeout(SuperstructureConstants.kIntakeForAutoSeconds)
        .andThen(m_roller.stop());
    }

    public Command intakeBackwards() {
        return m_roller.runVoltage(SuperstructureConstants.kBackwardsOperatorRollerVoltage);
    }

    public Command shootPreset(int positionNumber) {
        if (positionNumber == 1) {
            return Commands.runOnce(() -> {
                kickerVoltage = SuperstructureConstants.kKickerVoltage;
                targetDrumVelocity = SuperstructureConstants.kInFrontOfClimberDrumVelocity;
            })
            .andThen(m_drum.setVelocity(SuperstructureConstants.kInFrontOfClimberDrumVelocity));
        }

        else if (positionNumber == 2) {
            return Commands.runOnce(() -> { 
                kickerVoltage = SuperstructureConstants.kKickerVoltage;
                targetDrumVelocity = SuperstructureConstants.kNeutralZonePassingDrumVelocity;
            })
            .andThen(m_drum.setVelocity(SuperstructureConstants.kNeutralZonePassingDrumVelocity));
        }

        else if (positionNumber == 3) {
            return Commands.runOnce(() -> { 
                kickerVoltage = SuperstructureConstants.kKickerVoltage;
                targetDrumVelocity = SuperstructureConstants.kOpposingZonePassingDrumVelocity;
            })
            .andThen(m_drum.setVelocity(SuperstructureConstants.kOpposingZonePassingDrumVelocity));
        }

        else {
            return Commands.parallel(
                m_kicker.stop(),
                m_drum.stop()
            );
        }
    }

    public Command shoot() {
        return Commands.waitUntil(() -> m_drum.atVelocity(targetDrumVelocity))
        .andThen(m_kicker.runVoltage(() -> kickerVoltage));
    }

    public Command shootBackwards() {
        return Commands.parallel(
            m_drum.setVelocity(SuperstructureConstants.kBackwardsOperatorDrumVelocity),
            m_kicker.runVoltage(() -> SuperstructureConstants.kBackwardsOperatorKickerVoltage)
        );
    }

    public Command stopShooting() {
        return Commands.parallel(
            m_drum.stop(),
            m_kicker.stop(),
            Commands.runOnce (() -> {
                kickerVoltage = 0.0;
                targetDrumVelocity = 0.0;
            })
        );
    }

    public Command shootForAuto () {
        return Commands.deadline( 
            Commands.sequence(
                Commands.waitSeconds(SuperstructureConstants.kSpinUpSecondsForAuto),
                m_kicker.runVoltage(() -> kickerVoltage).withTimeout(SuperstructureConstants.kShootSecondsForAuto)
            ),
            shootPreset(SuperstructureConstants.kPositionNumberForAuto)
            )
            .andThen(m_kicker.stop());
    }

    public Command agitateHopper() {
        return Commands.repeatingSequence (
            m_slapdown.applyPosition(SuperstructureConstants.kSlapdownUpSlappingAngleRadians),
            Commands.waitSeconds(1.5),
            m_slapdown.applyPosition(SuperstructureConstants.kSlapdownDownSlappingAngleRadians),
            Commands.waitSeconds(1.5)
        );
    }

}
