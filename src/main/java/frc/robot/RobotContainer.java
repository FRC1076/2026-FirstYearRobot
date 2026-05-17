package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.SystemConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.climber.ClimbDown;
import frc.robot.commands.climber.ClimbUp;
import frc.robot.commands.drive.TeleopDriveCommand;
import frc.robot.commands.drive.TeleopDriveCommandV2;
import frc.robot.commands.intake.Eject;
import frc.robot.commands.intake.Intake;
import frc.robot.commands.shooter.LaunchSequence;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIONavX;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOHardware;
import frc.robot.subsystems.intake.IntakeSubsystem;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import static frc.robot.Constants.OperatorConstants.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

    private final DriveSubsystem driveSubsystem;
    private final IntakeSubsystem intakeSubsystem;
    private final ClimberSubsystem climberSubsystem;

    private final TeleopDriveCommandV2 teleopDriveCommand;

    // The driver's controller
    private final CommandXboxController m_driverController =
            new CommandXboxController(OperatorConstants.kDriverControllerPort);

    // The operator's controller
    private final CommandXboxController operatorController =
            new CommandXboxController(OperatorConstants.kOperatorControllerPort);

    // The autonomous chooser
    private final SendableChooser<Command> autoChooser = new SendableChooser<>();

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        driveSubsystem = new DriveSubsystem(
            new GyroIONavX(),
            new ModuleIOHardware(ModuleIOHardware.ModuleConfig.FrontLeft),
            new ModuleIOHardware(ModuleIOHardware.ModuleConfig.FrontRight),
            new ModuleIOHardware(ModuleIOHardware.ModuleConfig.RearLeft),
            new ModuleIOHardware(ModuleIOHardware.ModuleConfig.RearRight)
        );

        intakeSubsystem = new IntakeSubsystem();
        climberSubsystem = new ClimberSubsystem();

        teleopDriveCommand = driveSubsystem.CommandBuilder.driveTeleop(
            () -> -m_driverController.getLeftY(),
            () -> -m_driverController.getLeftX(),
            () -> -m_driverController.getRightX(),
            1,
            1,
            false
        );

        configureBindings();
    }

    private void configureBindings() {
        driveSubsystem.setDefaultCommand(teleopDriveCommand);

        m_driverController.leftBumper().whileTrue(teleopDriveCommand.applyDoubleClutch());
        m_driverController.rightBumper().whileTrue(teleopDriveCommand.applySingleClutch());

        // Intake
        m_driverController.leftTrigger().whileTrue(new Intake(intakeSubsystem));

        // Launch sequence
        m_driverController.rightTrigger().whileTrue(new LaunchSequence(intakeSubsystem));

        // Eject
        m_driverController.a().whileTrue(new Eject(intakeSubsystem));

        // Climb down
        m_driverController.povDown().whileTrue(new ClimbDown(climberSubsystem));

        // Climb up
        m_driverController.povUp().whileTrue(new ClimbUp(climberSubsystem));
    }

    public Command getAutonomousCommand() {
        return Autos.exampleAuto(m_exampleSubsystem);
    }
}