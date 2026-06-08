package frc.robot.subsystems.drum;

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

public class DrumIOSparkMax implements DrumIO {
    private final SparkMax m_motor;

    private final SparkClosedLoopController m_PidController;

    private final RelativeEncoder m_encoder;

    public DrumIOSparkMax() {
        m_motor = new SparkMax(DrumConstants.kMotorID, MotorType.kBrushless);
        
        SparkMaxConfig m_config = new SparkMaxConfig();

        m_config
            .inverted(DrumConstants.kMotorInverted)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(DrumConstants.kCurrentLimit);

        m_config.encoder
            .positionConversionFactor(DrumConstants.kPositionFactor)
            .velocityConversionFactor(DrumConstants.kVelocityFactor);

        m_config.closedLoop
            .p(DrumConstants.kP)
            .i(DrumConstants.kI)
            .d(DrumConstants.kD)
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder);
        
        m_config.closedLoop.feedForward
            .kS(DrumConstants.kS)
            .kV(DrumConstants.kV)
            .kA(DrumConstants.kA);

        m_config.closedLoop.maxMotion
            .maxAcceleration(DrumConstants.kMaxAcceleration)
            .allowedProfileError(0.01);

        m_config.softLimit
            .forwardSoftLimitEnabled(false)
            .reverseSoftLimitEnabled(false);
        
        m_motor.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        m_PidController = m_motor.getClosedLoopController();

        m_encoder = m_motor.getEncoder();
    }

    @Override
    public void setVoltage(double volts) {
        m_motor.setVoltage(volts);
    }

    @Override
    public void setVelocity(double radPerSec) {
        m_PidController.setSetpoint(radPerSec, ControlType.kVelocity);
    }

    @Override
    public void updateInputs(DrumIOInputs inputs) {
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
