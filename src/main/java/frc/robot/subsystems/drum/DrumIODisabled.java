package frc.robot.subsystems.drum;

public class DrumIODisabled implements DrumIO {
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
    public void updateInputs(DrumIOInputs inputs) {
        inputs.motorAppliedVoltage = voltageTarget;
        inputs.motorVelocityRadPerSec = velocityTarget;
    }

    @Override
    public void stop() {
        setVoltage(0);
    }
    
}
