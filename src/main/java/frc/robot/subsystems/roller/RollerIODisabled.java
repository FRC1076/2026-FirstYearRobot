package frc.robot.subsystems.roller;

public class RollerIODisabled implements RollerIO {
    private double voltageTarget = 0.0;
    private double velocityTargetRadPerSec = 0.0;

    @Override
    public void setVoltage(double volts) {
        voltageTarget = volts;
    }

    @Override
    public void setVelocity(double radPerSec) {
        velocityTargetRadPerSec = radPerSec;
    }
    
    @Override 
    public void updateInputs(RollerIOInputs inputs) {
        inputs.motorAppliedVoltage[0] = voltageTarget;
        inputs.motorAppliedVoltage[1] = voltageTarget;
        inputs.motorVelocityRadPerSec[0] = velocityTargetRadPerSec;
        inputs.motorVelocityRadPerSec[1] = velocityTargetRadPerSec;
    }
}