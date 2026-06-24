package frc.robot.subsystems.drum;

import org.littletonrobotics.junction.AutoLog;

public interface DrumIO {
    @AutoLog
    public static class DrumIOInputs {
        public double[] motorAppliedVoltage = new double[2];
        public double[] motorCurrentAmps = new double[2];
        public double[] motorTempDegC = new double[2];
        public double[] motorVelocityRadPerSec = new double[2];
    }
    
    public abstract void setVoltage(double volts);

    public abstract void setVelocity(double radPerSec);

    public abstract void updateInputs(DrumIOInputs inputs);

    public abstract void stop();
    
}
