package frc.robot.subsystems.shooter;

public class ShooterIODisabled implements ShooterIO {
    private double topVoltageTarget = 0.0;
    private double bottomVoltageTarget = 0.0;
    private double velocityTarget = 0.0;

    @Override
    public void setTopVoltage(double volts) {
        topVoltageTarget = volts;
    }

    @Override
    public void setBottomVoltage(double volts) {
        bottomVoltageTarget = volts;
    }

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.motorVelocityRadPerSec[0] = velocityTarget;
        inputs.motorVelocityRadPerSec[1] = velocityTarget;

        inputs.motorAppliedVoltage[0] = topVoltageTarget;
        inputs.motorAppliedVoltage[1] = bottomVoltageTarget;
    }

    @Override
    public void stop() {
        setTopVoltage(0);
        setBottomVoltage(0);
    }

}
