package frc.robot.subsystems.kicker;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonSRXPIDSetConfiguration;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.Encoder;


// IN PROGRESS IN PROGRESS IN PROGRESS IN PROGRESS IN PROGRESS IN PROGRESS

public class KickerIOTalonSRX implements KickerIO {
    private final TalonSRX m_motor;
    // private final Encoder m_encoder;

    public KickerIOTalonSRX() {
        m_motor = new TalonSRX(KickerConstants.kMotorID);

        m_motor.configFactoryDefault();

        m_motor.setInverted(KickerConstants.kMotorInverted);
        m_motor.setNeutralMode(NeutralMode.Brake);
        m_motor.configPeakCurrentLimit(KickerConstants.kCurrentLimit);
        
        m_motor.config_kP(KickerConstants.kPIDSlot, KickerConstants.kP);
        m_motor.config_kI(KickerConstants.kPIDSlot, KickerConstants.kI);
        m_motor.config_kD(KickerConstants.kPIDSlot, KickerConstants.kD);
       
        
    


    }

    @Override
    public void setVoltage(double volts){

    }

    @Override
    public void setVelocity(double radPerSec){

    }

    @Override
    public void updateInputs(KickerIOInputs inputs) {

    }

    @Override
    public void stop() {

    }


    
}
 // IN PROGRESS IN PROGRESS IN PROGRESS IN PROGRESS IN PROGRESS IN PROGRESS