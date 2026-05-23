package frc.robotsubsystem.slapdown;

import org.littleonrobotics.junction.Autolog;

public interface SlapdownIO {
    @Autolog
    public static class SlapdownIOInputs {
    public double appliedVolts = 0;
    public double currentAmps = 0;
    public double angleRadians = 0;
    public double velocityRadiansPerSecond = 0;
 }

public abstract void updateInputs(SlapdownIOInputs inputs);

public abstract void setVoltage(double volts);

// public abstract void setPosition(double radians);

//public abstract void rezero();

//public default void periodic() {};

}  