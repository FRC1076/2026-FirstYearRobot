// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.SystemConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.climber.ClimbDown;
import frc.robot.commands.climber.ClimbUp;
import frc.robot.commands.drive.TeleopDriveCommand;
import frc.robot.commands.drive.TeleopDriveCommandV2;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.climber.*;
import frc.robot.subsystems.drive.DriveConstants.ModuleConstants.Common.Drive;
import frc.robot.subsystems.drive.DriveConstants.ModuleConstants.ModuleConfig;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.roller.RollerSubsystem;
import frc.robot.subsystems.roller.RollerIOSparkMax;
import frc.robot.subsystems.slapdown.SlapdownSubsystem;
import frc.robot.subsystems.slapdown.SlapdownConstants;
import frc.robot.subsystems.slapdown.SlapdownIOSparkMax;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIONavX;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOHardware;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

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
    //private final FuelSubsystem fuelSubsystem;
    //private final ClimberSubsystem climberSubsystem;

    private final RollerSubsystem rollerSubsystem;

    //private final SlapdownSubsystem slapdownSubsystem;

    private final TeleopDriveCommandV2 teleopDriveCommand;

    // The driver's controller
    private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);

    // The operator's controller
    private final CommandXboxController operatorController = new CommandXboxController(OperatorConstants.kOperatorControllerPort);

    // The autonomous chooser
    private final SendableChooser<Command> autoChooser = new SendableChooser<>();

    /**
    * The container for the robot. Contains subsystems, OI devices, and commands.
    */
    public RobotContainer() {
        driveSubsystem = new DriveSubsystem(
            new GyroIONavX(),
            new ModuleIOHardware(ModuleConfig.FrontLeft),
            new ModuleIOHardware(ModuleConfig.FrontRight),
            new ModuleIOHardware(ModuleConfig.RearLeft),
            new ModuleIOHardware(ModuleConfig.RearRight)
        );
        rollerSubsystem = new RollerSubsystem(new RollerIOSparkMax());
        //slapdownSubsystem = new SlapdownSubsystem(new SlapdownIOSparkMax());

        //fuelSubsystem = new FuelSubsystem();
        //climberSubsystem = new ClimberSubsystem();

        // Set the options to show up in the Dashboard for selecting auto modes. If you
        // add additional auto modes you can add additional lines here with
        // autoChooser.addOption

        // autoChooser.setDefaultOption("Autonomous", new Auto(driveSubsystem, fuelSubsystem)); <-- may add back in later

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

    /**
    * Use this method to define your trigger->command mappings. Triggers can be
    * created via the {@link Trigger#Trigger(java.util.function.BooleanSupplier)}
    * constructor with an arbitrary predicate, or via the named factories in
    * {@link edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses
    * for {@link CommandXboxController Xbox}/
    * {@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller PS4}
    * controllers or
    * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
    * joysticks}.
    */

    private void configureBindings() {
        driveSubsystem.setDefaultCommand(teleopDriveCommand);

        m_driverController.leftBumper().whileTrue(teleopDriveCommand.applyDoubleClutch());
        m_driverController.rightBumper().whileTrue(teleopDriveCommand.applySingleClutch());
        m_driverController.leftTrigger().whileTrue(rollerSubsystem.runVoltage(3.0)); // INTAKE
        // m_driverController.someTrigger().whileTrue(slapdownSubsystem.applyPosition(SlapdownConstants.kMinAngleRadians)); (CHANGE?)
        // m_driverController.rightTrigger().whileTrue(); // SHOOT

        // While the left bumper on operator controller is held, intake Fuel

        //m_driverController.leftTrigger().whileTrue(new Intake(fuelSubsystem));

        // While the right bumper on the operator controller is held, spin up for 1
        // second, then launch fuel. When the button is released, stop.

        //m_driverController.rightTrigger().whileTrue(new LaunchSequence(fuelSubsystem));

        // While the A button is held on the operator controller, eject fuel back out
        // the intake

        //m_driverController.a().whileTrue(new Eject(fuelSubsystem));

       // While the down arrow on the directional pad is held it will unclimb the robot

        //m_driverController.povDown().whileTrue(new ClimbDown(climberSubsystem));

        // While the up arrow on the directional pad is held it will cimb the robot

        //m_driverController.povUp().whileTrue(new ClimbUp(climberSubsystem));

        // Set the default command for the drive subsystem to the command provided by
        // factory with the values provided by the joystick axes on the driver
        // controller. The Y axis of the controller is inverted so that pushing the
        // stick away from you (a negative value) drives the robot forwards (a positive
        // value)

        //driveSubsystem.setDefaultCommand(new Drive(driveSubsystem, m_driverController)); <-- may add back in later

        //fuelSubsystem.setDefaultCommand(fuelSubsystem.run(() -> fuelSubsystem.stop()));

        //climberSubsystem.setDefaultCommand(climberSubsystem.run(() -> climberSubsystem.stop()));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An example command will be run in autonomous
        return Autos.exampleAuto(m_exampleSubsystem);
    }
}