package frc.robot.subsystems.slapdown;

public class SlapdownIODisabled implements SlapdownIO {
    private double appliedVoltage = 0.0;
    private double positionTargetRadians = 0.0;

    @Override
    public void setVoltage(double volts) {
        appliedVoltage = volts;
    }

    @Override
    public void setPosition(double radians) {
        positionTargetRadians = radians;
    }

    @Override
    public void rezero() {
        positionTargetRadians = 0.0;
    }

    @Override
    public void updateInputs(SlapdownIOInputs inputs) {
        inputs.appliedVolts = appliedVoltage;
        inputs.angleRadians = positionTargetRadians;
    }
}