package frc.robot.subsystems.belts;

import org.littletonrobotics.junction.AutoLog;

public interface BeltIO {
    @AutoLog
    public static class BeltIOInputs {
        public double motorAppliedVoltage = 0.0;

        public double motorCurrentAmps = 0.0;

        public double motorVelocityRadPerSec = 0.0;

        public double motorTempDegC = 0.0;
    }

    public abstract void setVoltage(double volts);
    
    public abstract void updateInputs(BeltIOInputs inputs);
}
