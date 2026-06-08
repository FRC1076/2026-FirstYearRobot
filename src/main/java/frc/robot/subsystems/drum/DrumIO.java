package frc.robot.subsystems.drum;

import org.littletonrobotics.junction.AutoLog;

public interface DrumIO {
    @AutoLog
    public static class DrumIOInputs {
        public double motorAppliedVoltage = 0;
        public double motorCurrentAmps = 0;
        public double motorTempDegC = 0;
        public double motorVelocityRadPerSec = 0;
    }
    
    public abstract void setVoltage(double volts);

    public abstract void setVelocity(double radPerSec);

    public abstract void updateInputs(DrumIOInputs inputs);

    public abstract void stop();
    
}
