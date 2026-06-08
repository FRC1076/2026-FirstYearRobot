package frc.robot.subsystems.kicker;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class KickerIOTalonSRX implements KickerIO {
    private final WPI_TalonSRX m_motor;

    public KickerIOTalonSRX() {
        m_motor = new WPI_TalonSRX(KickerConstants.kMotorID);

        m_motor.configFactoryDefault();

        m_motor.setInverted(KickerConstants.kMotorInverted);
        m_motor.setNeutralMode(NeutralMode.Brake);
        m_motor.enableCurrentLimit(true);
        m_motor.configPeakCurrentLimit(KickerConstants.kCurrentLimit);
        
        // m_motor.config_kP(KickerConstants.kPIDSlot, KickerConstants.kP);
        // m_motor.config_kI(KickerConstants.kPIDSlot, KickerConstants.kI);
        // m_motor.config_kD(KickerConstants.kPIDSlot, KickerConstants.kD);

        m_motor.configForwardSoftLimitEnable(false);
        m_motor.configReverseSoftLimitEnable(false);
        
    }

    @Override
    public void setVoltage(double volts){
        m_motor.setVoltage(volts);
    }

    // @Override
    // public void setVelocity(double ticksPer100Ms){
    //     double numMotorTicks = ticksPer100Ms / KickerConstants.kVelocityFactor;
    //     m_motor.set(ControlMode.Velocity, numMotorTicks);

    // }

    @Override
    public void updateInputs(KickerIOInputs inputs) {
        inputs.motorAppliedVoltage = m_motor.get() * m_motor.getBusVoltage();

        inputs.motorCurrentAmps = m_motor.getStatorCurrent();

        inputs.motorTempDegC = m_motor.getTemperature();

    }

    @Override
    public void stop() {
        m_motor.setVoltage(0.0);
    }

}