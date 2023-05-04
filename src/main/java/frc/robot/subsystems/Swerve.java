package frc.robot.subsystems;

import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.swerve.NEOSwerveModule;
import frc.robot.Constants;
import frc.robot.Constants.Swerve.Mod1;
import frc.robot.Constants.Swerve.Mod2;
import frc.robot.Constants.Swerve.Mod3;
import frc.robot.Constants.Swerve.Mod4;

public class Swerve extends Subsystem {

    private final Pigeon2 gyro1;
    private final Pigeon2 gyro2;
    private final Pigeon2 gyro3;
    private final Pigeon2 gyro4;

    private NEOSwerveModule[] mModules;
    private final SwerveDriveOdometry mOdometry;

    private static Swerve mInstance = null;

    public Swerve() {
        gyro1 = new Pigeon2(Constants.Swerve.pigeon1);
        gyro2 = new Pigeon2(Constants.Swerve.pigeon2);
        gyro3 = new Pigeon2(Constants.Swerve.pigeon3);
        gyro4 = new Pigeon2(Constants.Swerve.pigeon4);

        mModules = new NEOSwerveModule[] {
            new NEOSwerveModule(1, Mod1.constants),
            new NEOSwerveModule(2, Mod2.constants),
            new NEOSwerveModule(3, Mod3.constants),
            new NEOSwerveModule(4, Mod4.constants)
        };

        Timer.delay(0.1);
        resetGyro();

        mOdometry = new SwerveDriveOdometry(Constants.Swerve.swerveKinematics, getGyroAngle(), getModulePositions());
    }   

    public static Swerve getInstance() {
        if (mInstance == null) {
            mInstance = new Swerve();
        }

        return mInstance;
    }

    public void setModuleState(SwerveModuleState[] desiredState) {
        SwerveDriveKinematics.desaturateWheelSpeeds(desiredState, Constants.Swerve.maxSpeed);

        for (NEOSwerveModule mod : mModules) {
            mod.setDesiredState(desiredState[mod.kModuleNumber - 1], false);
        }
    }

    public void drive(Translation2d translation, double rotation, boolean isFieldOriented, boolean isOpenLoop) {
        SwerveModuleState[] states = Constants.Swerve.swerveKinematics.toSwerveModuleStates(
            isFieldOriented ? ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), rotation, getGyroAngle())
                            : new ChassisSpeeds(translation.getX(), translation.getY(), rotation));

        SwerveDriveKinematics.desaturateWheelSpeeds(states, Constants.Swerve.maxSpeed);

        for (NEOSwerveModule mod : mModules) {
            mod.setDesiredState(states[mod.kModuleNumber - 1], isOpenLoop);
        }
    }

    public Pose2d getOdometryPose() {
        return mOdometry.getPoseMeters();
    }

    public Rotation2d getGyroAngle() {
        double averageYaw = ( gyro1.getYaw() + gyro2.getYaw() + gyro3.getYaw() + gyro4.getYaw() ) / 4;
        if (Constants.Swerve.invertGyro){
            return new Rotation2d(360 - averageYaw); 
        } else {
            return new Rotation2d(averageYaw);
        }
    }

    public SwerveModuleState[] getStates() {
        SwerveModuleState[] states = new SwerveModuleState[4];

        for (NEOSwerveModule mod : mModules) {
            states[mod.kModuleNumber - 1] = mod.getState();
        }
        
        return states;
    }

    public SwerveModulePosition[] getModulePositions() {
        SwerveModulePosition[] positions = new SwerveModulePosition[4];
        
        for (NEOSwerveModule mod : mModules) {
            positions[mod.kModuleNumber - 1] = mod.getPosition();
        }

        return positions;
    }

    @Override
    public void readPeriodicInputs() {
        mOdometry.update(getGyroAngle(), getModulePositions());
    }

    @Override
    public void outputTelemetry() {
        SmartDashboard.putNumber("gyro ", getGyroAngle().getDegrees());

        for (NEOSwerveModule mod : mModules) {
            SmartDashboard.putNumber(
                "Mod " + mod.kModuleNumber + " Cancoder", mod.getAngle().getDegrees());

            SmartDashboard.putNumber(
                "Mod " + mod.kModuleNumber + " Velocity", mod.getState().speedMetersPerSecond);
        }
    }

    public void resetGyro() {
        gyro1.setYaw(0);
        gyro2.setYaw(0);
        gyro3.setYaw(0);
        gyro4.setYaw(0);
    }

    public void resetOdometry(Pose2d pose2d) {
        mOdometry.resetPosition(getGyroAngle(), getModulePositions(), pose2d);
    }

}
