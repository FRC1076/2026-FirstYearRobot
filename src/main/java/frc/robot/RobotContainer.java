// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Set;

import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.drive.TeleopDriveCommandV2;
import frc.robot.subsystems.Superstructure;
import frc.robot.subsystems.SuperstructureConstants;
import frc.robot.subsystems.drive.DriveConstants;
import frc.robot.subsystems.drive.DriveConstants.ModuleConstants.ModuleConfig;
import frc.robot.subsystems.drive.DriveSubsystem;
import frc.robot.subsystems.drive.GyroIONavX;
import frc.robot.subsystems.drive.ModuleIOHardware;
import frc.robot.subsystems.drum.DrumIOSparkMax;
import frc.robot.subsystems.drum.DrumSubsystem;
import frc.robot.subsystems.kicker.KickerIOSparkMax;
import frc.robot.subsystems.kicker.KickerSubsystem;
import frc.robot.subsystems.roller.RollerIOSparkMax;
import frc.robot.subsystems.roller.RollerSubsystem;
import frc.robot.subsystems.slapdown.SlapdownIOSparkMax;
import frc.robot.subsystems.slapdown.SlapdownSubsystem;
import lib.hardware.hid.SamuraiXboxController;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...

    private final DriveSubsystem driveSubsystem;
    //private final FuelSubsystem fuelSubsystem;
    //private final ClimberSubsystem climberSubsystem;

    private final RollerSubsystem rollerSubsystem;

    private final SlapdownSubsystem slapdownSubsystem;

    private final DrumSubsystem drumSubsystem;

    private final KickerSubsystem kickerSubsystem;

    // private final VisionLocalizationSystem visionSystem;

    private final Superstructure superstructure;

    private final TeleopDriveCommandV2 teleopDriveCommand;

    // The driver's controller
    private final SamuraiXboxController m_driverController = new SamuraiXboxController(OperatorConstants.kDriverControllerPort);

    // The operator's controller
    private final SamuraiXboxController m_operatorController = new SamuraiXboxController(OperatorConstants.kOperatorControllerPort);

    // The autonomous chooser
    private final SendableChooser<String> autoChooser = new SendableChooser<>();

    /**
    * The container for the robot. Contains subsystems, OI devices, and commands.
    */
    public RobotContainer() {
        // visionSystem = new VisionLocalizationSystem();
        driveSubsystem = new DriveSubsystem(
            new GyroIONavX(),
            new ModuleIOHardware(ModuleConfig.FrontLeft),
            new ModuleIOHardware(ModuleConfig.FrontRight),
            new ModuleIOHardware(ModuleConfig.RearLeft),
            new ModuleIOHardware(ModuleConfig.RearRight)
            // visionSystem
        );
        rollerSubsystem = new RollerSubsystem(new RollerIOSparkMax());
        slapdownSubsystem = new SlapdownSubsystem(new SlapdownIOSparkMax());
        drumSubsystem = new DrumSubsystem(new DrumIOSparkMax());
        kickerSubsystem = new KickerSubsystem(new KickerIOSparkMax());



        // Define locations of cameras relative to center (FIND)
        // Transform3d leftCameraLocation = new Transform3d(new Translation3d(0.3, 0.0, 0.5), new Rotation3d());
        // Transform3d rightCameraLocation = new Transform3d(new Translation3d(-0.3, 0.0, 0.5), new Rotation3d(0, 0, Math.PI));
        
        // Create the camera objects
        /* CameraLocalizer backLeftCamera = new PhotonVisionLocalizerWithTagPrioritization(
            new PhotonCamera("LeftCam"), 
            leftCameraLocation,
            PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
            PoseStrategy.PNP_DISTANCE_TRIG_SOLVE,
            () -> driveSubsystem.getPose().getRotation(),
            PhysicalConstants.VisionConstants.kAprilTagFieldLayout,
            (VecBuilder.fill(1.5, 1.5, 1.5)).times(PhysicalConstants.VisionConstants.PhotonVision.kHubTagPriority),
            VecBuilder.fill(0.75, 0.75, 0.75).times(PhysicalConstants.VisionConstants.PhotonVision.kHubTagPriority),
            VisionConstants.kHubTags,
            (1.0 / PhysicalConstants.VisionConstants.PhotonVision.kHubTagPriority)
        ); */

        /*CameraLocalizer frontRightCamera = new PhotonVisionLocalizerWithTagPrioritization(
            new PhotonCamera("RightCam"),
            rightCameraLocation,
            PoseStrategy.MULTI_TAG_PNP_ON_COPROCESSOR,
            PoseStrategy.PNP_DISTANCE_TRIG_SOLVE,
            () -> driveSubsystem.getPose().getRotation(),
            PhysicalConstants.VisionConstants.kAprilTagFieldLayout,
            (VecBuilder.fill(1.5, 1.5, 1.5)).times(PhysicalConstants.VisionConstants.PhotonVision.kHubTagPriority),
            (VecBuilder.fill(0.75, 0.75, 0.75)).times(PhysicalConstants.VisionConstants.PhotonVision.kHubTagPriority),
            VisionConstants.kHubTags,
            (1.0 / PhysicalConstants.VisionConstants.PhotonVision.kHubTagPriority)
        ); */

        // Add the cameras to the vision system
        // visionSystem.addCamera(backLeftCamera);
        // visionSystem.addCamera(frontRightCamera);    

        teleopDriveCommand = driveSubsystem.CommandBuilder.driveTeleop(
            () -> -m_driverController.getLeftY(), 
            () -> -m_driverController.getLeftX(), 
            () -> -m_driverController.getRightX(),
            1,
            1, 
            DriveConstants.useSpeedScaling
        );

        superstructure = new Superstructure(driveSubsystem, drumSubsystem, kickerSubsystem, rollerSubsystem, slapdownSubsystem);

        Shuffleboard.getTab("Autonomous")
            .add("Auto Selector", autoChooser)
            .withWidget(BuiltInWidgets.kComboBoxChooser)
            .withPosition(0,0)
            .withSize(2,1);

        autoChooser.setDefaultOption("Middle to Depot to Shoot", "Middle to Depot to Shoot");
        autoChooser.addOption("Left Bump to Depot to Shoot", "Left Bump to Depot to Shoot");
        autoChooser.addOption("Left Trench to Depot to Shoot", "Left Trench to Depot to Shoot");
        autoChooser.addOption("Right Trench to Depot to Shoot", "Right Trench to Depot to Shoot");
        autoChooser.addOption("Right Bump to Depot to Shoot", "Right Bump to Depot to Shoot");

        autoChooser.addOption("Move Backward to Shoot", "Move Backward to Shoot");

        SmartDashboard.putData("Auto choices", autoChooser);

        NamedCommands.registerCommand("intake", Commands.defer(() -> superstructure.intakeForAuto(SuperstructureConstants.kIntakeRollerVoltage1), Set.of(rollerSubsystem, slapdownSubsystem)));
        NamedCommands.registerCommand("shoot", Commands.defer(() -> superstructure.shootForAuto(), Set.of(kickerSubsystem, drumSubsystem)));

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

        m_driverController.povUp().onTrue(superstructure.shootPreset(1)); // will be: climber post shooting preset
        m_driverController.povRight().onTrue(superstructure.shootPreset(2));
        m_driverController.povDown().onTrue(superstructure.shootPreset(3)); // will be: passing preset
        m_driverController.povLeft().onTrue(superstructure.shootPreset(4));
        
        m_driverController.y().onTrue(superstructure.stopShooting());

        m_driverController.leftTrigger().whileTrue(superstructure.intake(SuperstructureConstants.kIntakeRollerVoltage1)).onFalse(superstructure.stopIntake());
        m_driverController.rightTrigger().whileTrue(superstructure.shoot());

        m_driverController.start().onTrue(Commands.runOnce(() -> driveSubsystem.rezeroGyro()));

        // operator controls 

        m_operatorController.a().whileTrue(superstructure.agitateHopper());

        m_operatorController.leftTrigger().whileTrue(superstructure.intakeBackwards());
        m_operatorController.rightTrigger().whileTrue(superstructure.shootBackwards());


    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        String selected = autoChooser.getSelected();
        if (selected == null) {
            return Commands.none();
        }
        return new PathPlannerAuto(selected);
    }
}