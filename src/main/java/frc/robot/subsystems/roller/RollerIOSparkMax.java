// Copyright (c) FRC 1076 PiHi Samurai
// You may use, distribute, and modify this software under the terms of
// the license found in the root directory of this project

package frc.robot.subsystems.roller;

import static edu.wpi.first.units.Units.Volts;

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

public class RollerIOSparkMax implements RollerIO {
    private final SparkMax m_leadMotor;
    private final SparkMax m_followMotor;

    private RelativeEncoder m_relativeEncoder;
    private final SparkClosedLoopController m_closedLoopController;

    public RollerIOSparkMax() {
        m_leadMotor = new SparkMax(RollerConstants.kLeadMotorPort, MotorType.kBrushless);
        m_followMotor = new SparkMax(RollerConstants.kFollowMotorPort, MotorType.kBrushless);
        
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
            .kA(RollerConstants.kA)
            .kCos(RollerConstants.kCos)
            .kCosRatio(RollerConstants.kCosRatio);

        m_leadConfig.closedLoop.maxMotion
            .cruiseVelocity(RollerConstants.kCruiseVelocity)
            .maxAcceleration(RollerConstants.kMaxAcceleration)
            .allowedProfileError(0.01);

        m_leadConfig.softLimit
            .forwardSoftLimitEnabled(false)
            .reverseSoftLimitEnabled(false);
        
        m_followConfig
            .follow(m_leadMotor, RollerConstants.kFollowMotorInverted);

        m_leadMotor.configure(m_leadConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        m_followMotor.configure(m_followConfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

        m_closedLoopController = m_leadMotor.getClosedLoopController();
        m_relativeEncoder = m_leadMotor.getEncoder();
        
    }

    @Override
    public void setVoltage(double volts) {
        m_leadMotor.setVoltage(volts);
    }

    public void setVelocity(double radPerSec){
        if (radPerSec == 0) {
            m_leadMotor.setVoltage(0);
            m_followMotor.setVoltage(0);
        } else {
            m_closedLoopController.setSetpoint(radPerSec, ControlType.kVelocity);
        }
    }
    
    public void updateInputs(RollerIOInputs inputs){
        inputs.motorAppliedVoltage[0] = m_leadMotor.getAppliedOutput() * m_leadMotor.getBusVoltage();
        inputs.motorAppliedVoltage[1] = m_followMotor.getAppliedOutput() * m_followMotor.getBusVoltage();

        inputs.motorCurrentAmps[0] = m_leadMotor.getOutputCurrent();
        inputs.motorCurrentAmps[1] = m_followMotor.getOutputCurrent();

        inputs.motorVelocityRadPerSec[0] = m_relativeEncoder.getVelocity();
        inputs.motorVelocityRadPerSec[1] = m_relativeEncoder.getVelocity();

        inputs.motorTempDegC[0] = m_leadMotor.getMotorTemperature();
        inputs.motorTempDegC[1] = m_followMotor.getMotorTemperature();
    }


}