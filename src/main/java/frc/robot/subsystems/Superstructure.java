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

    private double kickerVoltage = 10.0;

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
            return Commands.parallel(
            m_roller.runVoltage(volts),
            m_slapdown.applyPosition(SuperstructureConstants.kSlapdownDownSlappingAngleRadians)
            );
        }
    }

    public Command intakeUp() {
        return m_slapdown.applyPosition(SuperstructureConstants.kSlapdownUpSlappingAngleRadians);
    }

    public Command shootPreset(int positionNumber) {
        if (positionNumber == 1) {
            return Commands.runOnce(() -> kickerVoltage = SuperstructureConstants.kInFrontOfBumpKickerVoltage).andThen(m_drum.setVelocity(SuperstructureConstants.kInFrontOfBumpDrumVelocity));
        }

        else if (positionNumber == 2) {
            return Commands.runOnce(() -> kickerVoltage = SuperstructureConstants.kInFrontOfClimberKickerVoltage).andThen(m_drum.setVelocity(SuperstructureConstants.kInFrontOfClimberDrumVelocity));
        }

        else if (positionNumber == 3) {
            return Commands.runOnce(() -> kickerVoltage = SuperstructureConstants.kCornerKickerVoltage).andThen(m_drum.setVelocity(SuperstructureConstants.kCornerDrumVelocity));
        }

        else if (positionNumber == 4) {
            return Commands.runOnce(() -> kickerVoltage = SuperstructureConstants.kPassingKickerVoltage).andThen(m_drum.setVelocity(SuperstructureConstants.kPassingDrumVelocity));
        }

        else {
            return Commands.parallel(
                m_kicker.stop(),
                m_drum.stop()
            );
        }
    }

    public Command shoot() {
        return m_kicker.runVoltage(() -> kickerVoltage);
    }

    public Command agitateHopper() {
        return Commands.sequence (
            m_slapdown.applyPosition(SuperstructureConstants.kSlapdownUpSlappingAngleRadians),
            Commands.waitSeconds(1.5),
            m_slapdown.applyPosition(SuperstructureConstants.kSlapdownDownSlappingAngleRadians),
            Commands.waitSeconds(1.5),
            m_slapdown.applyPosition(SuperstructureConstants.kSlapdownUpSlappingAngleRadians),
            Commands.waitSeconds(1.5)
        );
    }

}
