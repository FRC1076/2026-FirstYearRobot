package frc.robot.subsystems.roller;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.roller.RollerIO.RollerIOInputs;

import org.littletonrobotics.junction.Logger;

public class RollerSubsystem extends SubsystemBase {
    private RollerIO io;
    private RollerIOInputsAutoLogged inputs = new RollerIOInputsAutoLogged();

    public RollerSubsystem(RollerIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Roller", inputs);
    }

    public double getLeadVelocity() {
        return inputs.motorVelocityRadPerSec[0];
    }

    public double getFollowVelocity() {
        return inputs.motorVelocityRadPerSec[1];
    }

    /** Set the motor to the specific voltage */
    public Command setVoltage(double volts) {
        return Commands.runOnce(
            () -> io.setVoltage(volts),
            this
        );
    }

    /** Run the Roller motor at supplied voltage */ //????
    public Command runVoltage(double volts) {
        return Commands.runEnd(
            () -> io.setVoltage(volts),
            () -> io.setVoltage(0),
            this
        );
    }

    public Command setVelocity(double radPerSec) {
        return Commands.runOnce(
            () -> io.setVelocity(radPerSec),
            this
        );
    }

    /** Stop the roller's motors */
    public Command stop() {
        return setVoltage(0);
    }

    /** Gets the roller motor's voltage */
    public double getVoltage() {
        return inputs.motorAppliedVoltage[0];
    }
    
}
