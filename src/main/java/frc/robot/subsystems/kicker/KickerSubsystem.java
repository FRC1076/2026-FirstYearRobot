package frc.robot.subsystems.kicker;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.littletonrobotics.junction.Logger;

public class KickerSubsystem extends SubsystemBase {
    private KickerIO io;
    private KickerIOInputsAutoLogged inputs = new KickerIOInputsAutoLogged();

    public KickerSubsystem(KickerIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Kicker", inputs);
    }

    // /** Get the Kicker motors velocities */
    // public double getVelocity() {
    //     return inputs.motorVelocityRadPerSec;
    // }

    /** Run at volts while scheduled, stop when the command ends */
    public Command setVoltage(double volts) {
        return startEnd(() -> io.setVoltage(volts), () -> io.setVoltage(0));
    }

    /** Run the Kicker motors at supplied voltages */
    public Command runVoltage(DoubleSupplier volts) {
        return Commands.runEnd (() -> io.setVoltage(volts.getAsDouble()), () -> io.stop());
    }

    // public Command setVelocity(double radPerSec) {
    //     return Commands.runOnce(() -> io.setVelocity(radPerSec), this);
    // }

    /** Stop the Kicker's motors */
    public Command stop() {
        return setVoltage(0);
    }

    public Command stopOnce() {
        return runOnce(() -> io.setVoltage(0));
    }

    /** Gets the Kicker motors voltage */
    public double getVoltage() {
        return inputs.motorAppliedVoltage;
    }

}

