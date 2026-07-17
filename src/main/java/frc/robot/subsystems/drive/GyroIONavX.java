// Copyright (c) FRC 1076 PiHi Samurai
// You may use, distribute, and modify this software under the terms of
// the license found in the root directory of this project

// The contents of this file are based off those of
// team 6328 Mechanical Advantage's Spark Swerve Template,
// whose license can be found in AdvantageKit-License.md
// in the root directory of this file

package frc.robot.subsystems.drive;

import static frc.robot.subsystems.drive.DriveConstants.odometryFrequencyHz;

import java.util.concurrent.ConcurrentLinkedQueue;
import com.studica.frc.AHRS;
import com.studica.frc.AHRS.NavXComType;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;

import java.util.ArrayList;

/** IO implementation for NavX. */
public class GyroIONavX implements GyroIO {
    private final AHRS navX = new AHRS(NavXComType.kMXP_SPI, (byte) odometryFrequencyHz);
    private final ConcurrentLinkedQueue<Double> yawPositionQueue;
    private final ConcurrentLinkedQueue<Long> yawTimestampQueue;

    private final ArrayList<Double> yawPositionBuffer = new ArrayList<>(20);
    private final ArrayList<Long> yawTimestampBuffer = new ArrayList<>(20);

    public GyroIONavX() {
        yawTimestampQueue = OdometryThread.getInstance().makeTimestampQueue();
        yawPositionQueue = OdometryThread.getInstance().registerSignal( () -> Math.toRadians(navX.getAngle()));
    }

    @Override
    public void reset() {
        navX.reset();
    }

    @Override
    public void updateInputs(GyroIOInputs inputs) {
        inputs.connected = navX.isConnected();
        inputs.yawPosition = Rotation2d.fromDegrees(-navX.getAngle());
        inputs.yawVelocityRadPerSec = Units.degreesToRadians(-navX.getRawGyroZ());
        
        int samples = OdometryThread.getInstance().sampleCount;
        OdometryThread.safeDrain(yawTimestampQueue, yawTimestampBuffer, samples);
        OdometryThread.safeDrain(yawPositionQueue, yawPositionBuffer, samples);

        inputs.odometryYawPositions = yawPositionBuffer.stream().map((Double value) -> Rotation2d.fromDegrees(value)).toArray(Rotation2d[]::new);
        inputs.odometryYawTimestamps = yawTimestampBuffer.stream().mapToDouble((Long value) -> value/1e6).toArray();

        yawTimestampBuffer.clear();
        yawPositionBuffer.clear();
    }
}