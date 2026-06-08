package frc.robot.subsystems.slapdown;

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

public class SlapdownIOSparkMax implements SlapdownIO {
    private final SparkMax m_motor;

    private final SparkClosedLoopController m_PidController;

    private final RelativeEncoder m_encoder;

    public SlapdownIOSparkMax() {
        m_motor = new SparkMax(SlapdownConstants.kMotorID, MotorType.kBrushless); 

        SparkMaxConfig m_config = new SparkMaxConfig();

        m_config
            .inverted(SlapdownConstants.kInverted)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(SlapdownConstants.kCurrentLimit);

        m_config.encoder
            .positionConversionFactor(SlapdownConstants.kPositionFactor)
            .velocityConversionFactor(SlapdownConstants.kVelocityFactor);

        m_config.closedLoop
            .p(SlapdownConstants.kP)
            .i(SlapdownConstants.kI)
            .d(SlapdownConstants.kD)
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder);
        
        m_config.closedLoop.feedForward
            .kS(SlapdownConstants.kS)
            .kV(SlapdownConstants.kV)
            .kA(SlapdownConstants.kA)
            .kCos(SlapdownConstants.kCos)
            .kCosRatio(SlapdownConstants.kCosRatio);

        m_config.closedLoop.maxMotion
            .cruiseVelocity(SlapdownConstants.kCruiseVelocity)
            .maxAcceleration(SlapdownConstants.kMaxAcceleration)
            .allowedProfileError(0.05);

        m_config.softLimit
            .forwardSoftLimitEnabled(false)
            .reverseSoftLimitEnabled(false);

        m_motor.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        m_PidController = m_motor.getClosedLoopController();
        
        m_encoder = m_motor.getEncoder();

    }

    public void setVoltage(double volts) {
        m_motor.setVoltage(volts);

    }

    public void setPosition(double radians) {
        m_PidController.setSetpoint(radians, ControlType.kPosition);

    }

    public void rezero() {
        m_encoder.setPosition(0.0);

    }

    public void updateInputs(SlapdownIOInputs inputs) {
        inputs.appliedVolts = m_motor.getAppliedOutput() * m_motor.getBusVoltage();
        inputs.currentAmps = m_motor.getOutputCurrent();
        inputs.motorTempDegC = m_motor.getMotorTemperature();
        inputs.velocityRadiansPerSecond = m_encoder.getVelocity();
        inputs.angleRadians = m_encoder.getPosition();
        inputs.PIDTargetRadians = m_PidController.getSetpoint();

    }

}
