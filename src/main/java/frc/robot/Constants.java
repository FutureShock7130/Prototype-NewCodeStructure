package frc.robot;

import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import frc.lib.swerve.SwerveModuleConstants;

public class Constants {
    public static final double kLooperDt = 0.01;
    
    public static class Swerve {
        public static final double stickDeadband = 0.05;
        public static final double rotDeadband = 0.1;
        public static final int pigeon1 = 1;
        public static final int pigeon2 = 2;
        public static final int pigeon3 = 3;
        public static final int pigeon4 = 4;
        public static final boolean invertGyro = false; // Always ensure Gyro is CCW+ CW-

        /* Drivetrain Constants */
        public static final double trackWidth = 0.576; //meters
        public static final double wheelBase = 0.576; //meters
        public static final double wheelDiameter = Units.inchesToMeters(4.0);
        public static final double wheelCircumference = wheelDiameter * Math.PI;

        public static final double openLoopRamp = 0.25;
        public static final double closedLoopRamp = 0.0;

        public static final double driveGearRatio = (6.7460317460317460317460317460317 / 1.0); // 6.75:1 (6.7460317460317460317460317460317)
        public static final double angleGearRatio = (150.0 / 7.0 / 1.0); // 150/7 : 1

        public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
            new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, trackWidth / 2.0),
            new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0));

        /* Swerve Voltage Compensation */
        public static final double voltageComp = 12.0;

        /* Swerve Current Limiting */
        public static final int angleContinuousCurrentLimit = 30; //20
        public static final int driveContinuousCurrentLimit = 40; //80

        /* Angle Motor PID Values */
        public static final double angleKP = 0.01;
        public static final double angleKD = 0.0;

        /* Drive Motor PID Values */
        public static final double driveKP = 0.12;
        public static final double driveKI = 0.0;
        public static final double driveKD = 0.0025;
        public static final double driveKFF = 0.0;

        /* Drive Motor Characterization Values */
        public static final double driveKS = 0.667;
        public static final double driveKV = 2.44;
        public static final double driveKA = 0.27;

        /* Drive Motor Conversion Factors */
        public static final double driveConversionPositionFactor = (wheelDiameter * Math.PI) / driveGearRatio;
        public static final double driveConversionVelocityFactor = driveConversionPositionFactor / 60.0;
        public static final double angleConversionFactor = 360.0 / angleGearRatio;

        /* Swerve Profiling Values */
        public static final double maxSpeed = 4.1; // meters per second
        public static final double maxAngularVelocity = 13.5; // meters per second

        /* Neutral Modes */
        public static final IdleMode angleNeutralMode = IdleMode.kBrake;
        public static final IdleMode driveNeutralMode = IdleMode.kBrake;

        /* Motor Inverts */
        public static final boolean driveInvert = false;
        public static final boolean angleInvert = false;

        /* PID Shit */
        public static final double drivekP = 0;
        public static final double drivekD = 0;
        public static final double rotorkP = 0;
        public static final double rotorkD = 0;

        public static boolean canCoderInvert = false;

        /* Module Specific Constants */
        /* Front Left Module - Module 0 */
        public static final class Mod0 {
            public static final int driveMotorID = 11;
            public static final int angleMotorID = 12;
            public static final int canCoderID = 1;
            // public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-134.0);
            // public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-131.572);
            // public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-0.79);
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-132);
    
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                canCoderID, angleOffset);
        }
    
        /* Front Right Module - Module 1 */
        public static final class Mod1 {
            public static final int driveMotorID = 21;
            public static final int angleMotorID = 22;
            public static final int canCoderID = 2;
            // public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-6.2);
            // public static final Rotation2d angleOffset = Rotation2d.fromDegrees(0);
            // public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-6.5);
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-6.5);
    
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                canCoderID, angleOffset);
        }
    
        /* Back Left Module - Module 2 */
        public static final class Mod2 {
            public static final int driveMotorID = 41;
            public static final int angleMotorID = 42;
            public static final int canCoderID = 4;
            // public static final Rotation2d angleOffset = Rotation2d.fromDegrees(15.21);
            // public static final Rotation2d angleOffset = Rotation2d.fromDegrees(15.21);
            // public static final Rotation2d angleOffset = Rotation2d.fromDegrees(1.5);
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-343.3);
    
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                canCoderID, angleOffset);
        }
    
        /* Back Right Module - Module 3 */
        public static final class Mod3 {
            public static final int driveMotorID = 31;
            public static final int angleMotorID = 32;
            public static final int canCoderID = 3;
            // public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-41.2);
            // public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-39.882);
            // public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-4);
            public static final Rotation2d angleOffset = Rotation2d.fromDegrees(-43.7);
            public static final SwerveModuleConstants constants = new SwerveModuleConstants(driveMotorID, angleMotorID,
                canCoderID, angleOffset);
        }
    }
    
}
