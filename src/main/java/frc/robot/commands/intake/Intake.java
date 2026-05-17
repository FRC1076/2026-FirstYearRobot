package frc.robot.commands.intake;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.intake.*;
import static frc.robot.Constants.IntakeConstants.*;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class Intake extends Command {
  /** Creates a new Intake. */

  IntakeSubsystem intakeSubsystem;

  public Intake(IntakeSubsystem intakeSystem) {
    addRequirements(intakeSystem);
    this.intakeSubsystem = intakeSystem;
  }

  // Called when the command is initially scheduled. Set the rollers to the
  // appropriate values for intaking
  @Override
  public void initialize() {
    intakeSubsystem
        .setIntakeLauncherRoller(SmartDashboard.getNumber("Intaking intake roller value", INTAKE_INTAKING_PERCENT));
    intakeSubsystem.setFeederRoller(SmartDashboard.getNumber("Intaking feeder roller value", INDEXER_INTAKING_PERCENT));
  }

  // Called every time the scheduler runs while the command is scheduled. This
  // command doesn't require updating any values while running
  @Override
  public void execute() {
  }

  // Called once the command ends or is interrupted. Stop the rollers
  @Override
  public void end(boolean interrupted) {
    intakeSubsystem.setIntakeLauncherRoller(0);
    intakeSubsystem.setFeederRoller(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}