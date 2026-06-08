package frc.robot.subsystems.kicker;

import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;

public class KickerIOSparkMax implements KickerIO {
    private final SparkMax m_motor;

    private final RelativeEncoder m_encoder;

    public KickerIOSparkMax() {
        m_motor = new SparkMax(KickerConstants.kMotorID, MotorType.kBrushless);
        
        SparkMaxConfig m_config = new SparkMaxConfig();

        m_config
            .inverted(KickerConstants.kMotorInverted)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(KickerConstants.kCurrentLimit);

        m_config.encoder
            .positionConversionFactor(KickerConstants.kPositionFactor)
            .velocityConversionFactor(KickerConstants.kVelocityFactor);

        m_config.closedLoop
            .p(KickerConstants.kP)
            .i(KickerConstants.kI)
            .d(KickerConstants.kD)
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder);
        
        m_config.closedLoop.feedForward
            .kS(KickerConstants.kS)
            .kV(KickerConstants.kV)
            .kA(KickerConstants.kA)
            .kCos(KickerConstants.kCos)
            .kCosRatio(KickerConstants.kCosRatio);

        m_config.closedLoop.maxMotion
            .cruiseVelocity(KickerConstants.kCruiseVelocity)
            .maxAcceleration(KickerConstants.kMaxAcceleration)
            .allowedProfileError(0.01);

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
    public void updateInputs(KickerIOInputs inputs) {
        inputs.motorAppliedVoltage = m_motor.getAppliedOutput() * m_motor.getBusVoltage();

        inputs.motorCurrentAmps = m_motor.getOutputCurrent();

        inputs.motorTempDegC = m_motor.getMotorTemperature();

        inputs.motorVelocityRadPerSec = m_encoder.getVelocity();
    }

    @Override 
    public void stop() {
        m_motor.setVoltage(0);
    }
    
}
