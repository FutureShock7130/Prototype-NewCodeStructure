package frc.robot.manual.impl;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.lib.util.LogiJoystick;
import frc.robot.Constants;
import frc.robot.manual.ManualCommand;
import frc.robot.subsystems.impl.Swerve;

public class TeleopSwerve implements ManualCommand{

    private Swerve mSwerve;
    private LogiJoystick mController;
    // private XboxController mXboxController;

    private double translationVal;
    private double strafeVal;
    private double rotationVal;

    private boolean isFieldOriented = false;

    private SlewRateLimiter translationLimiter = new SlewRateLimiter(3.0);
    private SlewRateLimiter strafeLimiter = new SlewRateLimiter(3.0);
    private SlewRateLimiter rotationLimiter = new SlewRateLimiter(3.0);

    public TeleopSwerve(Swerve mSwerve, LogiJoystick mController) {
        this.mSwerve = mSwerve;
        this.mController = mController;
        // this.mXboxController = mXboxController;
    }

    @Override
    public void handle() {
        translationVal = 
            translationLimiter.calculate(
                MathUtil.applyDeadband(mController.getLeftY(), Constants.Swerve.stickDeadband)
            );
        strafeVal = 
            strafeLimiter.calculate(
                MathUtil.applyDeadband(mController.getLeftX(), Constants.Swerve.stickDeadband)
            );
        rotationVal = 
            rotationLimiter.calculate(
                MathUtil.applyDeadband(mController.getRightX(), Constants.Swerve.stickDeadband)
            );

        if (mController.getXPressed()) isFieldOriented = !isFieldOriented;

        mSwerve.drive(new Translation2d(translationVal, strafeVal).times(Constants.Swerve.maxSpeed),
                        rotationVal * Constants.Swerve.maxAngularVelocity, 
                        isFieldOriented, 
                        true);

        SmartDashboard.putBoolean("isFieldOriented", isFieldOriented);
    }
    
}
