package frc.robot.subsystems.roller;

public class RollerIODisabled implements RollerIO {
    private double voltageTarget = 0.0;
    private double velocityTarget = 0.0;

    @Override
    public void setVoltage(double volts) {
        voltageTarget = volts;
    }

    @Override
    public void setVelocity(double radPerSec) {
        velocityTarget = radPerSec;
    }
    
    @Override 
    public void updateInputs(RollerIOInputs inputs) {
        inputs.motorAppliedVoltage[0] = voltageTarget;
        inputs.motorAppliedVoltage[1] = voltageTarget;
        inputs.motorVelocityRadPerSec[0] = velocityTarget;
        inputs.motorVelocityRadPerSec[1] = velocityTarget;
    }
}