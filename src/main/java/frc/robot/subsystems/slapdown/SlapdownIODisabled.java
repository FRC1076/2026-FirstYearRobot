package frc.robot.subsystem.slapdown;

public class SlapdownIODisabled implements SlapdownIO {
    private double appliedVoltage = 0;
    private double positionTargetRadians = 0;

    @Override
    public void setVoltage(double volts) {
        appliedVoltage = volts;
    }
    @Override
    public void updateInputs(SlapdownIOInputs inputs) {
        inputs.appliedVolts = appliedVoltage;
        inputs.angleRadians = positionTargetRadians;
    }
}