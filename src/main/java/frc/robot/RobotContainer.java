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
import frc.robot.subsystems.Superstructure;
import frc.robot.subsystems.climber.ClimberSubsystem;
import frc.robot.subsystems.climber.*;
import frc.robot.subsystems.drive.DriveConstants.ModuleConstants.Common.Drive;
import frc.robot.subsystems.drum.DrumIOSparkMax;
import frc.robot.subsystems.drum.DrumSubsystem;
import frc.robot.subsystems.kicker.KickerIOSparkMax;
import frc.robot.subsystems.kicker.KickerIOTalonSRX;
import frc.robot.subsystems.kicker.KickerSubsystem;
import frc.robot.subsystems.drive.DriveConstants.ModuleConstants.ModuleConfig;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.roller.RollerSubsystem;
import frc.robot.subsystems.roller.RollerIOSparkMax;
import frc.robot.subsystems.slapdown.SlapdownSubsystem;
import lib.hardware.hid.SamuraiXboxController;
import frc.robot.subsystems.slapdown.SlapdownConstants;
import frc.robot.subsystems.slapdown.SlapdownIOSparkMax;
import frc.robot.subsystems.drive.GyroIO;
import frc.robot.subsystems.drive.GyroIONavX;
import frc.robot.subsystems.drive.ModuleIO;
import frc.robot.subsystems.drive.ModuleIOHardware;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
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

    private final SlapdownSubsystem slapdownSubsystem;

    private final DrumSubsystem drumSubsystem;

    private final KickerSubsystem kickerSubsystem;

    private final Superstructure superstructure;

    private final TeleopDriveCommandV2 teleopDriveCommand;

    // The driver's controller
    private final SamuraiXboxController m_driverController = new SamuraiXboxController(OperatorConstants.kDriverControllerPort);

    // The operator's controller
    private final SamuraiXboxController operatorController = new SamuraiXboxController(OperatorConstants.kOperatorControllerPort);

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
        slapdownSubsystem = new SlapdownSubsystem(new SlapdownIOSparkMax());
        drumSubsystem = new DrumSubsystem(new DrumIOSparkMax());
        kickerSubsystem = new KickerSubsystem(new KickerIOTalonSRX());

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

        superstructure = new Superstructure(driveSubsystem, drumSubsystem, kickerSubsystem, rollerSubsystem, slapdownSubsystem);

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

        // driver controls 

        m_driverController.povUp().toggleOnTrue(superstructure.shootPreset(1));
        m_driverController.povLeft().toggleOnTrue(superstructure.shootPreset(2));
        m_driverController.povDown().toggleOnTrue(superstructure.shootPreset(3));
        m_driverController.povRight().toggleOnTrue(superstructure.shootPreset(4));
        
        m_driverController.a().whileTrue(superstructure.agitateHopper());
        m_driverController.b().onTrue(superstructure.intakeUp());

        m_driverController.leftTrigger().whileTrue(superstructure.intake(6.0)).onFalse(superstructure.intake(0));
        m_driverController.rightTrigger().whileTrue(superstructure.shoot());

        m_driverController.start().onTrue(Commands.runOnce(() -> driveSubsystem.rezeroGyro()));

        
        // subsystem testing controls (TEMP)

        m_driverController.x().onTrue(slapdownSubsystem.applyPosition(Math.PI / 2));
        m_driverController.y().onTrue(slapdownSubsystem.applyPosition(0));
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