package frc.robot.subsystems.roller;

import org.littletonrobotics.junction.AutoLog;

public interface RollerIO {
    @AutoLog
    public static class RollerIOInputs {
        public double[] motorAppliedVoltage = new double[2];

        public double[] motorCurrentAmps = new double[2];

        public double[] motorVelocityRadPerSec = new double[2];

        public double[] motorTempDegC = new double[2];
    }

    public abstract void setVoltage(double volts);
    
    public abstract void updateInputs(RollerIOInputs inputs);
}