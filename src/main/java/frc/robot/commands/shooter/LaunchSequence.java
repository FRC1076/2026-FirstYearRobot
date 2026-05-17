package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.intake.*;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class LaunchSequence extends SequentialCommandGroup {
  /** Creates a new LaunchSequence. */
  public LaunchSequence(IntakeSubsystem intakeSubsystem) {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(
        new SpinUp(intakeSubsystem).withTimeout(ShooterConstants.SPIN_UP_SECONDS),
        new Launch(intakeSubsystem));
  }
}