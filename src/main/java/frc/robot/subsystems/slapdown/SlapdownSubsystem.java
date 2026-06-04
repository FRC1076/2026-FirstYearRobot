package frc.robot.subsystems.slapdown;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;

import static edu.wpi.first.units.Units.Radians;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.Volts;

import java.util.function.DoubleSupplier;

import org.littletonrobotics.junction.Logger;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;

public class SlapdownSubsystem extends SubsystemBase {

    private final SlapdownIO io;
    private final SlapdownIOInputsAutoLogged inputs = new SlapdownIOInputsAutoLogged();
    private final SysIdRoutine sysId;

    public SlapdownSubsystem(SlapdownIO io) {
        this.io = io;

        sysId = new SysIdRoutine(
            new SysIdRoutine.Config(
            null, Volts.of(1), null,
            (state) -> Logger.recordOutput("Slapdown/SysIdState", state.toString())
            ),

            new SysIdRoutine.Mechanism(
                (voltage) -> io.setVoltage(voltage.in(Volts)),
                (log) ->
                    log.motor("Slapdown Neo")
                        .voltage(Volts.of(inputs.appliedVolts))
                        .angularPosition(Radians.of(inputs.angleRadians))
                        .angularVelocity(RadiansPerSecond.of(inputs.velocityRadiansPerSecond)),
                this 
                
            )
        );
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Slapdown", inputs);
    }

    public double getSlapdownAngleRadians() {
        return inputs.angleRadians;
    }

    public boolean withinTolerance(double target) {
        return Math.abs(getSlapdownAngleRadians() - target) < SlapdownConstants.kAngleToleranceRadians;
    }

    public boolean withinTolerance(DoubleSupplier target) {
        return Math.abs(getSlapdownAngleRadians() - target.getAsDouble()) < SlapdownConstants.kAngleToleranceRadians;
    }

    /** Set the voltage applied to the motor with software stops enabled */
    public Command applyVoltage(double volts) {
        return Commands.runOnce(
            () -> io.setVoltage(volts),
            this
        );
    }

    /** Run the motor at the supplied voltage with software stops enabled */
    public Command runVoltage(DoubleSupplier volts) {
        return Commands.run(
            () -> io.setVoltage(volts.getAsDouble()),
            this
        );
    }

    /** Tell the slapdown to go to the specified position */
    public Command applyPosition(double radians) {
        return Commands.runOnce(
            () -> io.setPosition(MathUtil.clamp(radians, SlapdownConstants.kMinAngleRadians, SlapdownConstants.kMaxAngleRadians)),
            this
        );
    }

    /** Run the slapdown to the supplied position */
    public Command runPosition(DoubleSupplier radians) {
        return Commands.run(
            () -> io.setPosition(MathUtil.clamp(radians.getAsDouble(), SlapdownConstants.kMinAngleRadians, SlapdownConstants.kMaxAngleRadians)),
            this
        );
    }

    /** Set the current position of the slapdown to zero */
    public Command rezeroSlapdown() {
        return Commands.runOnce(() -> io.rezero());
    }

    public Command sysIdQuasistatic(Direction direction) {
        return sysId.quasistatic(direction);
    }

    public Command sysIdDynamic(Direction direction) {
        return sysId.dynamic(direction);
    }




    
}
