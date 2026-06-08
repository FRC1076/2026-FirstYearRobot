// Copyright (c) FRC 1076 PiHi Samurai
// You may use, distribute, and modify this software under the terms of
// the license found in the root directory of this project

package frc.robot.subsystems.roller;

import static edu.wpi.first.units.Units.Volts;

import com.revrobotics.spark.FeedbackSensor;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkMax;

public class RollerIOSparkMax implements RollerIO {
    private final SparkMax m_leadMotor;
    private final SparkMax m_followMotor;

    private final SparkClosedLoopController m_PidController;

    private final RelativeEncoder m_leadEncoder;
    private final RelativeEncoder m_followEncoder;

    public RollerIOSparkMax() {
        m_leadMotor = new SparkMax(RollerConstants.kLeadMotorID, MotorType.kBrushless);
        m_followMotor = new SparkMax(RollerConstants.kFollowMotorID, MotorType.kBrushless);
        
        SparkMaxConfig m_leadConfig = new SparkMaxConfig();
        SparkMaxConfig m_followConfig = new SparkMaxConfig();

        m_leadConfig
            .inverted(RollerConstants.kLeadMotorInverted)
            .idleMode(IdleMode.kBrake)
            .smartCurrentLimit(RollerConstants.kCurrentLimit);

        m_leadConfig.encoder
            .positionConversionFactor(RollerConstants.kPositionFactor)
            .velocityConversionFactor(RollerConstants.kVelocityFactor);

        m_leadConfig.closedLoop
            .p(RollerConstants.kP)
            .i(RollerConstants.kI)
            .d(RollerConstants.kD)
            .feedbackSensor(FeedbackSensor.kPrimaryEncoder);
        
        m_leadConfig.closedLoop.feedForward
            .kS(RollerConstants.kS)
            .kV(RollerConstants.kV)
            .kA(RollerConstants.kA);

        m_leadConfig.closedLoop.maxMotion
            .maxAcceleration(RollerConstants.kMaxAcceleration)
            .allowedProfileError(0.01);

        m_leadConfig.softLimit
            .forwardSoftLimitEnabled(false)
            .reverseSoftLimitEnabled(false);
        
        m_followConfig
            .follow(m_leadMotor, RollerConstants.kFollowMotorInverted);

        m_leadMotor.configure(m_leadConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        m_followMotor.configure(m_followConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        m_PidController = m_leadMotor.getClosedLoopController();

        m_leadEncoder = m_leadMotor.getEncoder();
        m_followEncoder = m_followMotor.getEncoder();
        
    }

    @Override
    public void setVoltage(double volts) {
        m_leadMotor.setVoltage(volts);
    }

    @Override
    public void setVelocity(double radPerSec) {
        m_PidController.setSetpoint(radPerSec, ControlType.kVelocity);
    }
    
    @Override
    public void updateInputs(RollerIOInputs inputs){
        inputs.motorAppliedVoltage[0] = m_leadMotor.getAppliedOutput() * m_leadMotor.getBusVoltage();
        inputs.motorAppliedVoltage[1] = m_followMotor.getAppliedOutput() * m_followMotor.getBusVoltage();

        inputs.motorCurrentAmps[0] = m_leadMotor.getOutputCurrent();
        inputs.motorCurrentAmps[1] = m_followMotor.getOutputCurrent();

        inputs.motorVelocityRadPerSec[0] = m_leadEncoder.getVelocity();
        inputs.motorVelocityRadPerSec[1] = m_followEncoder.getVelocity();

        inputs.motorTempDegC[0] = m_leadMotor.getMotorTemperature();
        inputs.motorTempDegC[1] = m_followMotor.getMotorTemperature();
    }


}