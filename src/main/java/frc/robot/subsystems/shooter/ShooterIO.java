package frc.robot.subsystems.shooter;

import org.littletonrobotics.junction.AutoLog;

public interface ShooterIO {
    @AutoLog
    public static class ShooterIOInputs {
        public double[] motorAppliedVoltage = new double[2];
        public double[] motorCurrentAmps = new double[2];
        public double[] motorTempDegC = new double[2];
        public double[] motorVelocityRadPerSec = new double[2];
    }

    public abstract void setTopVoltage(double volts);
    
    public abstract void setBottomVoltage(double volts);

    public abstract void updateInputs(ShooterIOInputs inputs);

    public abstract void stop();

}