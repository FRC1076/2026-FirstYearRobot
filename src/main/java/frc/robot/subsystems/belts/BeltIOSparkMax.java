package frc.robot.subsystems.belts;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkMax;

public class BeltIOSparkMax implements BeltIO {
    private final SparkMax m_motor;
    private final RelativeEncoder m_encoder;

    public BeltIOSparkMax() {
        m_motor = new SparkMax(BeltConstants.kMotorID, MotorType.kBrushless);
        
        SparkMaxConfig m_config = new SparkMaxConfig();

        m_config
            .inverted(BeltConstants.kMotorInverted)
            .idleMode(BeltConstants.kIdleModeSparkMax)
            .smartCurrentLimit(BeltConstants.kCurrentLimit);

        m_config.encoder
            .positionConversionFactor(BeltConstants.kPositionFactor)
            .velocityConversionFactor(BeltConstants.kVelocityFactor);

        m_config.softLimit
            .forwardSoftLimitEnabled(false)
            .reverseSoftLimitEnabled(false);

        m_motor.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        m_encoder = m_motor.getEncoder();
        
    }

    @Override
    public void setVoltage(double volts) {
        m_motor.setVoltage(volts);
    }
    
    @Override
    public void updateInputs(BeltIOInputs inputs){
        inputs.motorAppliedVoltage = m_motor.getAppliedOutput() * m_motor.getBusVoltage();

        inputs.motorCurrentAmps = m_motor.getOutputCurrent();

        inputs.motorVelocityRadPerSec = m_encoder.getVelocity();

        inputs.motorTempDegC = m_motor.getMotorTemperature();
    }


}
