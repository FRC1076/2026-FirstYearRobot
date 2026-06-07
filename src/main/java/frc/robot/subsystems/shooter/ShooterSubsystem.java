package frc.robot.subsystems.shooter;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.littletonrobotics.junction.Logger;

public class ShooterSubsystem extends SubsystemBase {
    private ShooterIO io;
    private ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

    public ShooterSubsystem(ShooterIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Shooter", inputs);
    }

    /** Get the shooter motors velocities */
    public double getTopVelocity() {
        return inputs.motorVelocityRadPerSec[0];
    }

    public double getBottomVelocity() {
        return inputs.motorVelocityRadPerSec[1];
    }

    /** Set the motors to the specific voltages */
    public Command setVoltage(double topVolts, double bottomVolts) {
        return Commands.parallel(
            Commands.runOnce (() -> io.setTopVoltage(topVolts), this),
            Commands.runOnce (() -> io.setBottomVoltage(bottomVolts), this)
        );
    }

    /** Run the Shooter motors at supplied voltages */
    public Command runVoltage(DoubleSupplier topVolts, DoubleSupplier bottomVolts) {
        return Commands.parallel(
            Commands.run (() -> io.setTopVoltage(topVolts.getAsDouble()), this),
            Commands.run (() -> io.setBottomVoltage(bottomVolts.getAsDouble()), this)
        );
    }

    /** Stop the shooter's motors */
    public Command stop() {
        return setVoltage(0, 0);
    }

    /** Gets the shooter motors voltages */
    public double getTopVoltage() {
        return inputs.motorAppliedVoltage[0];
    }

    public double getBottomVoltage() {
        return inputs.motorAppliedVoltage[1];
    }

}
