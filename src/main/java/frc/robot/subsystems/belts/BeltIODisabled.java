package frc.robot.subsystems.belts;

public class BeltIODisabled implements BeltIO {
    private double voltageTarget = 0.0;

    @Override
    public void setVoltage(double volts) {
        voltageTarget = volts;
    }
    
    @Override 
    public void updateInputs(BeltIOInputs inputs) {
        inputs.motorAppliedVoltage = voltageTarget;
    }
}
