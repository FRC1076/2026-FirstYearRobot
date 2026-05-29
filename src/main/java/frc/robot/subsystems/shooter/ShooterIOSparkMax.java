package frc.robot.subsystems.shooter;

import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import frc.robot.subsystems.roller.RollerConstants;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;

public class ShooterIOSparkMax implements ShooterIO {
    private final SparkMax m_topMotor;
    private final SparkMax m_bottomMotor;

    private final RelativeEncoder m_topMotorEncoder;
    private final RelativeEncoder m_bottomMotorEncoder;

    public ShooterIOSparkMax() {
        m_topMotor = new SparkMax(ShooterConstants.kTopMotorID, MotorType.kBrushless);
        m_bottomMotor = new SparkMax(ShooterConstants.kBottomMotorID, MotorType.kBrushless);
        
        SparkMaxConfig m_config = new SparkMaxConfig();

        m_config
            .inverted(ShooterConstants.kTopMotorInverted)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(ShooterConstants.kCurrentLimit);

        m_config.encoder
            .positionConversionFactor(ShooterConstants.kPositionFactor)
            .velocityConversionFactor(ShooterConstants.kVelocityFactor);

        m_config.closedLoop
            .p(ShooterConstants.kP)
            .i(ShooterConstants.kI)
            .d(ShooterConstants.kD)
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder);
        
        m_config.closedLoop.feedForward
            .kS(ShooterConstants.kS)
            .kV(ShooterConstants.kV)
            .kA(ShooterConstants.kA)
            .kCos(ShooterConstants.kCos)
            .kCosRatio(ShooterConstants.kCosRatio);

        m_config.closedLoop.maxMotion
            .cruiseVelocity(ShooterConstants.kCruiseVelocity)
            .maxAcceleration(ShooterConstants.kMaxAcceleration)
            .allowedProfileError(0.01);

        m_config.softLimit
            .forwardSoftLimitEnabled(false)
            .reverseSoftLimitEnabled(false);
        
        m_topMotor.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        m_config.inverted(ShooterConstants.kBottomMotorInverted);
        m_bottomMotor.configure(m_config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        m_topMotorEncoder = m_topMotor.getEncoder();
        m_bottomMotorEncoder = m_bottomMotor.getEncoder();
    }

    @Override
    public void setTopVoltage(double volts) {
        m_topMotor.setVoltage(volts);
    }

    @Override
    public void setBottomVoltage(double volts) {
        m_bottomMotor.setVoltage(volts);
    }

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.motorAppliedVoltage[0] = m_topMotor.getAppliedOutput();
        inputs.motorAppliedVoltage[1] = m_topMotor.getAppliedOutput();

        inputs.motorCurrentAmps[0] = m_topMotor.getOutputCurrent();
        inputs.motorCurrentAmps[1] = m_topMotor.getOutputCurrent();

        inputs.motorTempDegC[0] = m_topMotor.getMotorTemperature();
        inputs.motorTempDegC[1] = m_bottomMotor.getMotorTemperature();

        inputs.motorVelocityRadPerSec[0] = m_topMotorEncoder.getVelocity();
        inputs.motorVelocityRadPerSec[1] = m_bottomMotorEncoder.getVelocity();
    }

    @Override 
    public void stop() {
        m_topMotor.setVoltage(0);
        m_bottomMotor.setVoltage(0);
    }
    
}
