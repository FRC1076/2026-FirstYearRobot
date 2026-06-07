package frc.robot.subsystems.belts;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.littletonrobotics.junction.Logger;

public class BeltSubsystem extends SubsystemBase {
    private BeltIO io;
    private BeltIOInputsAutoLogged inputs = new BeltIOInputsAutoLogged();

    public BeltSubsystem(BeltIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Roller", inputs);
    }

    public double getVelocity() {
        return inputs.motorVelocityRadPerSec;
    }

    /** Set the motor to the specific voltage */
    public Command setVoltage(double volts) {
        return Commands.runOnce(
            () -> io.setVoltage(volts),
            this
        );
    }

    /** Run the Belt motor at supplied voltage */ //????
    public Command runVoltage(Double volts) {
        return Commands.run(
            () -> io.setVoltage(volts),
            this
        );
    }

    /** Stop the belt's motors */
    public Command stop() {
        return setVoltage(0);
    }

    /** Gets the belt motor's voltage */
    public double getVoltage() {
        return inputs.motorAppliedVoltage;
    }
    
}

