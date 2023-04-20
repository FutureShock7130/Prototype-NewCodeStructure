package frc.lib.swerve;

import com.ctre.phoenix.sensors.WPI_CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import frc.lib.math.MathUtility;
import frc.lib.math.PID;
import frc.lib.util.CANCoderUtil;
import frc.lib.util.CANSparkMaxUtil;
import frc.lib.util.CANCoderUtil.CCUsage;
import frc.lib.util.CANSparkMaxUtil.Usage;
import frc.robot.Constants;

public class NEOSwerveModule {
    
    public int kModuleNumber;
    private Rotation2d mLastAngle;
    private Rotation2d mAngleOffset;

    private CANSparkMax mDriveMotor;
    private CANSparkMax mAngleMotor;

    private RelativeEncoder mDriveEncoder;
    private WPI_CANCoder mAngleEncoder;

    private SparkMaxPIDController mDriveController;

    private PID mRotorPID;

    private CTREConfigs mCTREConfigs;

    private final SimpleMotorFeedforward mFeedforward = new SimpleMotorFeedforward(
      Constants.Swerve.driveKS, Constants.Swerve.driveKV, Constants.Swerve.driveKA);

    public NEOSwerveModule(int moduleNumber, SwerveModuleConstants constants) {
        this.kModuleNumber = moduleNumber;
        mAngleOffset = constants.angleOffset;
        mCTREConfigs = CTREConfigs.getInstance();

        mDriveMotor = new CANSparkMax(constants.driveMotorID, MotorType.kBrushless);
        mDriveEncoder = mDriveMotor.getEncoder();
        mDriveController = mDriveMotor.getPIDController();
        configDriveMotor();

        mAngleMotor = new CANSparkMax(constants.angleMotorID, MotorType.kBrushless);
        configAngleMotor();

        mAngleEncoder = new WPI_CANCoder(constants.cancoderID, "7130");
        mAngleEncoder.configMagnetOffset(mAngleOffset.getDegrees());
        configAngleEncoder();

        mLastAngle = getState().angle;
    }

    public void setDesiredState(SwerveModuleState mDesiredState, boolean isOpenLoop) {
        mDesiredState = OnboardModuleState.optimize(mDesiredState, getState().angle);

        Rotation2d mAngle = (Math.abs(mDesiredState.speedMetersPerSecond) <= (Constants.Swerve.maxSpeed * 0.01))
            ? mLastAngle
            : mDesiredState.angle;
    
        mDesiredState.angle = mAngle;
    
        double error = getState().angle.getDegrees() - mDesiredState.angle.getDegrees();
        double constrainedError = MathUtility.constrainAngleDegrees(error);
        double rotorOutput = mRotorPID.calculate(constrainedError);
        rotorOutput = MathUtility.clamp(rotorOutput, -1, 1);
        mAngleMotor.set(rotorOutput);
        mLastAngle = mAngle;
    
    
        if (isOpenLoop) {
          double percentOutput = mDesiredState.speedMetersPerSecond / Constants.Swerve.maxSpeed;
          percentOutput = MathUtility.clamp(percentOutput, -1, 1);
          mDriveMotor.set(percentOutput);
        } else {
          mDriveController.setReference(
              mDesiredState.speedMetersPerSecond,
              ControlType.kVelocity,
              0,
              mFeedforward.calculate(mDesiredState.speedMetersPerSecond));
        }
    }

    public void configDriveMotor() {
        mDriveMotor.restoreFactoryDefaults();
        CANSparkMaxUtil.setCANSparkMaxBusUsage(mDriveMotor, Usage.kAll);
        mDriveMotor.setSmartCurrentLimit(Constants.Swerve.driveContinuousCurrentLimit);
        mDriveMotor.setInverted(Constants.Swerve.driveInvert);
        mDriveMotor.setIdleMode(Constants.Swerve.driveNeutralMode);
        mDriveEncoder.setVelocityConversionFactor(Constants.Swerve.driveConversionVelocityFactor);
        mDriveEncoder.setPositionConversionFactor(Constants.Swerve.driveConversionPositionFactor);
        mDriveController.setP(Constants.Swerve.driveKP);
        mDriveController.setI(Constants.Swerve.driveKI);
        mDriveController.setD(Constants.Swerve.driveKD);
        mDriveController.setFF(Constants.Swerve.driveKFF);
        mDriveMotor.enableVoltageCompensation(Constants.Swerve.voltageComp);
        mDriveEncoder.setPosition(0.0);
    }

    public void configAngleMotor() {
        mAngleMotor.restoreFactoryDefaults();
        CANSparkMaxUtil.setCANSparkMaxBusUsage(mAngleMotor, Usage.kVelocityOnly);
        mAngleMotor.setSmartCurrentLimit(Constants.Swerve.angleContinuousCurrentLimit);
        mAngleMotor.setInverted(Constants.Swerve.angleInvert);
        mAngleMotor.setIdleMode(Constants.Swerve.angleNeutralMode);
        mRotorPID = new PID(Constants.Swerve.angleKP, 0, Constants.Swerve.angleKD, 0, 0);
        mAngleMotor.enableVoltageCompensation(Constants.Swerve.voltageComp);
    }

    public void configAngleEncoder() {
        mAngleEncoder.configFactoryDefault();
        CANCoderUtil.setCANCoderBusUsage(mAngleEncoder, CCUsage.kSensorDataOnly);
        mAngleEncoder.configAllSettings(mCTREConfigs.swerveCanCoderConfig);
    }

    public Rotation2d getAngle() {
        return Rotation2d.fromDegrees(mAngleEncoder.getAbsolutePosition());
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(mDriveEncoder.getPosition(), getAngle());
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(mDriveEncoder.getVelocity(), getAngle());
    }

}
