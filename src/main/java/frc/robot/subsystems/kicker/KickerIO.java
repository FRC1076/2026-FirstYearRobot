package frc.robot.subsystems.kicker;

import org.littletonrobotics.junction.AutoLog;

public interface KickerIO {
    @AutoLog
    public static class KickerIOInputs {
        public double motorAppliedVoltage = 0;
        public double motorCurrentAmps = 0;
        public double motorTempDegC = 0;
        public double motorVelocityRadPerSec = 0;
    }

    public abstract void setVoltage(double volts);

    public abstract void updateInputs(KickerIOInputs inputs);

    public abstract void stop();
    
}
