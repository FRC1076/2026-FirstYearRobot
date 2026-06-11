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

    public Superstructure(DriveSubsystem drive, DrumSubsystem drum, KickerSubsystem kicker, RollerSubsystem roller, SlapdownSubsystem slapdown) {
        this.m_drive = drive;
        this.m_drum = drum;
        this.m_kicker = kicker;
        this.m_roller = roller;
        this.m_slapdown = slapdown;
    }
    
    public Command intake(double volts) {
        return m_roller.runVoltage(volts);

    }

    public Command slapdown() {
        return m_slapdown.applyPosition(SuperstructureConstants.kSlapdownSlappingAngleRadians);
    }

    // public Command shoot(int positionNumber) {
    //     if (positionNumber == 1) {
    //         return Commands.parallel(
    //             m_kicker.setVoltage(SuperstructureConstants.java)
    //         );

    //     }

    //     else if (positionNumber == 2) {
            
    //     }

    //     else if (positionNumber == 3) {
            
    //     }

    //     else {
            
    //     }
    // }

    // SUPERSTRUCTURE WIP ||| SUPERSTRUCTURE WIP ||| SUPERSTRUCTURE WIP ||| SUPERSTRUCTURE WIP ||| SUPERSTRUCTURE WIP


}
