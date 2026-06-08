package frc.robot.subsystems.drum;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.littletonrobotics.junction.Logger;

public class DrumSubsystem extends SubsystemBase {
    private DrumIO io;
    private DrumIOInputsAutoLogged inputs = new DrumIOInputsAutoLogged();

    public DrumSubsystem(DrumIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Drum", inputs);
    }

    /** Get the Drum motors velocities */
    public double getVelocity() {
        return inputs.motorVelocityRadPerSec;
    }

    /** Set the motors to the specific voltages */
    public Command setVoltage(double topVolts, double bottomVolts) {
        return Commands.parallel(
            Commands.runOnce (() -> io.setVoltage(topVolts), this)
        );
    }

    /** Run the Drum motors at supplied voltages */
    public Command runVoltage(DoubleSupplier topVolts, DoubleSupplier bottomVolts) {
        return Commands.parallel(
            Commands.run (() -> io.setVoltage(topVolts.getAsDouble()), this)
        );
    }

    /** Stop the Drum's motors */
    public Command stop() {
        return setVoltage(0, 0);
    }

    /** Gets the Drum motors voltage */
    public double getVoltage() {
        return inputs.motorAppliedVoltage;
    }

}
